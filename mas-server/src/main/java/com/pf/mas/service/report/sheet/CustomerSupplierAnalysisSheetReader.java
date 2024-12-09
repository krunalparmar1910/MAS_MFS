package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.CustomerSupplierAnalysisTotalRepository;
import com.pf.mas.repository.sheet.CustomersAnalysisRepository;
import com.pf.mas.repository.sheet.DetailsOfCusomtersAndSuppliersRepository;
import com.pf.mas.repository.sheet.HSNChapterAnalysisRepository;
import com.pf.mas.repository.sheet.SuppliersAnalysisRepository;
import com.pf.mas.service.report.sheet.carrier.CustomerSupplierAnalysisRecordCarrier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CustomerSupplierAnalysisSheetReader implements SheetReader {
    private static final String SUB_TOTAL = "SubTotal";
    private static final String CUSTOMERS_ANALYSIS_GROUP = "B2B CUSTOMERS' ANALYSIS";
    private static final String SUPPLIERS_ANALYSIS_GROUP = "B2B SUPPLIERS' ANALYSIS";
    private static final String HSN_REVENUE_GROUP = "HSN WISE REVENUE ANALYSIS";
    private static final String CHAPTER_WISE_GROUP = "CHAPTER WISE REVENUE ANALYSIS";
    private static final String CUSTOMER_DETAILS_GROUP = "Details of Customers (Top 20 of TTM)";
    private static final String SUPPLIER_DETAILS_GROUP = "Details of Suppliers (Top 20 of TTM)";
    private static final Set<String> SKIP_HEADERS = Set.of("Product Analysis", "Chapter Analysis");
    private static final Map<SheetTypeName, Set<String>> TYPES_GROUPS_MAP = Map.of(
            SheetTypeName.CUSTOMERS_ANALYSIS, Set.of(CUSTOMERS_ANALYSIS_GROUP),
            SheetTypeName.SUPPLIERS_ANALYSIS, Set.of(SUPPLIERS_ANALYSIS_GROUP),
            SheetTypeName.HSN_CHAPTER_ANALYSIS, Set.of(HSN_REVENUE_GROUP, CHAPTER_WISE_GROUP),
            SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP, Set.of(CUSTOMER_DETAILS_GROUP, SUPPLIER_DETAILS_GROUP)
    );
    private static final Map<SheetTypeName, Set<String>> TOTAL_FIELDS_MAP = Map.of(
            SheetTypeName.CUSTOMERS_ANALYSIS, Set.of(SUB_TOTAL, "Others", "Adjusted Revenue (Total)"),
            SheetTypeName.SUPPLIERS_ANALYSIS, Set.of(SUB_TOTAL, "Others", "Adjusted Purchase and Expenses (Total)"),
            SheetTypeName.HSN_CHAPTER_ANALYSIS, Set.of(SUB_TOTAL, "Revenue from other HSNs", "Revenue from other Chapters", "Product Revenue (Total)", "Add: Under Reported product revenue", "Adjusted Revenue (Total)"),
            SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP, Set.of()
    );
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final CustomersAnalysisRepository customersAnalysisRepository;
    private final SuppliersAnalysisRepository suppliersAnalysisRepository;
    private final HSNChapterAnalysisRepository hsnChapterAnalysisRepository;
    private final DetailsOfCusomtersAndSuppliersRepository detailsOfCusomtersAndSuppliersRepository;
    private final CustomerSupplierAnalysisTotalRepository customerSupplierAnalysisTotalRepository;

    public CustomerSupplierAnalysisSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            CustomersAnalysisRepository customersAnalysisRepository,
            SuppliersAnalysisRepository suppliersAnalysisRepository,
            HSNChapterAnalysisRepository hsnChapterAnalysisRepository,
            DetailsOfCusomtersAndSuppliersRepository detailsOfCusomtersAndSuppliersRepository,
            CustomerSupplierAnalysisTotalRepository customerSupplierAnalysisTotalRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.customerSupplierAnalysisTotalRepository = customerSupplierAnalysisTotalRepository;
        this.suppliersAnalysisRepository = suppliersAnalysisRepository;
        this.hsnChapterAnalysisRepository = hsnChapterAnalysisRepository;
        this.detailsOfCusomtersAndSuppliersRepository = detailsOfCusomtersAndSuppliersRepository;
        this.customersAnalysisRepository = customersAnalysisRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        FieldDateMonthYear fieldDateMonthYear = null;
        Set<String> groupHeaders = TYPES_GROUPS_MAP.get(sheetTypeName);
        Set<String> totalFields = TOTAL_FIELDS_MAP.get(sheetTypeName);
        List<String> fieldHeadersList = Collections.emptyList();
        CustomerSupplierAnalysisRecordCarrier carrier = new CustomerSupplierAnalysisRecordCarrier(sheetTypeName);

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(), fieldIndex = 0; cellIndex < row.getLastCellNum(); ++cellIndex, ++fieldIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (groupHeaders.stream().anyMatch(header -> StringUtils.containsIgnoreCase(cellValue, header))) {
                    FieldGroup newGroup = SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder);
                    fieldGroups.add(newGroup);

                    // this sheet does not have date headers
                    if (sheetTypeName != SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP
                            && SheetReaderUtils.isValidDate(newGroup.getFieldGroupName())) {
                        fieldDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(newGroup.getFieldGroupName());
                    }

                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();
                        fieldHeadersList = getFieldHeaderList(row, dataFormatter);
                    }
                    break;
                } else if (StringUtils.containsIgnoreCase(cellValue, SheetReaderUtils.PERIOD_COVERED)) {
                    fieldDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue);
                } else if (StringUtils.isNotBlank(cellValue) && !fieldGroups.isEmpty() && fieldDateMonthYear != null
                        && SKIP_HEADERS.stream().noneMatch(header -> StringUtils.containsIgnoreCase(cellValue, header))) {
                    // handle total fields separately per record
                    if (totalFields.contains(cellValue)) {
                        carrier.newTotalRecord(cellValue, fieldGroups.get(fieldGroups.size() - 1), fieldDateMonthYear, clientOrder);
                        for (int totalIndex = cellIndex + 1; totalIndex < row.getLastCellNum(); ++totalIndex) {
                            Cell totalCell = row.getCell(totalIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String totalCellValue = dataFormatter.formatCellValue(totalCell);
                            if (StringUtils.isNotBlank(totalCellValue)) {
                                BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(totalCell, totalCellValue);
                                carrier.setTotalFieldValue(fieldHeadersList.get(totalIndex), totalCellValue, numericCellValue);
                            }
                        }
                        break;
                    } else {
                        if (fieldIndex == 0) {
                            carrier.newRecord(fieldGroups.get(fieldGroups.size() - 1), fieldDateMonthYear, clientOrder);
                        }
                        BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                        carrier.setFieldValue(fieldHeadersList.get(fieldIndex), cellValue, numericCellValue);
                    }
                }
            }
        }

        saveAllRecords(sheetTypeName, fieldGroups, carrier);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    private List<String> getFieldHeaderList(Row row, DataFormatter dataFormatter) {
        List<String> setterList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue)) {
                setterList.add(cellValue);
            }
        }
        return setterList;
    }

    private void saveAllRecords(SheetTypeName sheetTypeName, List<FieldGroup> fieldGroups, CustomerSupplierAnalysisRecordCarrier carrier) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        log.debug("Saving {} CustomerSupplierAnalysisTotal records", carrier.getCustomerSupplierAnalysisTotalList().size());
        customerSupplierAnalysisTotalRepository.saveAll(carrier.getCustomerSupplierAnalysisTotalList());

        if (SheetTypeName.CUSTOMERS_ANALYSIS == sheetTypeName) {
            log.debug("Saving {} CustomersAnalysis records", carrier.getCustomersAnalysisList().size());
            customersAnalysisRepository.saveAll(carrier.getCustomersAnalysisList());
        } else if (SheetTypeName.SUPPLIERS_ANALYSIS == sheetTypeName) {
            log.debug("Saving {} SuppliersAnalysis records", carrier.getSuppliersAnalysisList().size());
            suppliersAnalysisRepository.saveAll(carrier.getSuppliersAnalysisList());
        } else if (SheetTypeName.HSN_CHAPTER_ANALYSIS == sheetTypeName) {
            log.debug("Saving {} HSNChapterAnalysis records", carrier.getHsnChapterAnalysisList().size());
            hsnChapterAnalysisRepository.saveAll(carrier.getHsnChapterAnalysisList());
        } else if (SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP == sheetTypeName) {
            log.debug("Saving {} DetailsOfCustomersAndSuppliers records", carrier.getDetailsOfCustAndSuppList().size());
            detailsOfCusomtersAndSuppliersRepository.saveAll(carrier.getDetailsOfCustAndSuppList());
        }
    }
}

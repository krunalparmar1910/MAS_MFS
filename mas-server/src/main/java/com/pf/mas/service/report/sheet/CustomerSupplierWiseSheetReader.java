package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.CircularTransactionsRepository;
import com.pf.mas.repository.sheet.CircularTransactionsValueRepository;
import com.pf.mas.repository.sheet.CustomerProductSupplierWiseTotalRepository;
import com.pf.mas.repository.sheet.CustomerWiseRepository;
import com.pf.mas.repository.sheet.CustomerWiseValueRepository;
import com.pf.mas.repository.sheet.ProductWiseRepository;
import com.pf.mas.repository.sheet.ProductWiseValueRepository;
import com.pf.mas.repository.sheet.SupplierWiseRepository;
import com.pf.mas.repository.sheet.SupplierWiseValueRepository;
import com.pf.mas.service.report.sheet.carrier.CustomerSupplierWiseRecordCarrier;
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
public class CustomerSupplierWiseSheetReader implements SheetReader {
    private static final Map<SheetTypeName, Set<String>> GROUP_HEADERS_MAP = Map.of(
            SheetTypeName.CUSTOMER_WISE, Set.of("Partywise Bifurcation of Revenue (in INR)"),
            SheetTypeName.SUPPLIER_WISE, Set.of("Partywise Bifurcation of Purchase and Expenses (in INR)"),
            SheetTypeName.PRODUCT_WISE, Set.of("Product Wise Bifurcation of Revenue (in INR)"),
            SheetTypeName.CIRCULAR_TRANSACTIONS, Set.of("Circular Transactions (Revenue and Purchase executed with the same vendor)")
    );
    private static final Map<SheetTypeName, Set<String>> TOTAL_FIELDS_MAP = Map.of(
            SheetTypeName.CUSTOMER_WISE, Set.of("Except B2B Transactions", "Adjusted Revenue (Total)"),
            SheetTypeName.SUPPLIER_WISE, Set.of("Adjusted Purchase and Expenses (Total)"),
            SheetTypeName.PRODUCT_WISE, Set.of("No HSN code available", "Adjusted Revenue (Total)"),
            SheetTypeName.CIRCULAR_TRANSACTIONS, Set.of()
    );
    private static final Map<SheetTypeName, List<String>> FIELD_NAMES_MAP = Map.of(
            SheetTypeName.CUSTOMER_WISE, List.of("CUSTOMER'S NAME", "CUSTOMER'S GSTIN"),
            SheetTypeName.SUPPLIER_WISE, List.of("SUPPLIER'S NAME", "SUPPLIER'S GSTIN"),
            SheetTypeName.PRODUCT_WISE, List.of("PRODUCT (HSN)", "HSN's Name"),
            SheetTypeName.CIRCULAR_TRANSACTIONS, List.of("VENDOR NAME", "VENDOR GSTN", "VENDOR STATE", "ADJUSTED REVENUE/\nADJUSTED PURCHASE AND EXPENSES")
    );
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final CircularTransactionsRepository circularTransactionsRepository;
    private final CustomerWiseRepository customerWiseRepository;
    private final ProductWiseRepository productWiseRepository;
    private final SupplierWiseRepository supplierWiseRepository;
    private final CircularTransactionsValueRepository circularTransactionsValueRepository;
    private final CustomerWiseValueRepository customerWiseValueRepository;
    private final ProductWiseValueRepository productWiseValueRepository;
    private final SupplierWiseValueRepository supplierWiseValueRepository;
    private final CustomerProductSupplierWiseTotalRepository customerProductSupplierWiseTotalRepository;

    @SuppressWarnings("java:S107")
    public CustomerSupplierWiseSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            CircularTransactionsRepository circularTransactionsRepository,
            CustomerWiseRepository customerWiseRepository,
            ProductWiseRepository productWiseRepository,
            SupplierWiseRepository supplierWiseRepository,
            CircularTransactionsValueRepository circularTransactionsValueRepository,
            CustomerWiseValueRepository customerWiseValueRepository,
            ProductWiseValueRepository productWiseValueRepository,
            SupplierWiseValueRepository supplierWiseValueRepository,
            CustomerProductSupplierWiseTotalRepository customerProductSupplierWiseTotalRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.circularTransactionsRepository = circularTransactionsRepository;
        this.customerWiseRepository = customerWiseRepository;
        this.productWiseRepository = productWiseRepository;
        this.supplierWiseRepository = supplierWiseRepository;
        this.circularTransactionsValueRepository = circularTransactionsValueRepository;
        this.customerWiseValueRepository = customerWiseValueRepository;
        this.productWiseValueRepository = productWiseValueRepository;
        this.supplierWiseValueRepository = supplierWiseValueRepository;
        this.customerProductSupplierWiseTotalRepository = customerProductSupplierWiseTotalRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        FieldDateMonthYear sheetDateMonthYear = null;
        List<FieldDateMonthYear> fieldDateMonthYearList = Collections.emptyList();
        Set<String> groupHeaders = GROUP_HEADERS_MAP.get(sheetTypeName);
        Set<String> totalFields = TOTAL_FIELDS_MAP.get(sheetTypeName);
        List<String> fieldNames = FIELD_NAMES_MAP.get(sheetTypeName);
        int fieldNameCount = fieldNames.size();
        CustomerSupplierWiseRecordCarrier carrier = new CustomerSupplierWiseRecordCarrier(sheetTypeName);

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (groupHeaders.stream().anyMatch(cellValue::equalsIgnoreCase)) {
                    fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));
                    fieldDateMonthYearList = Collections.emptyList();

                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();

                        // if group found, next row is expected to be headers and dates hence parse next row for dates
                        fieldDateMonthYearList = getFieldDateMonthYearList(row, dataFormatter, fieldNameCount);

                        // data will be present from next row
                        if (rowIterator.hasNext()) {
                            row = rowIterator.next();
                        }
                    }
                    break;
                } else if (StringUtils.containsIgnoreCase(cellValue, SheetReaderUtils.PERIOD_COVERED)) {
                    sheetDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue);
                }
            }

            if (!fieldGroups.isEmpty()) {
                FieldGroup fieldGroup = fieldGroups.get(fieldGroups.size() - 1);
                String totalFieldName = "";
                boolean isTotalField = false;

                for (int cellIndex = row.getFirstCellNum(), fieldCount = 0; cellIndex < row.getLastCellNum(); ++cellIndex, ++fieldCount) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                    if (StringUtils.isNotBlank(cellValue)) {
                        // case for total fields
                        if (totalFields.stream().anyMatch(cellValue::equalsIgnoreCase) || isTotalField) {
                            if (!isTotalField) {
                                isTotalField = true;
                                totalFieldName = cellValue;
                            } else {
                                BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                                // add a new total field for each total field value encountered specific to the current group
                                carrier.newTotalRecord(
                                        totalFieldName, cellValue, numericCellValue, fieldGroup, fieldDateMonthYearList.get(fieldCount - fieldNameCount), clientOrder);
                            }
                        } else {
                            // for all fields, initialize new record for every row
                            // each row will have one record, a record will have fields and list of field values against a specific date
                            if (fieldCount == 0) {
                                carrier.newRecord(fieldGroup, sheetDateMonthYear, clientOrder);
                            }
                            // field names will be common, and each field name record will have multiple field values against a specific date
                            // hence for field names, directly set the field name in the record
                            if (fieldCount < fieldNameCount) {
                                carrier.setFieldValue(fieldNames.get(fieldCount), cellValue);
                            } else if ((fieldCount - fieldNameCount) < fieldDateMonthYearList.size()) {
                                // and for each value field, add a new value record against the record
                                BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                                carrier.newValueRecord(fieldDateMonthYearList.get(fieldCount - fieldNameCount), cellValue, numericCellValue);
                            }
                        }
                    }
                }
            }
        }

        saveAllRecords(sheetTypeName, fieldGroups, carrier);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    private List<FieldDateMonthYear> getFieldDateMonthYearList(Row row, DataFormatter dataFormatter, int fieldNameCount) throws MasReportSheetReaderException {
        List<FieldDateMonthYear> fieldDateMonthYearList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + fieldNameCount; cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue) && SheetReaderUtils.isValidDate(cellValue)) {
                fieldDateMonthYearList.add(fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue));
            }
        }
        return fieldDateMonthYearList;
    }

    private void saveAllRecords(SheetTypeName sheetTypeName, List<FieldGroup> fieldGroups, CustomerSupplierWiseRecordCarrier carrier) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        customerProductSupplierWiseTotalRepository.saveAll(carrier.getCustomerProductSupplierWiseTotalList());
        log.debug("Saving {} CustomerProductSupplierWiseTotal records", carrier.getCustomerProductSupplierWiseTotalList().size());

        if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            log.debug("Saving {} CircularTransactions records", carrier.getCircularTransactionsList().size());
            circularTransactionsRepository.saveAll(carrier.getCircularTransactionsList());

            log.debug("Saving {} CircularTransactionsValue records", carrier.getCircularTransactionsValueList().size());
            circularTransactionsValueRepository.saveAll(carrier.getCircularTransactionsValueList());
        } else if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            log.debug("Saving {} CustomerWise records", carrier.getCustomerWiseList().size());
            customerWiseRepository.saveAll(carrier.getCustomerWiseList());

            log.debug("Saving {} CustomerWiseValue records", carrier.getCustomerWiseValueList().size());
            customerWiseValueRepository.saveAll(carrier.getCustomerWiseValueList());
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            log.debug("Saving {} SupplierWise records", carrier.getSupplierWiseList().size());
            supplierWiseRepository.saveAll(carrier.getSupplierWiseList());

            log.debug("Saving {} SupplierWiseValue records", carrier.getSupplierWiseValueList().size());
            supplierWiseValueRepository.saveAll(carrier.getSupplierWiseValueList());
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            log.debug("Saving {} ProductWise records", carrier.getProductWiseList().size());
            productWiseRepository.saveAll(carrier.getProductWiseList());

            log.debug("Saving {} ProductWiseValue records", carrier.getProductWiseValueList().size());
            productWiseValueRepository.saveAll(carrier.getProductWiseValueList());
        }
    }
}

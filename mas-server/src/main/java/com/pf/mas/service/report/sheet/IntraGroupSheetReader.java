package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.IntraGroup;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.IntraGroupPurchasesAndExpensesValueRepository;
import com.pf.mas.repository.sheet.IntraGroupRepository;
import com.pf.mas.repository.sheet.IntraGroupRevenueValueRepository;
import com.pf.mas.repository.sheet.IntraGroupSummaryOfGroupTransactionRepository;
import com.pf.mas.service.report.sheet.carrier.IntraGroupRecordCarrier;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class IntraGroupSheetReader implements SheetReader {
    private static final Set<String> GROUP_HEADERS = Set.of(
            "SUMMARY OF REVENUE TRANSACTIONS OF THE GROUP (IN INR)",
            "SUMMARY OF PURCHASE AND EXPENSES TRANSACTIONS OF THE GROUP (IN INR)",
            "SUMMARY OF GROUP TRANSACTIONS (IN INR)"
    );
    private static final String TTM = "TTM";
    private static final String TOTAL = "TOTAL";
    private static final Set<String> SUMMARY_COLUMNS_TWO = Set.of("PARTICULARS", "GSTIN");
    private static final int DEFAULT_COLUMNS_TO_SKIP = 1;
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final IntraGroupRepository intraGroupRepository;
    private final IntraGroupRevenueValueRepository intraGroupRevenueValueRepository;
    private final IntraGroupPurchasesAndExpensesValueRepository intraGroupPurchasesAndExpensesValueRepository;
    private final IntraGroupSummaryOfGroupTransactionRepository intraGroupSummaryOfGroupTransactionRepository;

    public IntraGroupSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            IntraGroupRepository intraGroupRepository,
            IntraGroupRevenueValueRepository intraGroupRevenueValueRepository,
            IntraGroupPurchasesAndExpensesValueRepository intraGroupPurchasesAndExpensesValueRepository,
            IntraGroupSummaryOfGroupTransactionRepository intraGroupSummaryOfGroupTransactionRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.intraGroupRepository = intraGroupRepository;
        this.intraGroupRevenueValueRepository = intraGroupRevenueValueRepository;
        this.intraGroupPurchasesAndExpensesValueRepository = intraGroupPurchasesAndExpensesValueRepository;
        this.intraGroupSummaryOfGroupTransactionRepository = intraGroupSummaryOfGroupTransactionRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        List<FieldDateMonthYear> fieldDateMonthYearList = Collections.emptyList();
        FieldDateMonthYear sheetDateMonthYear = null;
        List<String> gstList = Collections.emptyList();
        List<String> fieldHeadersList = Collections.emptyList();
        boolean iteratingSummaryGroup = false;
        IntraGroupRecordCarrier carrier = new IntraGroupRecordCarrier();
        Set<String> gstSet = new LinkedHashSet<>();

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (GROUP_HEADERS.stream().anyMatch(cellValue::equalsIgnoreCase)) {
                    iteratingSummaryGroup = true;
                    fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));

                    if (rowIterator.hasNext()) {
                        // if group found, next row is expected to be headers and dates hence parse next row for dates
                        row = rowIterator.next();
                        fieldDateMonthYearList = getFieldDateMonthYearList(row, dataFormatter, sheetDateMonthYear);

                        if (rowIterator.hasNext()) {
                            row = rowIterator.next();
                            // next row is expected to be headers
                            fieldHeadersList = getFieldHeadersList(row, dataFormatter);

                            if (rowIterator.hasNext()) {
                                row = rowIterator.next();
                            }
                        }
                    }
                    break;
                } else if (GROUP_HEADERS.stream().anyMatch(header -> StringUtils.containsIgnoreCase(cellValue, header))) {
                    // some group headers have dates in them which requires a contains match rather than exact match
                    iteratingSummaryGroup = false;
                    fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));

                    if (rowIterator.hasNext()) {
                        // next row is empty for such cases
                        row = rowIterator.next();

                        if (rowIterator.hasNext()) {
                            // next row is expected to be gst list
                            row = rowIterator.next();
                            gstList = getGSTList(row, dataFormatter, gstSet);

                            if (rowIterator.hasNext()) {
                                row = rowIterator.next();
                            }
                        }
                    }
                } else if (StringUtils.containsIgnoreCase(cellValue, SheetReaderUtils.PERIOD_COVERED)) {
                    sheetDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue);
                }
            }

            handleIntraGroupRecords(
                    fieldGroups,
                    fieldDateMonthYearList,
                    row,
                    dataFormatter,
                    iteratingSummaryGroup,
                    carrier,
                    fieldHeadersList,
                    gstList,
                    sheetDateMonthYear,
                    clientOrder);
        }

        saveAllRecords(fieldGroups, carrier);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    @SuppressWarnings("java:S107")
    private void handleIntraGroupRecords(
            List<FieldGroup> fieldGroups,
            List<FieldDateMonthYear> fieldDateMonthYearList,
            Row row,
            DataFormatter dataFormatter,
            boolean iteratingSummaryGroup,
            IntraGroupRecordCarrier carrier,
            List<String> fieldHeadersList,
            List<String> gstList,
            FieldDateMonthYear sheetDateMonthYear,
            ClientOrder clientOrder) {
        if (!fieldGroups.isEmpty() && !fieldDateMonthYearList.isEmpty()) {
            FieldGroup fieldGroup = fieldGroups.get(fieldGroups.size() - 1);

            int valueFieldCount = 0;
            int summaryFieldCount = 0;
            IntraGroup intraGroup = null;
            for (int cellIndex = row.getFirstCellNum() + DEFAULT_COLUMNS_TO_SKIP, fieldCount = 0; cellIndex < row.getLastCellNum(); ++cellIndex, ++fieldCount) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                if (StringUtils.isNotBlank(cellValue)) {
                    if (iteratingSummaryGroup) {
                        if (fieldCount == 0) {
                            intraGroup = carrier.newIntraGroupRecord(fieldGroup, sheetDateMonthYear, clientOrder, cellValue);
                        } else {
                            if (valueFieldCount == 0) {
                                carrier.newIntraGroupValueRecord(fieldGroup, fieldDateMonthYearList.get(cellIndex / fieldHeadersList.size()), intraGroup);
                            }
                            BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                            carrier.setIntraGroupValueFieldValue(fieldGroup, fieldHeadersList.get(valueFieldCount), cellValue, numericCellValue);
                            ++valueFieldCount;
                            if (valueFieldCount == fieldHeadersList.size()) {
                                valueFieldCount = 0;
                            }
                        }
                    } else {
                        if (fieldCount == 0) {
                            intraGroup = carrier.newIntraGroupRecord(fieldGroup, sheetDateMonthYear, clientOrder, cellValue);
                            summaryFieldCount = 0;
                        } else {
                            if (intraGroup == null) {
                                intraGroup = carrier.getIntraGroupList().get(carrier.getIntraGroupList().size() - 1);
                            }
                            BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                            carrier.newIntraGroupValueSummaryRecord(fieldGroup, intraGroup, fieldDateMonthYearList);
                            carrier.setIntraGroupValueFieldSummaryValue(gstList.get(summaryFieldCount), cellValue, numericCellValue);
                        }
                    }
                }
                // has to be incremented only after first field is found for correct index
                // as empty cells will not increase the summary field cout
                if (fieldCount > 0) {
                    ++summaryFieldCount;
                }
            }
        }
    }

    private List<FieldDateMonthYear> getFieldDateMonthYearList(
            Row row,
            DataFormatter dataFormatter,
            FieldDateMonthYear sheetDateMonthYear) throws MasReportSheetReaderException {
        List<FieldDateMonthYear> fieldDateMonthYearList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + SUMMARY_COLUMNS_TWO.size(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue)) {
                if (StringUtils.containsIgnoreCase(cellValue, TTM) && sheetDateMonthYear != null) {
                    fieldDateMonthYearList.add(fieldDateMonthYearSheetReader.getLastOneYearFromToDate(sheetDateMonthYear, cellValue));
                } else {
                    fieldDateMonthYearList.add(fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue));
                }
            }
        }
        return fieldDateMonthYearList;
    }

    private List<String> getFieldHeadersList(Row row, DataFormatter dataFormatter) {
        Set<String> fieldSet = new HashSet<>();
        List<String> fieldHeadersList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + SUMMARY_COLUMNS_TWO.size(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue) && !fieldSet.contains(cellValue)) {
                fieldHeadersList.add(cellValue);
                fieldSet.add(cellValue);
            }
        }
        return fieldHeadersList;
    }

    private List<String> getGSTList(Row row, DataFormatter dataFormatter, Set<String> gstSet) {
        for (int cellIndex = row.getFirstCellNum() + SUMMARY_COLUMNS_TWO.size(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell)).replace("\n", "");
            if (StringUtils.isNotBlank(cellValue)) {
                gstSet.add(cellValue);
            }
        }
        gstSet.add(TOTAL);
        return gstSet.stream().toList();
    }

    private void saveAllRecords(List<FieldGroup> fieldGroups, IntraGroupRecordCarrier carrier) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        log.debug("Saving {} IntraGroup records", carrier.getIntraGroupList().size());
        intraGroupRepository.saveAll(carrier.getIntraGroupList());

        log.debug("Saving {} IntraGroupRevenueValue records", carrier.getIntraGroupRevenueValueList().size());
        intraGroupRevenueValueRepository.saveAll(carrier.getIntraGroupRevenueValueList());

        log.debug("Saving {} IntraGroupPurchasesAndExpensesValue records", carrier.getIntraGroupPurchasesAndExpensesValueList().size());
        intraGroupPurchasesAndExpensesValueRepository.saveAll(carrier.getIntraGroupPurchasesAndExpensesValueList());

        log.debug("Saving {} IntraGroupSummaryOfGroupTransaction records", carrier.getIntraGroupSummaryOfGroupTransactionList().size());
        intraGroupSummaryOfGroupTransactionRepository.saveAll(carrier.getIntraGroupSummaryOfGroupTransactionList());
    }
}

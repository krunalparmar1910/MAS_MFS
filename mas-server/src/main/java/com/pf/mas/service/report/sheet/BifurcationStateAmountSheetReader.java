package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.AdjustedAmountsRepository;
import com.pf.mas.repository.sheet.AdjustedAmountsValueRepository;
import com.pf.mas.repository.sheet.BifurcationRepository;
import com.pf.mas.repository.sheet.BifurcationValueRepository;
import com.pf.mas.repository.sheet.StateWiseRepository;
import com.pf.mas.repository.sheet.StateWiseValueRepository;
import com.pf.mas.repository.sheet.YearlyReturnSummaryRepository;
import com.pf.mas.repository.sheet.YearlyReturnSummaryValueRepository;
import com.pf.mas.service.report.sheet.carrier.BifurcationStateAmountRecordCarrier;
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
public class BifurcationStateAmountSheetReader implements SheetReader {
    private static final String PARTICULARS = "PARTICULARS";
    private static final String AMOUNT = "AMOUNT";
    private static final String ADJUSTED_REVENUE = "% SHARE IN  ADJUSTED REVENUE";
    private static final String ADJUSTED_PURCHASES_EXPENSES = "% SHARE IN ADJUSTED PURCHASE AND EXPENSES";
    private static final Map<SheetTypeName, Set<String>> GROUP_HEADERS_MAP = Map.of(
            SheetTypeName.BIFURCATION, Set.of("Bifurcation of Revenue (in INR)", "Bifurcation of Purchase and Expenses (in INR)"),
            SheetTypeName.ADJUSTED_AMOUNTS, Set.of("Bifurcation of Revenue (in INR)", "Bifurcation of Purchase and Expenses (in INR)"),
            SheetTypeName.STATE_WISE, Set.of("State wise Bifurcation of Revenue (in INR)", "State wise Bifurcation of Purchase and Expenses (in INR)"),
            SheetTypeName.YEARLY_RETURN_SUMMARY, Set.of("as per GSTR 9 (in INR)", "as per GSTR1 (in INR)", "GSTR9 Vs GSTR1 (in INR)")
    );
    private static final Map<SheetTypeName, Set<String>> FIELD_TYPES_MAP = Map.of(
            SheetTypeName.BIFURCATION, Set.of(PARTICULARS),
            SheetTypeName.ADJUSTED_AMOUNTS, Set.of(PARTICULARS),
            SheetTypeName.STATE_WISE, Set.of(PARTICULARS, "STATE CODE"),
            SheetTypeName.YEARLY_RETURN_SUMMARY, Set.of(PARTICULARS)
    );
    private static final Map<SheetTypeName, Set<String>> DATE_VALUE_FIELDS_MAP = Map.of(
            SheetTypeName.BIFURCATION, Set.of(AMOUNT, ADJUSTED_REVENUE, ADJUSTED_PURCHASES_EXPENSES),
            SheetTypeName.ADJUSTED_AMOUNTS, Set.of(AMOUNT, ADJUSTED_REVENUE, ADJUSTED_PURCHASES_EXPENSES),
            SheetTypeName.STATE_WISE, Set.of(AMOUNT, ADJUSTED_REVENUE, ADJUSTED_PURCHASES_EXPENSES),
            SheetTypeName.YEARLY_RETURN_SUMMARY, Set.of("Amount", "% share in Sales during the period", "Total GST")
    );
    private static final Map<SheetTypeName, Integer> SHEET_TYPE_COLUMN_COUNT_MAP = Map.of(
            SheetTypeName.BIFURCATION, 2,
            SheetTypeName.ADJUSTED_AMOUNTS, 2,
            SheetTypeName.STATE_WISE, 2,
            SheetTypeName.YEARLY_RETURN_SUMMARY, 3
    );
    private static final String INFO_STRING = "*The revenue shown in this sheet";
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final BifurcationRepository bifurcationRepository;
    private final BifurcationValueRepository bifurcationValueRepository;
    private final AdjustedAmountsRepository adjustedAmountsRepository;
    private final AdjustedAmountsValueRepository adjustedAmountsValueRepository;
    private final StateWiseRepository stateWiseRepository;
    private final StateWiseValueRepository stateWiseValueRepository;
    private final YearlyReturnSummaryRepository yearlyReturnSummaryRepository;
    private final YearlyReturnSummaryValueRepository yearlyReturnSummaryValueRepository;

    @SuppressWarnings("java:S107")
    public BifurcationStateAmountSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            BifurcationRepository bifurcationRepository,
            BifurcationValueRepository bifurcationValueRepository,
            AdjustedAmountsRepository adjustedAmountsRepository,
            AdjustedAmountsValueRepository adjustedAmountsValueRepository,
            StateWiseRepository stateWiseRepository,
            StateWiseValueRepository stateWiseValueRepository,
            YearlyReturnSummaryRepository yearlyReturnSummaryRepository,
            YearlyReturnSummaryValueRepository yearlyReturnSummaryValueRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.bifurcationRepository = bifurcationRepository;
        this.bifurcationValueRepository = bifurcationValueRepository;
        this.adjustedAmountsRepository = adjustedAmountsRepository;
        this.adjustedAmountsValueRepository = adjustedAmountsValueRepository;
        this.stateWiseRepository = stateWiseRepository;
        this.stateWiseValueRepository = stateWiseValueRepository;
        this.yearlyReturnSummaryRepository = yearlyReturnSummaryRepository;
        this.yearlyReturnSummaryValueRepository = yearlyReturnSummaryValueRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        List<FieldDateMonthYear> fieldDateMonthYearList = Collections.emptyList();
        FieldDateMonthYear sheetDateMonthYear = null;
        Set<String> groupHeaders = GROUP_HEADERS_MAP.get(sheetTypeName);
        Set<String> fieldTypes = FIELD_TYPES_MAP.get(sheetTypeName);
        Set<String> dateValueFields = DATE_VALUE_FIELDS_MAP.get(sheetTypeName);
        int columnCount = SHEET_TYPE_COLUMN_COUNT_MAP.get(sheetTypeName);
        int fieldTypeCount = fieldTypes.size();
        List<String> fieldHeadersList = Collections.emptyList();
        List<String> fieldValueHeadersList = Collections.emptyList();
        BifurcationStateAmountRecordCarrier carrier = new BifurcationStateAmountRecordCarrier(sheetTypeName);

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (groupHeaders.stream().anyMatch(cellValue::equalsIgnoreCase)
                        || (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName
                        && groupHeaders.stream().anyMatch(header -> StringUtils.containsIgnoreCase(cellValue, header)))) {
                    FieldGroup newGroup = SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder);
                    fieldGroups.add(newGroup);
                    fieldDateMonthYearList = new ArrayList<>();

                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();
                        // if group found, next row is expected to be headers and dates hence parse next row for dates
                        fieldHeadersList = getFieldHeaderList(row, dataFormatter, fieldTypes, fieldTypeCount);
                        fieldDateMonthYearList = getFieldDateMonthYearList(row, dataFormatter, fieldTypeCount);

                        if (!fieldHeadersList.isEmpty() && rowIterator.hasNext()) {
                            row = rowIterator.next();
                            Cell firstCell = row.getCell(row.getFirstCellNum(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String firstCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(firstCell));
                            // no second row of field headers for value fields date wise for yearly return summary
                            if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName && StringUtils.isNotBlank(firstCellValue)) {
                                break;
                            }

                            fieldValueHeadersList = getFieldHeaderList(row, dataFormatter, dateValueFields, columnCount);

                            if (rowIterator.hasNext()) {
                                row = rowIterator.next();
                            }
                        }
                    }
                    break;
                } else if (StringUtils.containsIgnoreCase(cellValue, SheetReaderUtils.PERIOD_COVERED)) {
                    sheetDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue);
                }
            }

            if (!fieldGroups.isEmpty() && !fieldHeadersList.isEmpty() && !fieldDateMonthYearList.isEmpty()) {
                FieldGroup fieldGroup = fieldGroups.get(fieldGroups.size() - 1);

                int valueFieldCount = 0;
                boolean iteratingValueColumns = false;
                for (int cellIndex = row.getFirstCellNum(), fieldCount = 0; cellIndex < row.getLastCellNum(); ++cellIndex, ++fieldCount) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
                    int dateIndex = (fieldCount - fieldTypeCount) / columnCount;

                    if (StringUtils.isNotBlank(cellValue) || iteratingValueColumns) {
                        if (StringUtils.containsIgnoreCase(cellValue, INFO_STRING)) {
                            log.debug("Ignoring string {}", cellValue);
                            break;
                        } else if (fieldCount < fieldTypeCount) {
                            // fields per record for the field names present per group
                            if (fieldCount == 0) {
                                carrier.newRecord(fieldGroup, sheetDateMonthYear, clientOrder);
                            }
                            carrier.setFieldValue(fieldHeadersList.get(fieldCount), cellValue);
                        } else if (dateIndex < fieldDateMonthYearList.size()) {
                            // add value field per date column
                            iteratingValueColumns = true;
                            FieldDateMonthYear fieldDateMonthYear = fieldDateMonthYearList.get(dateIndex);
                            if (valueFieldCount == 0) {
                                carrier.newValueRecord(fieldDateMonthYear);
                            }
                            BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                            carrier.setValueFieldValue(fieldValueHeadersList.get(valueFieldCount), cellValue, numericCellValue);
                            ++valueFieldCount;
                            if (valueFieldCount == columnCount) {
                                valueFieldCount = 0;
                            }
                        }
                    }
                }
            }
        }

        saveAllRecords(sheetTypeName, fieldGroups, carrier);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    private List<String> getFieldHeaderList(Row row, DataFormatter dataFormatter, Set<String> fields, int fieldNameCount) {
        List<String> setterList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (fields.contains(cellValue)) {
                setterList.add(cellValue);
            }
            if (setterList.size() == fieldNameCount) {
                break;
            }
        }
        return setterList;
    }

    private List<FieldDateMonthYear> getFieldDateMonthYearList(
            Row row, DataFormatter dataFormatter, int fieldNameCount) throws MasReportSheetReaderException {
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

    private void saveAllRecords(SheetTypeName sheetTypeName, List<FieldGroup> fieldGroups, BifurcationStateAmountRecordCarrier carrier) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            log.debug("Saving {} Bifurcation records", carrier.getBifurcationList().size());
            bifurcationRepository.saveAll(carrier.getBifurcationList());

            log.debug("Saving {} BifurcationValue records", carrier.getBifurcationValueList().size());
            bifurcationValueRepository.saveAll(carrier.getBifurcationValueList());
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            log.debug("Saving {} AdjustedAmounts records", carrier.getAdjustedAmountsList().size());
            adjustedAmountsRepository.saveAll(carrier.getAdjustedAmountsList());

            log.debug("Saving {} AdjustedAmountsValue records", carrier.getAdjustedAmountsValueList().size());
            adjustedAmountsValueRepository.saveAll(carrier.getAdjustedAmountsValueList());
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            log.debug("Saving {} StateWise records", carrier.getStateWiseList().size());
            stateWiseRepository.saveAll(carrier.getStateWiseList());

            log.debug("Saving {} StateWiseValue records", carrier.getStateWiseValueList().size());
            stateWiseValueRepository.saveAll(carrier.getStateWiseValueList());
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            log.debug("Saving {} YearlyReturnSummary records", carrier.getYearlyReturnSummaryList().size());
            yearlyReturnSummaryRepository.saveAll(carrier.getYearlyReturnSummaryList());

            log.debug("Saving {} YearlyReturnSummaryValue records", carrier.getYearlyReturnSummaryValueList().size());
            yearlyReturnSummaryValueRepository.saveAll(carrier.getYearlyReturnSummaryValueList());
        }
    }
}

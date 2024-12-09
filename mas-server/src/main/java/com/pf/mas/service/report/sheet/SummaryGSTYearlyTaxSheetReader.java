package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.GSTR3BRepository;
import com.pf.mas.repository.sheet.GSTR3BValueRepository;
import com.pf.mas.repository.sheet.SummaryAnalysisRepository;
import com.pf.mas.repository.sheet.SummaryAnalysisValueRepository;
import com.pf.mas.repository.sheet.SummaryRepository;
import com.pf.mas.repository.sheet.SummaryValueRepository;
import com.pf.mas.repository.sheet.TaxRepository;
import com.pf.mas.repository.sheet.TaxValueRepository;
import com.pf.mas.repository.sheet.YearlyTaxRepository;
import com.pf.mas.repository.sheet.YearlyTaxValueRepository;
import com.pf.mas.service.report.sheet.carrier.SummaryRecordCarrier;
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
public class SummaryGSTYearlyTaxSheetReader implements SheetReader {
    private static final String QUATERLY_SUMMARY = "Quarterly Summary (in INR)";
    private static final String MONTHLY_SUMMARY = "Monthly Summary (in INR)";
    private static final String PEAK_MONTHS = "PEAK MONTHS/PEAK SEASONS";
    private static final String LULL_MONTHS = "LULL MONTHS/SLACK SEASONS";
    private static final Map<SheetTypeName, Set<String>> GROUP_HEADERS_MAP = Map.of(
            SheetTypeName.SUMMARY_ANALYSIS, Set.of(
                    "SUMMARY OF REVENUE, PURCHASES AND MARGIN (IN INR)",
                    "B2B CUSTOMER MATRIX",
                    PEAK_MONTHS,
                    LULL_MONTHS,
                    "B2B PRODUCT MATRIX - HSN WISE",
                    "B2B PRODUCT MATRIX - CHAPTER WISE",
                    "B2B SUPPLIER MATRIX",
                    "WORKING CAPITAL REQUIREMENT (FOR GST) OF BUSINESS",
                    "CIRCULAR TRANSACTIONS"
            ),
            SheetTypeName.SUMMARY, Set.of(
                    "Yearly Summary (in INR)",
                    QUATERLY_SUMMARY,
                    MONTHLY_SUMMARY
            ),
            SheetTypeName.GSTR_3B, Set.of(
                    "Revenue : GSTR1 VS GSTR3B (in INR)",
                    "Summary of Revenue (Taxable Value) (in INR)",
                    "Outward Taxable Supplies (Other than zero rated, nil rated and exempted) (in INR)",
                    "Inward supplies (liable to reverse charge) (in INR)"
            ),
            SheetTypeName.TAX, Set.of(
                    "Tax Payment Trend (in INR)",
                    "Input Tax Credit: Available vs Eligible vs Claimed (in INR)"
            ),
            SheetTypeName.YEARLY_TAX, Set.of(
                    "Tax Payment Trend (in INR)",
                    "Input Tax Credit: Available vs Eligible vs Claimed (in INR)"
            )
    );
    private static final Set<String> DATE_HEADERS = Set.of(PEAK_MONTHS, LULL_MONTHS);
    private static final Set<String> DATE_RANGE_HEADERS = Set.of(MONTHLY_SUMMARY);
    private static final Set<String> SKIP_LIST = Set.of("*The revenue shown in this sheet", "*Revenue and Purchase");
    private static final String PARTICULARS = "PARTICULARS";
    private static final int DEFAULT_COLUMN_COUNT = 1;
    private static final int QUARTERS = 4;
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final SummaryAnalysisRepository summaryAnalysisRepository;
    private final SummaryAnalysisValueRepository summaryAnalysisValueRepository;
    private final SummaryRepository summaryRepository;
    private final SummaryValueRepository summaryValueRepository;
    private final GSTR3BRepository gstr3BRepository;
    private final GSTR3BValueRepository gstr3BValueRepository;
    private final TaxRepository taxRepository;
    private final TaxValueRepository taxValueRepository;
    private final YearlyTaxRepository yearlyTaxRepository;
    private final YearlyTaxValueRepository yearlyTaxValueRepository;

    @SuppressWarnings("java:S107")
    public SummaryGSTYearlyTaxSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            SummaryAnalysisRepository summaryAnalysisRepository,
            SummaryAnalysisValueRepository summaryAnalysisValueRepository,
            SummaryRepository summaryRepository,
            SummaryValueRepository summaryValueRepository,
            GSTR3BRepository gstr3BRepository,
            GSTR3BValueRepository gstr3BValueRepository,
            TaxRepository taxRepository,
            TaxValueRepository taxValueRepository,
            YearlyTaxRepository yearlyTaxRepository,
            YearlyTaxValueRepository yearlyTaxValueRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.summaryAnalysisRepository = summaryAnalysisRepository;
        this.summaryAnalysisValueRepository = summaryAnalysisValueRepository;
        this.summaryRepository = summaryRepository;
        this.summaryValueRepository = summaryValueRepository;
        this.gstr3BRepository = gstr3BRepository;
        this.gstr3BValueRepository = gstr3BValueRepository;
        this.taxRepository = taxRepository;
        this.taxValueRepository = taxValueRepository;
        this.yearlyTaxRepository = yearlyTaxRepository;
        this.yearlyTaxValueRepository = yearlyTaxValueRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        Set<String> groupHeaders = GROUP_HEADERS_MAP.get(sheetTypeName);
        List<FieldDateMonthYear> fieldDateMonthYearList = Collections.emptyList();
        List<String> quarterList = Collections.emptyList();
        FieldDateMonthYear sheetDateMonthYear = null;
        SummaryRecordCarrier carrier = new SummaryRecordCarrier(sheetTypeName);
        boolean iteratingDateValues = false;

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();
            boolean foundDateValueGroup = false;

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (groupHeaders.stream().anyMatch(header -> StringUtils.equalsIgnoreCase(cellValue, header))
                        || DATE_RANGE_HEADERS.stream().anyMatch(header -> StringUtils.containsIgnoreCase(cellValue, header))) {
                    fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));
                    // each group will have date month year
                    // reinitialize list so that values for each field of this group can be set index based
                    fieldDateMonthYearList = Collections.emptyList();
                    quarterList = Collections.emptyList();
                    iteratingDateValues = DATE_HEADERS.contains(cellValue);
                    foundDateValueGroup = iteratingDateValues;

                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();

                        Cell firstCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String firstCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(firstCell));

                        // if group found, next row is expected to be headers and dates hence parse next row for dates
                        // for peak months and lull months this is not required
                        if (StringUtils.equalsIgnoreCase(firstCellValue, PARTICULARS)
                                || (iteratingDateValues && SheetReaderUtils.isValidDate(firstCellValue))) {
                            fieldDateMonthYearList = getFieldDateMonthYearList(row, dataFormatter, iteratingDateValues ? 0 : DEFAULT_COLUMN_COUNT);

                            if (rowIterator.hasNext()) {
                                row = rowIterator.next();

                                // for Summary sheet type, there can be another row specifying quarters
                                if (cellValue.equalsIgnoreCase(QUATERLY_SUMMARY)) {
                                    quarterList = getQuarterList(row, dataFormatter);

                                    // data will be present from next row
                                    if (rowIterator.hasNext()) {
                                        row = rowIterator.next();
                                    }
                                }
                            }
                        }
                    }
                    break;
                } else if (StringUtils.containsIgnoreCase(cellValue, SheetReaderUtils.PERIOD_COVERED)) {
                    // for the fields under the particulars column, a date is required to mark for which period this report stores data
                    // for this, parse the period covered string in the sheet header
                    sheetDateMonthYear = fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue);
                }
            }

            if (!fieldGroups.isEmpty() && !fieldDateMonthYearList.isEmpty()) {
                handleValueRecords(
                        fieldGroups,
                        row,
                        dataFormatter,
                        iteratingDateValues,
                        foundDateValueGroup,
                        carrier,
                        fieldDateMonthYearList,
                        sheetDateMonthYear,
                        quarterList,
                        clientOrder);
            }
        }

        saveAllRecords(sheetTypeName, fieldGroups, carrier);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    @SuppressWarnings("java:S107")
    private void handleValueRecords(
            List<FieldGroup> fieldGroups,
            Row row,
            DataFormatter dataFormatter,
            boolean iteratingDateValues,
            boolean foundDateValueGroup,
            SummaryRecordCarrier carrier,
            List<FieldDateMonthYear> fieldDateMonthYearList,
            FieldDateMonthYear sheetDateMonthYear,
            List<String> quarterList,
            ClientOrder clientOrder) {
        FieldGroup fieldGroup = fieldGroups.get(fieldGroups.size() - 1);
        int fieldCount = 0;
        // parse individual record
        for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

            if (StringUtils.isNotBlank(cellValue)) {
                if (SKIP_LIST.stream().anyMatch(skip -> StringUtils.containsIgnoreCase(cellValue, skip))) {
                    log.debug("Ignoring string {}", cellValue);
                    break;
                } else if (fieldCount == 0) {
                    // case for peak months and lull months group
                    if (iteratingDateValues) {
                        if (foundDateValueGroup) {
                            carrier.newRecord(fieldGroup, sheetDateMonthYear, clientOrder, fieldGroup.getFieldGroupName());
                        }
                        BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                        carrier.newValueRecord(fieldDateMonthYearList.get(fieldCount), cellValue, numericCellValue);
                    } else {
                        carrier.newRecord(fieldGroup, sheetDateMonthYear, clientOrder, cellValue);
                    }
                } else {
                    BigDecimal numericCellValue = SheetReaderUtils.getNumericCellValue(cell, cellValue);
                    // need to subtract default column count from field count for getting correct index for a field value
                    if (!quarterList.isEmpty()) {
                        carrier.newValueRecord(fieldDateMonthYearList.get((fieldCount - DEFAULT_COLUMN_COUNT) / QUARTERS),
                                cellValue, numericCellValue, quarterList.get(fieldCount - DEFAULT_COLUMN_COUNT));
                    } else {
                        if ((iteratingDateValues && fieldCount < fieldDateMonthYearList.size())
                                || (!iteratingDateValues && (fieldCount - DEFAULT_COLUMN_COUNT) < fieldDateMonthYearList.size())) {
                            FieldDateMonthYear fieldDateMonthYear = fieldDateMonthYearList.get(iteratingDateValues ? fieldCount : fieldCount - DEFAULT_COLUMN_COUNT);
                            carrier.newValueRecord(fieldDateMonthYear, cellValue, numericCellValue);
                        }
                    }
                }
                ++fieldCount;
            } else if (iteratingDateValues) {
                ++fieldCount;
            }
        }
    }

    private List<FieldDateMonthYear> getFieldDateMonthYearList(Row row, DataFormatter dataFormatter, int columnsToSkip) throws MasReportSheetReaderException {
        List<FieldDateMonthYear> fieldDateMonthYearList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + columnsToSkip; cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue) && SheetReaderUtils.isValidDate(cellValue)) {
                fieldDateMonthYearList.add(fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue));
            }
        }
        return fieldDateMonthYearList;
    }

    private List<String> getQuarterList(Row row, DataFormatter dataFormatter) {
        List<String> quarterList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + DEFAULT_COLUMN_COUNT; cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue)) {
                quarterList.add(cellValue);
            }
        }
        return quarterList;
    }

    private void saveAllRecords(SheetTypeName sheetTypeName, List<FieldGroup> fieldGroups, SummaryRecordCarrier carrier) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        if (SheetTypeName.SUMMARY_ANALYSIS == sheetTypeName) {
            log.debug("Saving {} SummaryAnalysis records", carrier.getSummaryAnalysisList().size());
            summaryAnalysisRepository.saveAll(carrier.getSummaryAnalysisList());

            log.debug("Saving {} SummaryAnalysisValue records", carrier.getSummaryAnalysisValueList().size());
            summaryAnalysisValueRepository.saveAll(carrier.getSummaryAnalysisValueList());
        } else if (SheetTypeName.SUMMARY == sheetTypeName) {
            log.debug("Saving {} Summary records", carrier.getSummaryList().size());
            summaryRepository.saveAll(carrier.getSummaryList());

            log.debug("Saving {} SummaryValue records", carrier.getSummaryValueList().size());
            summaryValueRepository.saveAll(carrier.getSummaryValueList());
        } else if (SheetTypeName.GSTR_3B == sheetTypeName) {
            log.debug("Saving {} GSTR3B records", carrier.getGstr3BList().size());
            gstr3BRepository.saveAll(carrier.getGstr3BList());

            log.debug("Saving {} GSTR3BValue records", carrier.getGstr3BValueList().size());
            gstr3BValueRepository.saveAll(carrier.getGstr3BValueList());
        } else if (SheetTypeName.TAX == sheetTypeName) {
            log.debug("Saving {} Tax records", carrier.getTaxList().size());
            taxRepository.saveAll(carrier.getTaxList());

            log.debug("Saving {} TaxValue records", carrier.getTaxValueList().size());
            taxValueRepository.saveAll(carrier.getTaxValueList());
        } else if (SheetTypeName.YEARLY_TAX == sheetTypeName) {
            log.debug("Saving {} YearlyTax records", carrier.getYearlyTaxList().size());
            yearlyTaxRepository.saveAll(carrier.getYearlyTaxList());

            log.debug("Saving {} YearlyTaxValue records", carrier.getYearlyTaxValueList().size());
            yearlyTaxValueRepository.saveAll(carrier.getYearlyTaxValueList());
        }
    }
}

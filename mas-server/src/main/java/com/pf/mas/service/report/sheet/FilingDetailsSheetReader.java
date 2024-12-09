package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.FilingDetail;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.FilingDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilingDetailsSheetReader implements SheetReader {
    private static final String FY_PREFIX = "FY ";
    private static final String FILING_DETAILS = "Filing Details";
    private static final String FINANCIAL_YEAR = "FINANCIAL YEAR";
    private static final String TAX_PERIOD = "TAX PERIOD";
    private static final String RETURN_TYPE = "RETURN TYPE";
    private static final String DUE_DATE = "DUE DATE";
    private static final String DATE_OF_FILING = "DATE OF FILING";
    private static final String DELAYED_DAYS = "DELAYED DAYS";
    private static final Set<String> FIELD_HEADERS = Set.of(FINANCIAL_YEAR, TAX_PERIOD, RETURN_TYPE, DUE_DATE, DATE_OF_FILING, DELAYED_DAYS);
    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final int DEFAULT_ROWS_TO_SKIP = 2;
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final FilingDetailRepository filingDetailRepository;

    public FilingDetailsSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            FilingDetailRepository filingDetailRepository) {
        this.fieldGroupRepository = fieldGroupRepository;
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.filingDetailRepository = filingDetailRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FilingDetail> filingDetails = new ArrayList<>();

        // parse only filing details, profile details are handled by ProfileSheetReader
        Pair<List<FieldGroup>, List<String>> listPair = readAndGetAllGroupsAndHeaders(sheet, dataFormatter, sheetType, clientOrder);
        List<FieldGroup> fieldGroups = listPair.getLeft();
        List<String> fieldHeadersList = listPair.getRight();
        Map<String, FieldGroup> fieldGroupNameMap = fieldGroups.stream().collect(Collectors.toMap(FieldGroup::getFieldGroupName, Function.identity()));

        // iterate sheet again
        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // if found any row containing header of filing details for any gst, then parse the sheet only for this table
                // when another table is found in same row then parse the sheet again only for this table
                // this is required since a row can have multiple filing details tables
                if (fieldGroupNameMap.get(cellValue) != null) {
                    filingDetails.addAll(getFilingDetails(sheet, row, cellIndex, dataFormatter, fieldHeadersList,
                            fieldGroupNameMap.keySet(), fieldGroupNameMap.get(cellValue), clientOrder));
                }
            }
        }

        saveAllRecords(fieldGroups, filingDetails);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    private Pair<List<FieldGroup>, List<String>> readAndGetAllGroupsAndHeaders(Sheet sheet, DataFormatter dataFormatter, SheetType sheetType, ClientOrder clientOrder) {
        List<FieldGroup> fieldGroups = new ArrayList<>();
        List<String> fieldHeadersList = new ArrayList<>();
        Set<String> fieldHeadersSet = new HashSet<>();

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                if (StringUtils.isNotBlank(cellValue)) {
                    // create groups and headers for filing details
                    // parse each row completely since one row can have multiple gsts' filing detail tables
                    if (StringUtils.containsIgnoreCase(cellValue, FILING_DETAILS)) {
                        fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));
                    } else if (FIELD_HEADERS.stream().anyMatch(header -> StringUtils.containsIgnoreCase(cellValue, header)) && !fieldHeadersSet.contains(cellValue)) {
                        fieldHeadersSet.add(cellValue);
                        fieldHeadersList.add(cellValue);
                    }
                }
            }
        }

        return Pair.of(fieldGroups, fieldHeadersList);
    }

    @SuppressWarnings("java:S107")
    private List<FilingDetail> getFilingDetails(
            Sheet sheet,
            Row currentRow,
            int currentCellIndex,
            DataFormatter dataFormatter,
            List<String> fieldHeadersList,
            Set<String> fieldGroupNameSet,
            FieldGroup fieldGroup,
            ClientOrder clientOrder) throws MasReportSheetReaderException {
        List<FilingDetail> filingDetailList = new ArrayList<>();
        // start parsing from the current row's index so that only the specific table is parsed
        for (int rowIndex = currentRow.getRowNum() + DEFAULT_ROWS_TO_SKIP; rowIndex < sheet.getLastRowNum(); ++rowIndex) {
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                Cell firstCell = row.getCell(currentCellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String firstCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(firstCell));

                // case to handle if end of table is reached
                if (StringUtils.isBlank(firstCellValue) || fieldGroupNameSet.contains(firstCellValue)) {
                    break;
                }

                // parse each row and set the filing details
                FilingDetail filingDetail = new FilingDetail();
                for (int cellIndex = currentCellIndex, fieldCount = 0; fieldCount < fieldHeadersList.size(); ++cellIndex, ++fieldCount) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
                    String header = fieldHeadersList.get(fieldCount);

                    setFilingDetailField(header, cellValue, filingDetail);
                }
                filingDetail.setFieldGroup(fieldGroup);
                filingDetail.setClientOrder(clientOrder);
                filingDetailList.add(filingDetail);
            }
        }
        return filingDetailList;
    }

    private void setFilingDetailField(String header, String cellValue, FilingDetail filingDetail) throws MasReportSheetReaderException {
        if (FINANCIAL_YEAR.equalsIgnoreCase(header)) {
            filingDetail.setFinancialYear(fieldDateMonthYearSheetReader.getFieldDateMonthYear(FY_PREFIX + cellValue));
        } else if (TAX_PERIOD.equalsIgnoreCase(header)) {
            filingDetail.setTaxPeriod(fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue));
        } else if (RETURN_TYPE.equalsIgnoreCase(header)) {
            filingDetail.setReturnType(cellValue);
        } else if (DUE_DATE.equalsIgnoreCase(header)) {
            filingDetail.setDueDate(SheetReaderUtils.parseDateValue(cellValue, DD_MM_YYYY));
        } else if (DATE_OF_FILING.equalsIgnoreCase(header)) {
            filingDetail.setDateOfFiling(SheetReaderUtils.parseDateValue(cellValue, DD_MM_YYYY));
        } else if (DELAYED_DAYS.equalsIgnoreCase(header)) {
            filingDetail.setDelayedDays(SheetReaderUtils.parseIntegerValue(cellValue));
        }
    }

    private void saveAllRecords(List<FieldGroup> fieldGroups, List<FilingDetail> filingDetails) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        log.debug("Saving {} FilingDetail records", filingDetails.size());
        filingDetailRepository.saveAll(filingDetails);
    }
}

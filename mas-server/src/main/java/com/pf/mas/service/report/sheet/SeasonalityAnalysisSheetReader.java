package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.SeasonalityAnalysis;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.SeasonalityAnalysisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SeasonalityAnalysisSheetReader implements SheetReader {
    private static final Set<String> GROUP_HEADERS = Set.of("Peak Months", "Lull Months");
    private final FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader;
    private final FieldGroupRepository fieldGroupRepository;
    private final SeasonalityAnalysisRepository seasonalityAnalysisRepository;

    public SeasonalityAnalysisSheetReader(
            FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader,
            FieldGroupRepository fieldGroupRepository,
            SeasonalityAnalysisRepository seasonalityAnalysisRepository) {
        this.fieldDateMonthYearSheetReader = fieldDateMonthYearSheetReader;
        this.fieldGroupRepository = fieldGroupRepository;
        this.seasonalityAnalysisRepository = seasonalityAnalysisRepository;
    }

    @Override
    public void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<SeasonalityAnalysis> records = new ArrayList<>();
        List<FieldGroup> fieldGroups = new ArrayList<>();
        List<FieldDateMonthYear> fieldDateMonthYearList = Collections.emptyList();
        List<FieldDateMonthYear> monthList = Collections.emptyList();

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // check for group headers
                if (GROUP_HEADERS.stream().anyMatch(cellValue::equalsIgnoreCase)) {
                    fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));

                    // if group found, next row is expected to be headers and dates hence parse next row for dates
                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();
                        fieldDateMonthYearList = getFieldDateMonthYearList(row, dataFormatter);

                        // here next row is expected to be months hence parse next row
                        if (rowIterator.hasNext()) {
                            row = rowIterator.next();
                            monthList = getFieldDateMonthYearList(row, dataFormatter);

                            // data will be present from next row
                            if (rowIterator.hasNext()) {
                                row = rowIterator.next();
                            }
                        }
                    }
                    break;
                }
            }

            if (!fieldDateMonthYearList.isEmpty() && !monthList.isEmpty()) {
                FieldGroup fieldGroup = fieldGroups.get(fieldGroups.size() - 1);

                String particulars = null;
                for (int cellIndex = row.getFirstCellNum(), fieldCount = 0; cellIndex < row.getLastCellNum(); ++cellIndex, ++fieldCount) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                    if (StringUtils.isNotBlank(cellValue)) {
                        // first column is the name of the particular
                        // next columns would be the field values
                        if (fieldCount == 0) {
                            particulars = cellValue;
                        } else {
                            FieldDateMonthYear month = monthList.get(fieldCount - 1);
                            SeasonalityAnalysis seasonalityAnalysis = new SeasonalityAnalysis();
                            seasonalityAnalysis.setParticulars(particulars);
                            seasonalityAnalysis.setFieldGroup(fieldGroup);
                            seasonalityAnalysis.setFieldDateMonthYear(SheetReaderUtils.getFYFieldDateMonthYearForMonth(fieldDateMonthYearList, month));
                            seasonalityAnalysis.setClientOrder(clientOrder);
                            seasonalityAnalysis.setValue(cellValue);
                            seasonalityAnalysis.setValueNumeric(SheetReaderUtils.getNumericCellValue(cell, cellValue));
                            seasonalityAnalysis.setMonth(month);
                            records.add(seasonalityAnalysis);
                        }
                    }
                }
            }
        }

        saveAllRecords(fieldGroups, records);
        log.debug("Completed sheet parsing for sheet {} for client order {}", sheetTypeName.getName(), clientOrder);
    }

    private List<FieldDateMonthYear> getFieldDateMonthYearList(Row row, DataFormatter dataFormatter) throws MasReportSheetReaderException {
        List<FieldDateMonthYear> fieldDateMonthYearList = new ArrayList<>();
        for (int cellIndex = row.getFirstCellNum() + 1; cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.isNotBlank(cellValue) && SheetReaderUtils.isValidDate(cellValue)) {
                fieldDateMonthYearList.add(fieldDateMonthYearSheetReader.getFieldDateMonthYear(cellValue));
            }
        }
        return fieldDateMonthYearList;
    }

    private void saveAllRecords(List<FieldGroup> fieldGroups, List<SeasonalityAnalysis> records) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        log.debug("Saving {} SeasonalityAnalysis records", records.size());
        seasonalityAnalysisRepository.saveAll(records);
    }
}

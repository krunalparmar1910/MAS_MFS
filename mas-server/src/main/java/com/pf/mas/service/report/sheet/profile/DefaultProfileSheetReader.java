package com.pf.mas.service.report.sheet.profile;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.ProfileDetailRepository;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
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

@Slf4j
@Service
public class DefaultProfileSheetReader implements ProfileSheetReader {
    private static final String PROFILE = "Profile";
    private static final String PROFILE_DETAILS = "Profile Details";
    private final FieldGroupRepository fieldGroupRepository;
    private final ProfileDetailRepository profileDetailRepository;

    public DefaultProfileSheetReader(
            FieldGroupRepository fieldGroupRepository,
            ProfileDetailRepository profileDetailRepository) {
        this.fieldGroupRepository = fieldGroupRepository;
        this.profileDetailRepository = profileDetailRepository;
    }

    @Override
    public List<ProfileDetail> readSheetAndStoreData(Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.debug("Starting sheet parsing for Profile Details for client order {}", clientOrder);

        DataFormatter dataFormatter = new DataFormatter();
        List<FieldGroup> fieldGroups = Collections.emptyList();
        List<String> fieldHeaders = Collections.emptyList();
        List<ProfileDetail> profileDetails = Collections.emptyList();
        int fieldCount = 0;

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row row = rowIterator.next();

            for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); ++cellIndex) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                // parse individual profile, cell may contain gst number as well hence check with contains
                if (StringUtils.containsIgnoreCase(cellValue, PROFILE)) {
                    // get all profiles present in this row
                    fieldGroups = getProfileGroup(row, dataFormatter, sheetType, cellIndex + 1, clientOrder);
                    profileDetails = initProfileDetails(fieldGroups, clientOrder);

                    if (rowIterator.hasNext()) {
                        row = rowIterator.next();
                        // store all fields found in first cell of each row
                        // this is needed as one row may contain more than one profile details for consolidated reports
                        fieldHeaders = getFieldHeaders(sheet, row, dataFormatter);
                        fieldCount = 0;
                    }
                    break;
                }
            }

            if (!profileDetails.isEmpty()) {
                int groupIndex = 0;
                // for each row after header, store the profile details
                for (int cellIndex = row.getFirstCellNum() + 1; cellIndex < row.getLastCellNum(); ++cellIndex) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));

                    if (StringUtils.isNotBlank(cellValue)) {
                        boolean fieldUpdated = ProfileDetail.setValueForFieldHeader(fieldHeaders.get(fieldCount), profileDetails.get(groupIndex), cellValue);
                        // check if any field was updated to update the group index
                        // this is to avoid invalid fields and empty cells in the sheet
                        // as two profile details have some empty columns between them
                        if (fieldUpdated) {
                            ++groupIndex;
                        }
                    }
                }
            }

            ++fieldCount;
            if (fieldCount == fieldHeaders.size()) {
                break;
            }
        }

        List<ProfileDetail> profileDetailList = saveAllRecords(fieldGroups, profileDetails);
        log.debug("Completed sheet parsing for Profile Details for client order {}", clientOrder);
        return profileDetailList;
    }

    private List<FieldGroup> getProfileGroup(Row row, DataFormatter dataFormatter, SheetType sheetType, int startIndex, ClientOrder clientOrder) {
        List<FieldGroup> fieldGroups = new ArrayList<>();
        for (int cellIndex = startIndex; cellIndex < row.getLastCellNum(); ++cellIndex) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(cell));
            if (StringUtils.containsIgnoreCase(cellValue, PROFILE_DETAILS)) {
                fieldGroups.add(SheetReaderUtils.getNewFieldGroup(cellValue, sheetType, clientOrder));
            }
        }
        // case for single reports
        if (fieldGroups.isEmpty()) {
            fieldGroups.add(SheetReaderUtils.getNewFieldGroup(PROFILE, sheetType, clientOrder));
        }
        return fieldGroups;
    }

    private List<ProfileDetail> initProfileDetails(List<FieldGroup> fieldGroups, ClientOrder clientOrder) {
        List<ProfileDetail> profileDetails = new ArrayList<>();
        for (FieldGroup fieldGroup : fieldGroups) {
            ProfileDetail profileDetail = new ProfileDetail();
            profileDetail.setFieldGroup(fieldGroup);
            profileDetail.setClientOrder(clientOrder);
            profileDetails.add(profileDetail);
        }
        return profileDetails;
    }

    private List<String> getFieldHeaders(Sheet sheet, Row currentRow, DataFormatter dataFormatter) {
        List<String> fieldHeaders = new ArrayList<>();
        for (int rowIndex = currentRow.getRowNum(); rowIndex < sheet.getLastRowNum(); ++rowIndex) {
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                Cell firstCell = row.getCell(row.getFirstCellNum(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String firstCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(firstCell));

                if (StringUtils.isNotBlank(firstCellValue)) {
                    fieldHeaders.add(firstCellValue);
                } else {
                    break;
                }
            }
        }
        return fieldHeaders;
    }

    private List<ProfileDetail> saveAllRecords(List<FieldGroup> fieldGroups, List<ProfileDetail> profileDetails) {
        log.debug("Saving {} FieldGroup records", fieldGroups.size());
        fieldGroupRepository.saveAll(fieldGroups);

        log.debug("Saving {} ProfileDetail records", fieldGroups.size());
        return profileDetailRepository.saveAll(profileDetails);
    }
}

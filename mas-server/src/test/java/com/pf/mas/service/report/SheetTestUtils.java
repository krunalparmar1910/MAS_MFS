package com.pf.mas.service.report;

import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.base.BaseSheetBifurcatedValueEntity;
import com.pf.mas.model.entity.base.BaseSheetDateValueEntity;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import com.pf.mas.model.entity.base.BaseSheetValueEntity;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class SheetTestUtils {
    public static final List<String> SINGLE_REPORT_FILES = List.of("gst_report_single.xlsx", "gst_report_single_yearly_tax.xlsx");
    public static final List<String> CONSOLIDATED_REPORT_FILES = List.of("gst_report_consolidated.xlsx", "gst_report_consolidated_yearly_tax.xlsx");
    public static final String INVALID_REPORT_FILE = "gst_report_invalid.xlsx";
    private static final String BASE_REPORT_PATH = "src/test/resources/report/";

    private SheetTestUtils() {
        // utils class
    }

    public static String getFilePath(String fileName) {
        File file = new File(BASE_REPORT_PATH + fileName);
        assertTrue(file.exists());
        return file.getPath();
    }

    public static byte[] getFileData(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return fileInputStream.readAllBytes();
        }
    }

    public static Map<SheetTypeName, Sheet> readSheetsAndGetSheetTypeMap(String filePath) throws IOException {
        Map<SheetTypeName, Sheet> sheetMap = new EnumMap<>(SheetTypeName.class);
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);

        for (Sheet sheet : workbook) {
            SheetTypeName sheetTypeName = SheetTypeName.getFromSheetName(sheet.getSheetName());
            if (sheetTypeName != null) {
                sheetMap.put(sheetTypeName, sheet);
            }
        }
        return sheetMap;
    }

    public static void validateFieldGroups(List<FieldGroup> fieldGroups, int expectedSize) {
        assertNotNull(fieldGroups);
        assertEquals(expectedSize, fieldGroups.size());
        for (FieldGroup fieldGroup : fieldGroups) {
            assertNotNull(fieldGroup.getFieldGroupName());
            assertNotNull(fieldGroup.getSheetType());
            assertNotNull(fieldGroup.getClientOrder());
        }
    }

    public static <T extends BaseSheetEntity> void validateNonNullFields(List<T> captorList, int expectedSize, String... fieldsToIgnore) {
        validateNonNullFields(captorList, expectedSize, true, fieldsToIgnore);
    }

    public static <T extends BaseSheetEntity> void validateNonNullFields(List<T> captorList, int expectedSize, boolean expectFieldDateMonthYear, String... fieldsToIgnore) {
        assertNotNull(captorList);
        assertEquals(expectedSize, captorList.size());
        for (T value : captorList) {
            Assertions.assertThat(value).hasNoNullFieldsOrPropertiesExcept(fieldsToIgnore);
            assertNotNull(value.getClientOrder());
            assertNotNull(value.getFieldGroup());
            if (expectFieldDateMonthYear) {
                assertNotNull(value.getFieldDateMonthYear());
            }
        }
    }

    public static <T extends BaseSheetValueEntity> void validateNonNullValueFields(List<T> captorList, int expectedSize, String... fieldsToIgnore) {
        assertNotNull(captorList);
        assertEquals(expectedSize, captorList.size());
        for (T value : captorList) {
            Assertions.assertThat(value).hasNoNullFieldsOrPropertiesExcept(fieldsToIgnore);
            assertNotNull(value.getFieldDateMonthYear());
            assertNotNull(value.getFieldValue());
            if (expectNumericValue(value.getFieldValue())) {
                assertNotNull(value.getFieldValueNumeric(), "field value: " + value.getFieldValue());
            }
        }
    }

    public static <T extends BaseSheetBifurcatedValueEntity> void validateNonNullBifurcatedValueFields(
            List<T> captorList, int expectedSize, boolean validateRevenue, String... fieldsToIgnore) {
        assertNotNull(captorList);
        assertEquals(expectedSize, captorList.size());
        for (T value : captorList) {
            Assertions.assertThat(value).hasNoNullFieldsOrPropertiesExcept(fieldsToIgnore);
            assertNotNull(value.getFieldDateMonthYear());
            assertNotNull(value.getAmount());
            if (expectNumericValue(value.getAmount())) {
                assertNotNull(value.getAmountNumeric(), "field value: " + value.getAmount());
            }

            if (validateRevenue) {
                assertNotNull(value.getPercentShareInAdjustedRevenue());
                if (expectNumericValue(value.getPercentShareInAdjustedRevenue())) {
                    assertNotNull(value.getPercentShareInAdjustedRevenueNumeric(), "field value: " + value.getPercentShareInAdjustedRevenue());
                }
            } else {
                assertNotNull(value.getPercentShareInAdjustedPurchasesExpenses());
                if (expectNumericValue(value.getPercentShareInAdjustedPurchasesExpenses())) {
                    assertNotNull(value.getPercentShareInAdjustedPurchasesExpensesNumeric(), "field value: " + value.getPercentShareInAdjustedPurchasesExpenses());
                }
            }
        }
    }

    public static <T extends BaseSheetDateValueEntity> void validateNonNullSheetDateValueFields(List<T> captorList, int expectedSize, String... fieldsToIgnore) {
        assertNotNull(captorList);
        assertEquals(expectedSize, captorList.size());
        for (T value : captorList) {
            Assertions.assertThat(value).hasNoNullFieldsOrPropertiesExcept(fieldsToIgnore);
            assertNotNull(value.getFieldDateMonthYear());
        }
    }

    public static boolean expectNumericValue(String value) {
        return SheetReaderUtils.parseNumericValue(value) != null;
    }

    @Getter
    @Builder
    @ToString(exclude = {"sheetMap"})
    public static class SheetReaderTestParams {
        private final Map<SheetTypeName, Sheet> sheetMap;
        private final SheetTypeName sheetTypeName;
        private final int expectedGroups;
        private final int expectedRecords;
        private final int expectedTotalFields;
        private final int expectedValueFields;
        private final boolean invalidData;
    }
}

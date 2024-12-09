package com.pf.mas.service.report;

import com.pf.mas.model.entity.SheetTypeName;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public final class SheetProvider {
    private static final SheetProvider INSTANCE = new SheetProvider();
    @Getter
    private final Map<SheetTypeName, Sheet> sheetMapSingle1;
    @Getter
    private final Map<SheetTypeName, Sheet> sheetMapSingle2;
    @Getter
    private final Map<SheetTypeName, Sheet> sheetMapConsolidated1;
    @Getter
    private final Map<SheetTypeName, Sheet> sheetMapConsolidated2;
    @Getter
    private final Map<SheetTypeName, Sheet> sheetMapInvalid;
    @Getter
    private final String sheetMapSingle1FileName;
    @Getter
    private final String sheetMapSingle2FileName;
    @Getter
    private final String sheetMapConsolidated1FileName;
    @Getter
    private final String sheetMapConsolidated2FileName;
    @Getter
    private final String sheetMapInvalidFileName;

    private SheetProvider() {
        try {
            sheetMapSingle1 = Collections.unmodifiableMap(
                    SheetTestUtils.readSheetsAndGetSheetTypeMap(SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(0))));
            sheetMapSingle1FileName = SheetTestUtils.SINGLE_REPORT_FILES.get(0);

            sheetMapSingle2 = Collections.unmodifiableMap(
                    SheetTestUtils.readSheetsAndGetSheetTypeMap(SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(1))));
            sheetMapSingle2FileName = SheetTestUtils.SINGLE_REPORT_FILES.get(1);

            sheetMapConsolidated1 = Collections.unmodifiableMap(
                    SheetTestUtils.readSheetsAndGetSheetTypeMap(SheetTestUtils.getFilePath(SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(0))));
            sheetMapConsolidated1FileName = SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(0);

            sheetMapConsolidated2 = Collections.unmodifiableMap(
                    SheetTestUtils.readSheetsAndGetSheetTypeMap(SheetTestUtils.getFilePath(SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(1))));
            sheetMapConsolidated2FileName = SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(1);

            sheetMapInvalid = Collections.unmodifiableMap(
                    SheetTestUtils.readSheetsAndGetSheetTypeMap(SheetTestUtils.getFilePath(SheetTestUtils.INVALID_REPORT_FILE)));
            sheetMapInvalidFileName = SheetTestUtils.INVALID_REPORT_FILE;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SheetProvider getInstance() {
        return INSTANCE;
    }
}

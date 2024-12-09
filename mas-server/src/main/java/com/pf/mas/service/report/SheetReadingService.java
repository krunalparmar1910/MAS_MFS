package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public interface SheetReadingService {
    void readAllSheetsAndStoreData(
            Map<SheetTypeName, Sheet> sheetMap,
            Map<SheetTypeName, SheetType> sheetTypeMap,
            ClientOrder clientOrder) throws MasReportSheetReaderException;
}

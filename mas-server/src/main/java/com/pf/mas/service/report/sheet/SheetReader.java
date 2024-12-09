package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetReader {
    void readSheetAndStoreData(SheetTypeName sheetTypeName, Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException;
}

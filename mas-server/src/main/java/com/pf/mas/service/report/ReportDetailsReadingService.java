package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetTypeName;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public interface ReportDetailsReadingService {
    void readAndStoreReportDetails(String reportFileName, Map<SheetTypeName, Sheet> sheetMap, ClientOrder clientOrder) throws MasReportSheetReaderException;
}

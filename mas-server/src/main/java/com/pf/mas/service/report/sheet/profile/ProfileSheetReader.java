package com.pf.mas.service.report.sheet.profile;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface ProfileSheetReader {
    List<ProfileDetail> readSheetAndStoreData(Sheet sheet, SheetType sheetType, ClientOrder clientOrder) throws MasReportSheetReaderException;
}

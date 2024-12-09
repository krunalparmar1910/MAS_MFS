package com.pf.mas.service.report;

import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.sheet.ProfileDetail;

import java.util.List;

public interface ReportReadingService {
    ClientOrder getOrCreateNewClientOrder(String entityId, String clientOrderId, String createdBy, String ipAddress) throws MasGSTNoEntityFoundException;

    List<ProfileDetail> readAndStoreReport(ClientOrder clientOrder, String fileName, byte[] fileData) throws MasReportSheetReaderException;
}

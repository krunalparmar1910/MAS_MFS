package com.pf.mas.service.gst;

import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.UpdateGST3BReportManualEntryRequest;
import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasGetGST3BReportException;

import java.util.List;

public interface GSTReportService {
    GSTReport getGSTReport(String entityId, List<String> clientOrderIds) throws MasGSTNoEntityFoundException, MasGetGST3BReportException;

    GSTReport updateManualEntriesInGST3BReport(UpdateGST3BReportManualEntryRequest updateRequest) throws MasGSTNoEntityFoundException, MasGetGST3BReportException;
}

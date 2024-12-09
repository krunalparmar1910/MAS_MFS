package com.pf.mas.service.corpository;

import com.pf.corpository.model.MasGetReportRequest;
import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasGetReportResponseException;
import com.pf.mas.exception.MasReportSheetReaderException;

public interface CorpositoryReportService {
    void readGetReportResponseAndStore(MasGetReportRequest request, byte[] response, String createdBy, String ipAddress)
            throws MasGetReportResponseException, MasReportSheetReaderException, MasGSTNoEntityFoundException;
}

package com.pf.mas.service.user;

import com.pf.mas.dto.report.GSTReportProfileDetail;
import com.pf.mas.dto.user.GetGST3BReportRequestHistoryResponseDTO;
import com.pf.mas.exception.MasJSONException;

import java.util.List;

public interface UserInfoService {
    GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistory(String username) throws MasJSONException;

    GetGST3BReportRequestHistoryResponseDTO updateGST3BReportRequestHistory(
            String username,
            String entityId,
            List<String> clientOrderIds,
            List<GSTReportProfileDetail> gstReportProfileDetails) throws MasJSONException;
}

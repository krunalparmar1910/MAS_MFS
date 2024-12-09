package com.pf.corpository.service;

import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.corpository.exception.MasRequestException;
import com.pf.corpository.model.MasGetReportRequest;
import com.pf.corpository.model.OrderStatusRequest;
import com.pf.corpository.model.OrderStatusResponse;

public interface CorpositoryService {
    OrderStatusResponse getOrderStatus(OrderStatusRequest request) throws MasRequestException, MasThirdPartyApiException;

    byte[] getReport(MasGetReportRequest request) throws MasRequestException, MasThirdPartyApiException;
}

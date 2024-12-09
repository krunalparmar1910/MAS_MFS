package com.pf.mas.controller;

import com.pf.common.exception.MasRuntimeException;
import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.corpository.exception.MasRequestException;
import com.pf.corpository.model.MasGetReportRequest;
import com.pf.corpository.model.OrderStatusRequest;
import com.pf.corpository.model.OrderStatusResponse;
import com.pf.corpository.service.CorpositoryService;
import com.pf.mas.service.corpository.CorpositoryReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/corpository-api")
public class CorpositoryController {
    private final AsyncTaskExecutor taskExecutor;
    private final CorpositoryService corpositoryService;
    private final CorpositoryReportService corpositoryReportService;

    public CorpositoryController(
            AsyncTaskExecutor taskExecutor,
            CorpositoryService corpositoryService,
            CorpositoryReportService corpositoryReportService) {
        this.taskExecutor = taskExecutor;
        this.corpositoryService = corpositoryService;
        this.corpositoryReportService = corpositoryReportService;
    }

    @PostMapping("/order-status")
    public OrderStatusResponse getOrderStatus(@RequestBody OrderStatusRequest request) throws MasRequestException, MasThirdPartyApiException {
        return corpositoryService.getOrderStatus(request);
    }

    @PostMapping("/get-report")
    public byte[] getReport(
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "ip-address", required = false) String ipAddress,
            @RequestBody MasGetReportRequest request) throws MasRequestException, MasThirdPartyApiException {
        log.debug("Received get-report call with params, createdBy {}, ipAddress {}, request {}", createdBy, ipAddress, request);
        log.info("Calling get-report for request {}", request);
        byte[] response = corpositoryService.getReport(request);

        // asynchronously parsing the response to maintain synchronous nature of api response
        log.info("Received response from Corpository for request {}, parsing report response separately", request);
        try {
            taskExecutor.execute(() -> {
                try {
                    corpositoryReportService.readGetReportResponseAndStore(request, response, createdBy, ipAddress);
                    log.info("Response for {} was parsed and stored successfully", request);
                } catch (Exception e) {
                    log.error("Unexpected error for reading report for request {}, cause {}", request, e.getLocalizedMessage());
                    throw new MasRuntimeException(e);
                }
            });
        } catch (Exception e) {
            log.error("Unexpected error while submitting task {}, could not parse report and save", e.getLocalizedMessage());
        }

        return response;
    }
}

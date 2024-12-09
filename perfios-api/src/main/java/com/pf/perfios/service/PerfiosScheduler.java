package com.pf.perfios.service;

import com.pf.perfios.model.dto.TransactionCallBackDTO;
import com.pf.perfios.model.entity.MasRequests;
import com.pf.perfios.repository.MasRequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfiosScheduler {

    private final PerfiosService perfiosService;

    private final MasRequestsRepository masRequestsRepository;

    @Value("${perfios.report.expiryDays}")
    private Integer expiryDays;

    @Scheduled(cron = "${perfios.cron.reportExpiry}")
    private void checkNonFetchedReports(){
        log.info("Running check for expired reports");
        List<MasRequests> masRequestsList = masRequestsRepository.findByReportFetchedFalseAndCustomerInfoIdNotNullAndReportExpiredFalse();

        masRequestsList.parallelStream().forEach(this::notifyMas);
    }

    private void notifyMas(MasRequests masRequest) {
        log.info("notifying MAS of transactionId {} days difference in expiry: {}", masRequest.getPerfiosTransactionId(), ChronoUnit.DAYS.between(masRequest.getCustomerInfo().getCreatedTimestamp(), Instant.now()));

        long differenceInDays = ChronoUnit.DAYS.between(masRequest.getCustomerInfo().getCreatedTimestamp(), Instant.now());
        if(differenceInDays >= 0 && differenceInDays < expiryDays) {
            TransactionCallBackDTO transactionCallBackDTO =
                    TransactionCallBackDTO.builder()
                            .clientTransactionId(masRequest.getCustomerTransactionId())
                            .perfiosTransactionId(masRequest.getPerfiosTransactionId())
                            .masFinancialId(masRequest.getMasFinancialId())
                            .errorCode("")
                            .errorMessage("")
                            .status("FETCH_REPORT_BEFORE_EXPIRY")
                            .build();

            perfiosService.notifyMas(masRequest, transactionCallBackDTO);
        } else if(differenceInDays > expiryDays) {
            masRequest.setReportExpired(true);
            masRequestsRepository.save(masRequest);
        }

        log.info("Notified MAS to fetch report for perfiosTransactionId: {} ", masRequest.getPerfiosTransactionId());
    }

}

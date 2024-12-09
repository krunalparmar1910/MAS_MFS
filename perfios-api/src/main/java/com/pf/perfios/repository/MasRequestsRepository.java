package com.pf.perfios.repository;

import com.pf.perfios.model.entity.MasRequests;
import com.pf.perfios.model.entity.MasWebhookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasRequestsRepository extends JpaRepository<MasRequests, Long> {

    Optional<MasRequests> findByPerfiosTransactionId(String perfiosTransactionId);

    MasRequests findByPerfiosTransactionIdAndMasFinancialId(String transactionId, String masFinancialId);

    List<MasRequests> findByReportFetchedFalseAndCustomerInfoIdNotNullAndReportExpiredFalse();

    List<MasRequests> findByMasWebhookStatus(MasWebhookStatus callbackFailed);

    Optional<MasRequests> findByMasFinancialIdAndCustomerTransactionId(String masFinancialId, String customerTransactionId);

    List<MasRequests> findByUniqueFirmId(String uniqueFirmId);

}

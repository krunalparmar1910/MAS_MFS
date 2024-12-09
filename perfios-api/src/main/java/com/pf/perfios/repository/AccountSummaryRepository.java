package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountSummaryRepository extends JpaRepository<AccountSummary, Long> {
    AccountSummary findByCustomerInfoAndAccountNumber(CustomerInfo customerInfo, String accountNo);

    AccountSummary findFirstByCustomerInfo(CustomerInfo customerInfo);

    List<AccountSummary> findByCustomerInfo(CustomerInfo customerInfo);

    List<AccountSummary> findByCustomerInfoIn(List<CustomerInfo> customerInfoList);

    List<AccountSummary> findByCustomerInfo_CustomerTransactionIdAndAccountNumberIn(String customerTransactionId, List<String> accountNumberList);

    List<AccountSummary> findByCustomerInfo_CustomerTransactionId(String customerTransactionId);

    Optional<AccountSummary> findByUuid(UUID accountSummaryUuid);
}

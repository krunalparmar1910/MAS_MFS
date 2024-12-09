package com.pf.perfios.repository;

import com.pf.perfios.model.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
    CustomerInfo findByPerfiosTransactionId(String transactionId);

    List<CustomerInfo> findByMasFinancialId(String masFinancialId);

    CustomerInfo findByCustomerTransactionId(String customerTransactionId);

    Optional<CustomerInfo> findByMasFinancialIdAndCustomerTransactionId(String masFinancialId, String customerTransactionId);

    List<CustomerInfo> findByCustomerTransactionIdIn( List<String> customerIdList);
}

package com.pf.mas.repository;

import com.pf.mas.model.entity.ClientOrderReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientOrderReportDetailsRepository extends JpaRepository<ClientOrderReportDetails, Long> {
    List<ClientOrderReportDetails> findByClientOrderId(Long clientOrderId);
}

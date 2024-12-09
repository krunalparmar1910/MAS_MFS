package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.DetailType;
import com.pf.perfios.model.entity.MonthwiseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonthwiseDetailsRepository extends JpaRepository<MonthwiseDetails, Long> {
    List<MonthwiseDetails> findByAccountSummaryAndType(AccountSummary accountSummary, DetailType none);

    Optional<MonthwiseDetails> findByUuid(UUID monthwiseDetailUuid);
}

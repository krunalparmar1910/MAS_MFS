package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {
    List<Transactions> findByAccountSummary(AccountSummary accountSummary);

    List<Transactions> findByAccountSummaryAndDateGreaterThanEqualAndDateLessThan(AccountSummary accountSummary, LocalDate startDate, LocalDate endDate);

    Optional<Transactions> findByUuid(UUID transactionUuid);
}

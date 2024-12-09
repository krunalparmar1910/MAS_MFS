package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.DerivedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DerivedTransactionsRepository extends JpaRepository<DerivedTransactions, Long> {
    List<DerivedTransactions> findByAccountSummaryAndDateGreaterThanEqualAndDateLessThanAndCategory(
            AccountSummary accountSummary, LocalDate startDate, LocalDate endDate, String category);
}

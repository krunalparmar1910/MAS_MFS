package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CircularTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularTransactionsRepository extends JpaRepository<CircularTransactions, Long> {
}

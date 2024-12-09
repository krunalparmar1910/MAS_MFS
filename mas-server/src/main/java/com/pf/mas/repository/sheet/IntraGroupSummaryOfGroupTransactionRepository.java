package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.IntraGroupSummaryOfGroupTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntraGroupSummaryOfGroupTransactionRepository extends JpaRepository<IntraGroupSummaryOfGroupTransaction, Long> {
}

package com.pf.mas.repository;

import com.pf.mas.model.entity.GSTReportManualBankingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTReportManualBankingEntryRepository extends JpaRepository<GSTReportManualBankingEntry, Long> {
}

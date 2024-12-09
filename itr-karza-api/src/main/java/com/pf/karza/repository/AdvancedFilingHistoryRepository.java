package com.pf.karza.repository;

import com.pf.karza.model.entity.advanced.filinghistory.AdvancedFilingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancedFilingHistoryRepository extends JpaRepository<AdvancedFilingHistory, Long> {
}
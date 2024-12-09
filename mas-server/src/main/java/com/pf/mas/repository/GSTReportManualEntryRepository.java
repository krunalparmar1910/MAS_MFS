package com.pf.mas.repository;

import com.pf.mas.model.entity.GSTReportManualEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTReportManualEntryRepository extends JpaRepository<GSTReportManualEntry, Long> {
    GSTReportManualEntry findByClientOrderId(Long clientOrderId);
}

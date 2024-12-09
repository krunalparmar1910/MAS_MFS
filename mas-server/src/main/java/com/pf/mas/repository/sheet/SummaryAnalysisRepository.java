package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SummaryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryAnalysisRepository extends JpaRepository<SummaryAnalysis, Long> {
}

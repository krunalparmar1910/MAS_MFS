package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SummaryAnalysisValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryAnalysisValueRepository extends JpaRepository<SummaryAnalysisValue, Long> {
}

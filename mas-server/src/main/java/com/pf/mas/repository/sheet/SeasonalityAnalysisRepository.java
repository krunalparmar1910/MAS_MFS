package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SeasonalityAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonalityAnalysisRepository extends JpaRepository<SeasonalityAnalysis, Long> {
}

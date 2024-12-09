package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.YearlyReturnSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyReturnSummaryRepository extends JpaRepository<YearlyReturnSummary, Long> {
}

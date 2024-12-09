package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.YearlyReturnSummaryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyReturnSummaryValueRepository extends JpaRepository<YearlyReturnSummaryValue, Long> {
}

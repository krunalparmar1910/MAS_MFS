package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SummaryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryValueRepository extends JpaRepository<SummaryValue, Long> {
}

package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.YearlyTaxValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyTaxValueRepository extends JpaRepository<YearlyTaxValue, Long> {
}

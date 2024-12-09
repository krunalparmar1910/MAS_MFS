package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.YearlyTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyTaxRepository extends JpaRepository<YearlyTax, Long> {
}

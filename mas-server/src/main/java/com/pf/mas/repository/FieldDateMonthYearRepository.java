package com.pf.mas.repository;

import com.pf.mas.model.entity.FieldDateMonthYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDateMonthYearRepository extends JpaRepository<FieldDateMonthYear, Long> {
    FieldDateMonthYear findByFieldDateValue(String fieldDateValue);
}

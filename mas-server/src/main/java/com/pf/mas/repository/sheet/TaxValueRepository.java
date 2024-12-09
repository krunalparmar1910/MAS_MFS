package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.TaxValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxValueRepository extends JpaRepository<TaxValue, Long> {
}

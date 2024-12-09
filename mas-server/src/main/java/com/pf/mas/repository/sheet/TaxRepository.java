package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}

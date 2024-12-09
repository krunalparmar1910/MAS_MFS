package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.AdjustedAmountsValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustedAmountsValueRepository extends JpaRepository<AdjustedAmountsValue, Long> {
}

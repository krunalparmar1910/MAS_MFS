package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.BifurcationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BifurcationValueRepository extends JpaRepository<BifurcationValue, Long> {
}

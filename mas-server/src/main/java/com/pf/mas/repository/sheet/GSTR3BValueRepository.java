package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.GSTR3BValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTR3BValueRepository extends JpaRepository<GSTR3BValue, Long> {
}

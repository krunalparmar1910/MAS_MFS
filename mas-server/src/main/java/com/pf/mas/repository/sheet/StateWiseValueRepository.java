package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.StateWiseValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateWiseValueRepository extends JpaRepository<StateWiseValue, Long> {
}

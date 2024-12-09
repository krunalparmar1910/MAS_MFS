package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.StateWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateWiseRepository extends JpaRepository<StateWise, Long> {
}

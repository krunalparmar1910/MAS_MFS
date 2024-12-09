package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.IntraGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntraGroupRepository extends JpaRepository<IntraGroup, Long> {
}

package com.pf.mas.repository;

import com.pf.mas.model.entity.SheetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetTypeRepository extends JpaRepository<SheetType, Long> {
}

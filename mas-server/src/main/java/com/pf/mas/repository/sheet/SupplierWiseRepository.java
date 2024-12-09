package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SupplierWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierWiseRepository extends JpaRepository<SupplierWise, Long> {
}

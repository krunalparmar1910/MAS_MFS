package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SupplierWiseValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierWiseValueRepository extends JpaRepository<SupplierWiseValue, Long> {
}

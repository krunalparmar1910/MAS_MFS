package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CustomerProductSupplierWiseTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProductSupplierWiseTotalRepository extends JpaRepository<CustomerProductSupplierWiseTotal, Long> {
}

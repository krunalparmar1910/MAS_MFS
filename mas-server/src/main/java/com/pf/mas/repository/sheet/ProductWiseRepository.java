package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.ProductWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWiseRepository extends JpaRepository<ProductWise, Long> {
}

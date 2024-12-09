package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.ProductWiseValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWiseValueRepository extends JpaRepository<ProductWiseValue, Long> {
}

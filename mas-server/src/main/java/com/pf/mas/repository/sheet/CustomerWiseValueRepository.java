package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CustomerWiseValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWiseValueRepository extends JpaRepository<CustomerWiseValue, Long> {
}

package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CustomerWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWiseRepository extends JpaRepository<CustomerWise, Long> {
}

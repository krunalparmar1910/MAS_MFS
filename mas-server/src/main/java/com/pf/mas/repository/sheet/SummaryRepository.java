package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    List<Summary> findByParticularsAndClientOrderId(String particulars, Long clientOrderId);
}

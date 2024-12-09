package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CustomersAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersAnalysisRepository extends JpaRepository<CustomersAnalysis, Long> {
    List<CustomersAnalysis> findByFieldGroupFieldGroupNameContainingAndClientOrderId(String fieldGroupName, Long clientOrderId);
}

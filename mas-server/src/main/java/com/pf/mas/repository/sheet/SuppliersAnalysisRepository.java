package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.SuppliersAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuppliersAnalysisRepository extends JpaRepository<SuppliersAnalysis, Long> {
    List<SuppliersAnalysis> findByFieldGroupFieldGroupNameContainingAndClientOrderId(String fieldGroupName, Long clientOrderId);
}

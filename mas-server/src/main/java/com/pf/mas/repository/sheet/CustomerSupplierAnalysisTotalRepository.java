package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CustomerSupplierAnalysisTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSupplierAnalysisTotalRepository extends JpaRepository<CustomerSupplierAnalysisTotal, Long> {
    List<CustomerSupplierAnalysisTotal> findByFieldGroupFieldGroupNameContainingAndFieldNameContainingAndClientOrderId(String fieldGroupName, String fieldName, Long clientOrderId);
}

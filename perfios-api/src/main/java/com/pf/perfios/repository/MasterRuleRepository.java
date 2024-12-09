package com.pf.perfios.repository;

import com.pf.perfios.model.entity.MasterRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRuleRepository extends JpaRepository<MasterRule, Long> {
    List<MasterRule> findByTransactionTypeList_Id(Long id);

    List<MasterRule> findByCategoryList_Id(Long id);

    List<MasterRule> findByPartiesMerchantList_Id(Long id);

    Optional<MasterRule> findByTransactionFlag(String transactionFlag);
}

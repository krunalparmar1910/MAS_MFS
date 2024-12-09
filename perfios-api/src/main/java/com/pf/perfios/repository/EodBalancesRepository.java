package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.EodBalances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EodBalancesRepository extends JpaRepository<EodBalances, Long> {
    List<EodBalances> findByAccountSummary(AccountSummary accountSummary);
}

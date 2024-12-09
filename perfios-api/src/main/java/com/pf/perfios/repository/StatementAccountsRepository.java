package com.pf.perfios.repository;

import com.pf.perfios.model.entity.StatementAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementAccountsRepository extends JpaRepository<StatementAccounts, Long> {
}

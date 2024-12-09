package com.pf.karza.repository;

import com.pf.karza.model.entity.bankingrelations.BankingRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingRelationsRepository extends JpaRepository<BankingRelations, Long> {
}

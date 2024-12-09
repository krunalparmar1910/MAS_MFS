package com.pf.perfios.repository;

import com.pf.perfios.model.entity.TransactionsCustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionsCustomFieldRepository extends JpaRepository<TransactionsCustomField, Long> {
    Optional<TransactionsCustomField> findByTransactions_Uuid(UUID id);
}

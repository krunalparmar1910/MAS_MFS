package com.pf.perfios.repository;

import com.pf.perfios.model.entity.IdentifierType;
import com.pf.perfios.model.entity.MasterIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterIdentifiersRepository extends JpaRepository<MasterIdentifiers, Long> {
    List<MasterIdentifiers> findByIdentifierType(IdentifierType identifierType);

    List<MasterIdentifiers> findByIdIn(List<Long> transactionTypeIdList);

    Optional<MasterIdentifiers> findByIdAndIdentifierType(Long id, IdentifierType transaction);

    boolean existsByIdentifierNameAndIdentifierType(String identifierName, IdentifierType identifierType);

    Optional<MasterIdentifiers> findByIdentifierNameAndIdentifierType(String loanDisbursementTransactionType, IdentifierType transaction);
}

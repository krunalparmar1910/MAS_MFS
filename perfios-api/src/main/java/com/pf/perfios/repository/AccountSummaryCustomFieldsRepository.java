package com.pf.perfios.repository;

import com.pf.perfios.model.entity.AccountSummaryCustomFields;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountSummaryCustomFieldsRepository extends JpaRepository<AccountSummaryCustomFields, Long> {
    Optional<AccountSummaryCustomFields> findByAccountSummary_Uuid(UUID accountSummaryId);

}

package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.cibil.NonCibilAccount;
import com.pf.mas.model.entity.consumer.Account;
import com.pf.mas.model.entity.consumer.EnvelopeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}

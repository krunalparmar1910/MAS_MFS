package com.pf.karza.repository;

import com.pf.karza.model.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankingDetailsRepository extends JpaRepository<BankDetails, UUID> {
}

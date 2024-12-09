package com.pf.karza.repository;

import com.pf.karza.model.entity.BadDebtDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadDebtDetailsRepository extends JpaRepository<BadDebtDetails, Long> {
}

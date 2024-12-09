package com.pf.karza.repository;

import com.pf.karza.model.entity.OutstandingDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutstandingDemandRepository extends JpaRepository<OutstandingDemand, Long> {
}

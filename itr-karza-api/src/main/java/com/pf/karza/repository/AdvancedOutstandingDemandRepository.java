package com.pf.karza.repository;

import com.pf.karza.model.entity.advanced.outstandingdemand.AdvancedOutstandingDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancedOutstandingDemandRepository extends JpaRepository<AdvancedOutstandingDemand, Long> {
}

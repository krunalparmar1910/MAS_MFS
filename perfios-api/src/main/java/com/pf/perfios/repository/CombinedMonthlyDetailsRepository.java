package com.pf.perfios.repository;

import com.pf.perfios.model.entity.CombinedMonthlyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinedMonthlyDetailsRepository extends JpaRepository<CombinedMonthlyDetails, Long> {

}

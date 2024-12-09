package com.pf.perfios.repository;

import com.pf.perfios.model.entity.FcuIndicators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcuIndicatorsRepository extends JpaRepository<FcuIndicators, Long> {

}

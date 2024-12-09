package com.pf.perfios.repository;

import com.pf.perfios.model.entity.FcuIndicatorsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcuIndicatorsDetailsRepository extends JpaRepository<FcuIndicatorsDetails, Long> {

}

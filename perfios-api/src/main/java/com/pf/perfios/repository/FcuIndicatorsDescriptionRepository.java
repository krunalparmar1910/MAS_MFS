package com.pf.perfios.repository;

import com.pf.perfios.model.entity.FcuIndicatorsDescription;
import com.pf.perfios.model.entity.IndicatorSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcuIndicatorsDescriptionRepository extends JpaRepository<FcuIndicatorsDescription, Long> {

    FcuIndicatorsDescription findByIndicatorSubType(IndicatorSubType fcuSubType);
}

package com.pf.perfios.repository;

import com.pf.perfios.model.entity.MonthwiseCustomFields;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MonthwiseCustomFieldsRepository extends JpaRepository<MonthwiseCustomFields, Long> {
    Optional<MonthwiseCustomFields> findByMonthwiseDetail_Uuid(UUID monthwiseDetailId);

}

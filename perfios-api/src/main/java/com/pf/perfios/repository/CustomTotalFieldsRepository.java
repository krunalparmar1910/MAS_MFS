package com.pf.perfios.repository;

import com.pf.perfios.model.dto.CustomEditableFieldDTO;
import com.pf.perfios.model.entity.CustomTotalFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomTotalFieldsRepository extends JpaRepository<CustomTotalFields,Long> {
    Optional<CustomTotalFields> findByMasFinId(String masFinancialId);
}

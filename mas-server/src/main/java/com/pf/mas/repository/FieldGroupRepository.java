package com.pf.mas.repository;

import com.pf.mas.model.entity.FieldGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldGroupRepository extends JpaRepository<FieldGroup, Long> {
}

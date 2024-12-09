package com.pf.karza.repository;

import com.pf.karza.model.entity.FormDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDetailsRepository extends JpaRepository<FormDetails, Long> {
}

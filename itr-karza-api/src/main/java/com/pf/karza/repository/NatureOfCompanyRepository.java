package com.pf.karza.repository;

import com.pf.karza.model.entity.NatureOfCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureOfCompanyRepository extends JpaRepository<NatureOfCompany, Long> {
}

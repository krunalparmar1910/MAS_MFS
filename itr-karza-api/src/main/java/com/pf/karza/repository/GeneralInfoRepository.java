package com.pf.karza.repository;

import com.pf.karza.model.entity.generalinfobusiness.GeneralInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralInfoRepository extends JpaRepository<GeneralInfo, Long> {
}

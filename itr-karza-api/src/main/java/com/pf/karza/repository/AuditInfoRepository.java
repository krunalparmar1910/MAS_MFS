package com.pf.karza.repository;

import com.pf.karza.model.entity.AuditInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditInfoRepository extends JpaRepository<AuditInfo, Long> {
}

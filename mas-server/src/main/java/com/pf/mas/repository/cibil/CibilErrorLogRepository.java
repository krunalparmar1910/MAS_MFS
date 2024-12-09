package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.cibil.CibilErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CibilErrorLogRepository extends JpaRepository<CibilErrorLog, Long> {
}

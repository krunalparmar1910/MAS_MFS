package com.pf.karza.repository;

import com.pf.karza.model.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertsRepository extends JpaRepository<Alerts, Long> {
}

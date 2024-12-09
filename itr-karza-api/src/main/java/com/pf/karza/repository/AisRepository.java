package com.pf.karza.repository;

import com.pf.karza.model.entity.ais.Ais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AisRepository extends JpaRepository<Ais, Long> {
}

package com.pf.karza.repository;

import com.pf.karza.model.entity.ubo.Ubo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UboRepository extends JpaRepository<Ubo, Long> {
}

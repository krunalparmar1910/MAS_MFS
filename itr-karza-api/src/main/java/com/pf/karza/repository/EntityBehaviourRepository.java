package com.pf.karza.repository;

import com.pf.karza.model.entity.EntityBehaviour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityBehaviourRepository extends JpaRepository<EntityBehaviour, Long> {
}

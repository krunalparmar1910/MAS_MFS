package com.pf.perfios.repository;

import com.pf.perfios.model.entity.StatementsConsidered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementsConsideredRepository extends JpaRepository<StatementsConsidered, Long> {

}

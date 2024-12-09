package com.pf.karza.repository;

import com.pf.karza.model.entity.advanced.twentysixas.AdvancedTwentySixAS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancedTwentySixASRepository extends JpaRepository<AdvancedTwentySixAS, Long> {
}

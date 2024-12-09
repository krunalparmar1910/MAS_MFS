package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.Bifurcation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BifurcationRepository extends JpaRepository<Bifurcation, Long> {
}

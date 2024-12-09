package com.pf.karza.repository;

import com.pf.karza.model.entity.twentySixAS.TwentySixASData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TwentySixASDataRepository extends JpaRepository<TwentySixASData, UUID> {
}

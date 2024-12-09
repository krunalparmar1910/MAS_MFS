package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.consumer.EnvelopeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvelopeResponseRepository extends JpaRepository<EnvelopeResponse, Long>, QuerydslPredicateExecutor<EnvelopeResponse> {
    Optional<EnvelopeResponse> findByRequestId(String requestId);
}

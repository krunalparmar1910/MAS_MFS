package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.commercial.EnvelopeResponseCommercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvelopeResponseCommercialRepository extends JpaRepository<EnvelopeResponseCommercial, Long> {
    Optional<EnvelopeResponseCommercial> findByRequestId(String requestId);
}

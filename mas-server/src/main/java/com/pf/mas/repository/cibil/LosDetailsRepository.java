package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.cibil.LosDetails;
import com.pf.mas.model.entity.cibil.NonCibilAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LosDetailsRepository  extends JpaRepository<LosDetails, UUID> {

    Optional<LosDetails> findByRequestIdAndArchived(String requestId, boolean archived);

}

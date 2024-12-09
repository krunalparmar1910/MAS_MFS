package com.pf.karza.repository;

import com.pf.karza.model.entity.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<UserRequest, Long> {
    Optional<UserRequest> findByMasRefId(String masRefId);
}

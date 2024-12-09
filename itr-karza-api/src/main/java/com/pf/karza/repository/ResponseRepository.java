package com.pf.karza.repository;

import com.pf.karza.model.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Optional<Response> findByMasRefId(String masRefId);
}

package com.pf.karza.repository;

import com.pf.karza.model.entity.ItrRawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItrRawDataRepository extends JpaRepository<ItrRawData, UUID> {
}
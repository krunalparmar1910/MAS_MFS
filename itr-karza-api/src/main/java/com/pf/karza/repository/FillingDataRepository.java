package com.pf.karza.repository;

import com.pf.karza.model.entity.fillingData.FillingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillingDataRepository extends JpaRepository<FillingData, Long> {
}

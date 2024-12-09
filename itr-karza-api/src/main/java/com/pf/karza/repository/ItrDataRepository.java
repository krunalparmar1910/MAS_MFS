package com.pf.karza.repository;

import com.pf.karza.model.entity.advanced.itrdata.ItrData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItrDataRepository extends JpaRepository<ItrData, Long> {
}

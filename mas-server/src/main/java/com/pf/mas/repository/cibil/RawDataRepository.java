package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.cibil.RawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawDataRepository  extends JpaRepository<RawData, Long> {

}

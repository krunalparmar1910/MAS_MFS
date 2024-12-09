package com.pf.mas.repository.cibil;

import com.pf.mas.model.entity.consumer.CibilDpdData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CibilDpdDataRepository extends JpaRepository<CibilDpdData,Long> {

}

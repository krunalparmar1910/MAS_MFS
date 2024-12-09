package com.pf.perfios.repository;

import com.pf.perfios.model.entity.TopFiveFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopFiveFundsRepository extends JpaRepository<TopFiveFunds, Long> {

}

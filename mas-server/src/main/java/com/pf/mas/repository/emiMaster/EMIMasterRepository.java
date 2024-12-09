package com.pf.mas.repository.emiMaster;

import com.pf.mas.model.entity.cibil.EMIMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EMIMasterRepository extends JpaRepository<EMIMaster, Long> {

    EMIMaster findByTypeOfLoan(String accountType);
}

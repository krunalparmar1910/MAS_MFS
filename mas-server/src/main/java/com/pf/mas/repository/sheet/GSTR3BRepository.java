package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.GSTR3B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GSTR3BRepository extends JpaRepository<GSTR3B, Long> {
    List<GSTR3B> findByParticularsContainingAndClientOrderId(String particulars, Long clientOrderId);
}

package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.IntraGroupRevenueValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntraGroupRevenueValueRepository extends JpaRepository<IntraGroupRevenueValue, Long> {
    List<IntraGroupRevenueValue> findByIntraGroupGstinContainingAndFieldDateMonthYearFieldDateValueAndIntraGroupClientOrderId(
            String intraGroupGstin, String fieldDateValue, Long clientOrderId);
}

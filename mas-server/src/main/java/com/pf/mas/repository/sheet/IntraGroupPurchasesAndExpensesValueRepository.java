package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.IntraGroupPurchasesAndExpensesValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntraGroupPurchasesAndExpensesValueRepository extends JpaRepository<IntraGroupPurchasesAndExpensesValue, Long> {
    List<IntraGroupPurchasesAndExpensesValue> findByIntraGroupGstinAndFieldDateMonthYearFieldDateValueAndIntraGroupClientOrderId(
            String intraGroupGstin, String fieldDateValue, Long clientOrderId);
}

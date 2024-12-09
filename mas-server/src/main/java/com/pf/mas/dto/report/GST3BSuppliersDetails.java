package com.pf.mas.dto.report;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
public class GST3BSuppliersDetails {
    private final String supplierName;

    private final BigDecimal adjustedPurchaseAndExpenses;

    private final String adjustedPurchaseAndExpensesPercent;

    // manual entry fields
    private final BigDecimal numericEntryAsPerBanking;

    private final Boolean numericEntryAddToInterfirm;
}

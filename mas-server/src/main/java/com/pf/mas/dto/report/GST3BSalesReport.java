package com.pf.mas.dto.report;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
public class GST3BSalesReport {
    private final String entityId;

    private final String clientOrderId;

    private final String tradeName;

    private final GST3BSales gst3BSales;

    private final BigDecimal interStateCustomersAnalysis;

    private final BigDecimal interStateSuppliersAnalysis;

    private final GST3BSuppliers gst3BSuppliers;

    private final GST3BCustomers gst3BCustomers;

    @Nullable
    private final BigDecimal grossAdjustedRevenue;

    // manual entry fields
    private final BigDecimal circularOrOthersCustomersAnalysis;

    private final BigDecimal circularOrOthersSuppliersAnalysis;

    private final Integer totalNumberOfMonthsCustomersAnalysis;

    private final Integer totalNumberOfMonthsSuppliersAnalysis;
}

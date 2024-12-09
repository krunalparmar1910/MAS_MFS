package com.pf.mas.dto.report;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
public class GST3BSuppliers {
    private final List<GST3BSuppliersDetails> gst3BSuppliersDetails;

    private final BigDecimal adjustedPurchaseAndExpensesTotal;
}

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
public class GST3BCustomersDetails {
    private final String customerName;

    private final BigDecimal adjustedRevenue;

    private final String adjustedRevenuePercent;

    // manual entry fields
    private final BigDecimal numericEntryAsPerBanking;

    private final Boolean numericEntryAddToInterfirm;
}

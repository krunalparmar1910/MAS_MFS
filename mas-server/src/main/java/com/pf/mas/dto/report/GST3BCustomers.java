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
public class GST3BCustomers {
    private final List<GST3BCustomersDetails> gst3BCustomersDetails;

    private final BigDecimal adjustedRevenueTotal;
}

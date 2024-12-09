package com.pf.mas.dto.report;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@Jacksonized
public class GST3BSalesDetail {
    private final LocalDate month;

    private final BigDecimal sales;

    private final BigDecimal purchase;

    @Nullable
    private final Boolean delayInFiling;

    @Nullable
    private final Integer delayedDays;
}

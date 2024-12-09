package com.pf.mas.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@Jacksonized
public class GST3BSales {
    private final List<GST3BSalesDetail> gst3BSalesDetails;

    private final BigDecimal totalSales;

    private final BigDecimal totalPurchase;

    private final Integer totalDelayedDays;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal averageDelayInDays;
}

package com.pf.mas.dto.report;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString(onlyExplicitlyIncluded = true)
public class UpdateGST3BReportManualEntryRequest {
    @ToString.Include
    private final String entityId;

    @ToString.Include
    private final String clientOrderId;

    private final GSTReport gstReport;
}

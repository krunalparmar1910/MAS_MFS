package com.pf.mas.dto.report;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@EqualsAndHashCode
@Jacksonized
public class GSTReportProfileDetail {
    private final String legalName;

    private final String tradeName;

    private final String panNumber;

    private final String gstNumber;

    private final String placeOfBusiness;

    private final String state;

    private final String status;
}

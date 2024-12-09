package com.pf.mas.dto.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@ToString
public class GetGST3BReportRequestResponseInfo {
    private final String entityId;
    private final List<String> clientOrderIds;
    private final String companyName;
}

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
public class GetGST3BReportRequestHistoryResponseDTO {
    private final List<GetGST3BReportRequestResponseInfo> getGST3BReportRequestResponseInfoList;
}

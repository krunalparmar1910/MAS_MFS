package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusResponseData {
    @JsonProperty("status-description")
    private final String statusDescription;

    @JsonProperty("status-id")
    private final Integer statusId;

    @JsonProperty("remarks")
    private final String remarks;
}

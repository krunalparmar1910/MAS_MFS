package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasGetReportRequestBody extends GetReportRequestBody {
    @ToString.Include
    @JsonProperty("entity-id")
    private final String entityId;
}

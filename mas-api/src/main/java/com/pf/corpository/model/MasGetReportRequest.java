package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.corpository.model.base.CorpositoryRequest;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@ToString
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasGetReportRequest extends CorpositoryRequest {
    @JsonProperty("para")
    private final MasGetReportRequestBody requestBody;
}

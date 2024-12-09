package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.corpository.model.base.CorpositoryRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusRequest extends CorpositoryRequest {
    @JsonProperty("para")
    private final OrderStatusRequestBody orderStatusRequestBody;
}

package com.pf.corpository.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class CorpositoryResponse {
    @JsonProperty("msg")
    private final String message;
}

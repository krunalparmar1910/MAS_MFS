package com.pf.karza.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItrResponse {
    private final String requestId;
    private final Integer statusCode;
    private final String masRefId;
    private final Map<String, Object> result;
    private final String error;
}

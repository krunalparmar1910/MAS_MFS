package com.pf.mas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ErrorResponse {
    private final String message;

    private final int errorCode;

    private final String progressStatus;
}

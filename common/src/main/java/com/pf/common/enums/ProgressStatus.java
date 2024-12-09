package com.pf.common.enums;

import lombok.Getter;

public enum ProgressStatus {
    INITIATED("Initiated"),
    COMPLETED("Completed"),
    API_CALL_FAILED("API call failed"),
    PARSING_ERROR("Error while parsing data"),
    NOT_COMPLETED("Could not complete request");

    @Getter
    private final String description;

    ProgressStatus(String description) {
        this.description = description;
    }
}

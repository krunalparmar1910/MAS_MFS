package com.pf.perfios.model.dto;

import lombok.Getter;

@Getter
public enum PerfiosCallBackStatus {

    COMPLETED("COMPLETED", "Transaction completed successfully"),
    ERROR("ERROR","There was an error processing the transaction"),
    ERROR_USER("ERROR_USER", "Transaction did not succeed due to an user error"),
    ERROR_SITE("ERROR_SITE", "Transaction did not succeed due to a (bank) website error"),
    ERROR_SESSION_EXPIRED("ERROR_SESSION_EXPIRED", "User walked away without providing any useful inputs"),
    CANCELLED("CANCELLED", "User cancelled the transaction"),
    REJECTED("REJECTED", "Perfios rejected the transaction");


    private String value;
    private String description;

    PerfiosCallBackStatus(String value, String description){
        this.value = value;
        this.description = description;
    }


}

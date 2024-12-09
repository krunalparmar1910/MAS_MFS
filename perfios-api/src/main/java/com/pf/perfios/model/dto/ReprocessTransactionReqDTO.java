package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("payload")
public class ReprocessTransactionReqDTO {
    private String perfiosTransactionId;
    private String yearMonthFrom;
    private String yearMonthTo;
    private String acceptancePolicy;
}

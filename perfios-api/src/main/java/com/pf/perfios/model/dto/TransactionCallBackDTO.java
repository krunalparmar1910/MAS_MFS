package com.pf.perfios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCallBackDTO {

    private String errorMessage;
    private String errorCode;
    private String clientTransactionId;
    private String perfiosTransactionId;
    private String masFinancialId;
    private String status;
}

package com.pf.perfios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomEditableFieldDTO {

    private String accountSummaryUuid;
    private long customCreditLoanReceipts;
    private long customDebitLoanReceipts;
    private long totalCreditInterBank;
    private long totalDebitInterBank;
    private long totalCreditInterFirm;
    private long totalDebitInterFirm;
    private long totalCreditNonBusinessTransaction;
    private long totalDebitNonBusinessTransaction;
    private String masFinId;
}

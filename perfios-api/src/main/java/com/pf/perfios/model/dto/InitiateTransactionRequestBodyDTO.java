package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.pf.perfios.utils.MASConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonRootName("payload")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitiateTransactionRequestBodyDTO {
    @JsonProperty("txnId")
    private String transactionNumberId;
    private String processingType;
    private BigDecimal loanAmount;
    private Long loanDuration;
    private String loanType;
    private String transactionCompleteCallbackUrl;
    private Boolean uploadingScannedStatements;
    private String acceptancePolicy;
    private String yearMonthFrom;
    private String yearMonthTo;

    public static InitiateTransactionRequestBodyDTO from(CreateReportDTO request) {
        return InitiateTransactionRequestBodyDTO
                .builder()
                .transactionNumberId(request.getCustomerTransactionId())
                .processingType(MASConst.PROCESSING_TYPE)
                .loanAmount(MASConst.LOAN_AMOUNT)
                .loanDuration(MASConst.LOAN_DURATION)
                .loanType(MASConst.LOAN_TYPE)
                .uploadingScannedStatements(request.getUploadingScannedStatements())
                .acceptancePolicy(MASConst.ACCEPTANCE_POLICY)
                .yearMonthFrom(request.getYearMonthFrom())
                .yearMonthTo(request.getYearMonthTo())
                .build();
    }
}

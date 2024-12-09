package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFieldReqDTO {

    private String accountSummaryUuid;
    private BigDecimal customCreditLoanReceipts;
    private BigDecimal customDebitLoanReceipts;

    private List<CustomFieldMonthlyReportReqDTO> monthlyReportFieldList;

    private CustomEditableFieldDTO customEditableFieldDTO;
}

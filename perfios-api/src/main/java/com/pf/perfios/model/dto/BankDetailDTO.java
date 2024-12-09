package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BankDetailDTO {
    private String accountSummaryUuid;

    private String customerTransactionId;

    private String perfiosTransactionId;

    private String bankName;

    private CustomerDTO customerDetails;

    private String accountNumber;
    private String accountType;

    private BigDecimal customCreditLoanReceipts;
    private BigDecimal customDebitLoanReceipts;

    private List<MonthlyReportDTO> monthlyReportList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate yearMonthFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate yearMonthTo;

    private BigDecimal averageBankingBalance;
    private BigDecimal medianBankingBalance;
}

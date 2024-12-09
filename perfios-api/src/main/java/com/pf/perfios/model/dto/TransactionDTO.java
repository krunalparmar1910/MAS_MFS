package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.Transactions;
import com.pf.perfios.utils.PerfiosUtils;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDTO {
    private String transactionUuid;

    @JsonFormat(pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate date;

    private Long chequeNo;

    private String description;

    private BigDecimal amount;
    private BigDecimal debit;
    private BigDecimal credit;

    private String category;

    private BigDecimal balance;

    //needs to be calculated
    private String transactionFlag;

    private String identifiedCategory;
    private String transactionType;

    private String parties;

    private String institutionName;

    private String accountNumber;

    private String comment;

    public static TransactionDTO fromTransaction(Transactions transaction) {
        AccountSummary account = transaction.getAccountSummary();
        return TransactionDTO.builder()
                .transactionUuid(transaction.getUuid().toString())
                .date(transaction.getDate())
                .chequeNo(transaction.getChequeNo())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .debit(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? transaction.getAmount().abs() : null)
                .credit(transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0 ? transaction.getAmount() : null)
                .category(transaction.getCategory())
                .balance(transaction.getBalance())
                .institutionName(account.getInstitutionName())
                .accountNumber(account.getAccountNumber())
                .transactionFlag("")
                .parties("")
                .identifiedCategory("")
                .transactionType("")
                .comment(transaction.getComment())
                .build();
    }

    public boolean isCredit() {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}

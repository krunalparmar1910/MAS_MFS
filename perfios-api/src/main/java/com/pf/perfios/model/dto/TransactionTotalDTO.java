package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.perfios.utils.PerfiosUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TransactionTotalDTO {
    private Double totalCredit;
    private Double totalDebit;

    @JsonFormat(pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate startTransactionDate;

    @JsonFormat(pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate endTransactionDate;
}

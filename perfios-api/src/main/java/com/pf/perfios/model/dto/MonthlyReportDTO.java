package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.perfios.model.entity.MonthwiseCustomFields;
import com.pf.perfios.model.entity.MonthwiseDetails;
import com.pf.perfios.utils.PerfiosUtils;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MonthlyReportDTO {

    private String monthwiseDetailUuid;
    @JsonFormat(pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate monthYear;
    private BigDecimal credit;
    private BigDecimal debit;
    @Builder.Default
    private BigDecimal creditInterBank = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal creditInterFirm = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal creditNonBusinessTransaction = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal debitInterBank = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal debitInterFirm = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal debitNonBusinessTransaction = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal totalLoanDisbursements = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal totalEmiTracing = BigDecimal.ZERO;
    private Long inwardReturn;
    private Long outwardReturn;
    @Builder.Default
    private BigDecimal penaltyCharges = BigDecimal.ZERO;

    private BigDecimal customCcUtilizationPercentage;

    public static MonthlyReportDTO toDTO(MonthwiseDetails detail, Long inwardReturn) {
        MonthwiseCustomFields monthwiseCustomFields = detail.getMonthWiseCustomField();
        return MonthlyReportDTO.builder()
                .monthwiseDetailUuid(detail.getUuid().toString())
                .credit(detail.getTotalAmountOfCreditTransactions())
                .debit(detail.getTotalAmountOfDebitTransactions())
                .monthYear(detail.getMonth())
                .inwardReturn(inwardReturn)
                .outwardReturn(detail.getTotalNoOfOutwardChequeBounces())
                .customCcUtilizationPercentage(monthwiseCustomFields != null ? monthwiseCustomFields.getCustomCcUtilizationPercentage() : null)
                .build();
    }
}

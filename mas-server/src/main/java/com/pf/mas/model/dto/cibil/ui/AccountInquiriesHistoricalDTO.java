package com.pf.mas.model.dto.cibil.ui;

import lombok.Data;

@Data
public class AccountInquiriesHistoricalDTO {
    private Long inquiriesLast30DaysFromCurrentDate;
    private Long inquiriesLast60DaysFromCurrentDate;
    private Long inquiriesLast120DaysFromCurrentDate;
    private Long inquiriesLast365DaysFromCurrentDate;
    private Long inquiriesLast730DaysFromCurrentDate;
    private Long accountsOpenedLast30DaysFromCurrentDate;
    private Long accountsOpenedLast60DaysFromCurrentDate;
    private Long accountsOpenedLast120DaysFromCurrentDate;
    private Long accountsOpenedLast365DaysFromCurrentDate;
    private Long accountsOpenedLast730DaysFromCurrentDate;
    private Long inquiriesLast30DaysFromCibilReportDate;
    private Long inquiriesLast60DaysFromCibilReportDate;
    private Long inquiriesLast120DaysFromCibilReportDate;
    private Long inquiriesLast365DaysFromCibilReportDate;
    private Long inquiriesLast730DaysFromCibilReportDate;
    private Long accountsOpenedLast30DaysFromCibilReportDate;
    private Long accountsOpenedLast60DaysFromCibilReportDate;
    private Long accountsOpenedLast120DaysFromCibilReportDate;
    private Long accountsOpenedLast365DaysFromCibilReportDate;
    private Long accountsOpenedLast730DaysFromCibilReportDate;

    public AccountInquiriesHistoricalDTO() {
        this.inquiriesLast30DaysFromCurrentDate = 0L;
        this.inquiriesLast60DaysFromCurrentDate = 0L;
        this.inquiriesLast120DaysFromCurrentDate = 0L;
        this.inquiriesLast365DaysFromCurrentDate = 0L;
        this.inquiriesLast730DaysFromCurrentDate = 0L;
        this.accountsOpenedLast30DaysFromCurrentDate = 0L;
        this.accountsOpenedLast60DaysFromCurrentDate = 0L;
        this.accountsOpenedLast120DaysFromCurrentDate = 0L;
        this.accountsOpenedLast365DaysFromCurrentDate = 0L;
        this.accountsOpenedLast730DaysFromCurrentDate = 0L;
        this.inquiriesLast30DaysFromCibilReportDate = 0L;
        this.inquiriesLast60DaysFromCibilReportDate = 0L;
        this.inquiriesLast120DaysFromCibilReportDate = 0L;
        this.inquiriesLast365DaysFromCibilReportDate = 0L;
        this.inquiriesLast730DaysFromCibilReportDate = 0L;
        this.accountsOpenedLast30DaysFromCibilReportDate = 0L;
        this.accountsOpenedLast60DaysFromCibilReportDate = 0L;
        this.accountsOpenedLast120DaysFromCibilReportDate = 0L;
        this.accountsOpenedLast365DaysFromCibilReportDate = 0L;
        this.accountsOpenedLast730DaysFromCibilReportDate = 0L;
    }
}

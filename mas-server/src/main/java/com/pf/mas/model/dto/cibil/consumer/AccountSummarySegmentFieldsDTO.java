package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Account_Summary_Segment_Fields")
public class AccountSummarySegmentFieldsDTO {
    @JacksonXmlProperty(localName = "ReportingMemberShortName")
    private String reportingMemberShortName;

    @JacksonXmlProperty(localName = "NumberOfAccounts")
    private Long numberOfAccounts;

    @JacksonXmlProperty(localName = "DateOfLastPayment")
    private String dateOfLastPayment;

    @JacksonXmlProperty(localName = "HighCreditOrSanctionedAmountFieldLength")
    private String highCreditOrSanctionedAmountFieldLength;

    @JacksonXmlProperty(localName = "HighCreditOrSanctionedAmount")
    private Long highCreditOrSanctionedAmount;

    @JacksonXmlProperty(localName = "AmountOverdueFieldLength")
    private String amountOverdueFieldLength;

    @JacksonXmlProperty(localName = "PaymentHistory1")
    private String paymentHistory1;

    @JacksonXmlProperty(localName = "PaymentHistory2")
    private String paymentHistory2;

    @JacksonXmlProperty(localName = "PaymentHistoryEndDate")
    private String paymentHistoryEndDate;

    @JacksonXmlProperty(localName = "ReportingMemberShortNameFieldLength")
    private String reportingMemberShortNameFieldLength;

    @JacksonXmlProperty(localName = "NumberOfAccountsFieldLength")
    private String numberOfAccountsFieldLength;

    @JacksonXmlProperty(localName = "LiveClosedIndicator")
    private String liveClosedIndicator;

    @JacksonXmlProperty(localName = "DateOpenedOrDisbursed")
    private String dateOpenedOrDisbursed;

    @JacksonXmlProperty(localName = "PaymentHistory2FieldLength")
    private String paymentHistory2FieldLength;

    @JacksonXmlProperty(localName = "PaymentHistoryStartDate")
    private String paymentHistoryStartDate;

    @JacksonXmlProperty(localName = "AccountGroup")
    private String accountGroup;

    @JacksonXmlProperty(localName = "DateReported")
    private String dateReported;

    @JacksonXmlProperty(localName = "CurrentBalanceFieldLength")
    private String currentBalanceFieldLength;

    @JacksonXmlProperty(localName = "AmountOverdue")
    private Long amountOverdue;

    @JacksonXmlProperty(localName = "PaymentHistory1FieldLength")
    private String paymentHistory1FieldLength;

    @JacksonXmlProperty(localName = "DateClosed")
    private String dateClosed;

    @JacksonXmlProperty(localName = "CurrentBalance")
    private Long currentBalance;

}
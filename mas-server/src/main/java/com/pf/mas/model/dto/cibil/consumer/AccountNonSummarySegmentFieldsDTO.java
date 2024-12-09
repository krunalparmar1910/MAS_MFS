package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Account_NonSummary_Segment_Fields")
public class AccountNonSummarySegmentFieldsDTO {

    @JacksonXmlProperty(localName = "ReportingMemberShortName")
    private String reportingMemberShortName;

    @JacksonXmlProperty(localName = "ReportingMemberShortNameFieldLength")
    private Double reportingMemberShortNameFieldLength;

    @JacksonXmlProperty(localName = "AccountNumberFieldLength")
    private String accountNumberFieldLength;

    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;

    @JacksonXmlProperty(localName = "OwenershipIndicator")
    private String ownershipIndicator;

    @JacksonXmlProperty(localName = "DateOpenedOrDisbursed")
    private String dateOpenedOrDisbursed;

    @JacksonXmlProperty(localName = "DateReportedAndCertified")
    private String dateReportedAndCertified;

    @JacksonXmlProperty(localName = "HighCreditOrSanctionedAmount")
    private Double highCreditOrSanctionedAmount;

    @JacksonXmlProperty(localName = "CurrentBalance")
    private Double currentBalance;

    @JacksonXmlProperty(localName = "PaymentHistory1FieldLength")
    private String paymentHistory1FieldLength;

    @JacksonXmlProperty(localName = "PaymentHistory2FieldLength")
    private String paymentHistory2FieldLength;

    @JacksonXmlProperty(localName = "PaymentHistoryEndDate")
    private String paymentHistoryEndDate;

    @JacksonXmlProperty(localName = "ValueOfCollateralFieldLength")
    private String valueOfCollateralFieldLength;

    @JacksonXmlProperty(localName = "CreditLimitFieldLength")
    private String creditLimitFieldLength;

    @JacksonXmlProperty(localName = "CreditLimit")
    private Double creditLimit;

    @JacksonXmlProperty(localName = "CashLimitFieldLength")
    private String cashLimitFieldLength;

    @JacksonXmlProperty(localName = "CashLimit")
    private Double cashLimit;

    @JacksonXmlProperty(localName = "RateOfInterestFieldLength")
    private String rateOfInterestFieldLength;

    @JacksonXmlProperty(localName = "EmiAmountFieldLength")
    private String emiAmountFieldLength;

    @JacksonXmlProperty(localName = "WrittenOffAmountTotal")
    private Double writtenOffAmountTotal;

    @JacksonXmlProperty(localName = "PaymentFrequency")
    private String paymentFrequency;

    @JacksonXmlProperty(localName = "ActualPaymentAmountFieldLength")
    private String actualPaymentAmountFieldLength;

    @JacksonXmlProperty(localName = "DateOfEntryForCIBILRemarksCode")
    private String dateOfEntryForCibilRemarksCode;

    @JacksonXmlProperty(localName = "DateOfEntryForErrorDisputeRemarksCode")
    private String dateOfEntryForErrorDisputeRemarksCode;

    @JacksonXmlProperty(localName = "ErrorDisputeRemarksCode1")
    private String errorDisputeRemarksCode1;

    @JacksonXmlProperty(localName = "ErrorDisputeRemarksCode2")
    private String errorDisputeRemarksCode2;

    @JacksonXmlProperty(localName = "FID")
    private String fid;

    @JacksonXmlProperty(localName = "SNo")
    private String sNo;

    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;

    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;

    @JacksonXmlProperty(localName = "HighCreditOrSanctionedAmountFieldLength")
    private String highCreditOrSanctionedAmountFieldLength;

    @JacksonXmlProperty(localName = "PaymentHistory1")
    private String paymentHistory1;

    @JacksonXmlProperty(localName = "SuitFiledOrWilfulDefault")
    private String suitFiledOrWilfulDefault;

    @JacksonXmlProperty(localName = "TypeOfCollateralFieldLength")
    private String typeOfCollateralFieldLength;

    @JacksonXmlProperty(localName = "TypeOfCollateral")
    private String typeOfCollateral;

    @JacksonXmlProperty(localName = "RepaymentTenure")
    private Double repaymentTenure;

    @JacksonXmlProperty(localName = "EmiAmount")
    private Double emiAmount;

    @JacksonXmlProperty(localName = "WrittenOffAmountTotalFieldLength")
    private String writtenOffAmountTotalFieldLength;

    @JacksonXmlProperty(localName = "WrittenOffAmountPrincipalFieldLength")
    private String writtenOffAmountPrincipalFieldLength;

    @JacksonXmlProperty(localName = "ActualPaymentAmount")
    private Double actualPaymentAmount;

    @JacksonXmlProperty(localName = "DateOfEntryForErrorCode")
    private String dateOfEntryForErrorCode;

    @JacksonXmlProperty(localName = "AccountNumber")
    private String accountNumber;

    @JacksonXmlProperty(localName = "DateOfLastPayment")
    private String dateOfLastPayment;

    @JacksonXmlProperty(localName = "DateClosed")
    private String dateClosed;

    @JacksonXmlProperty(localName = "AmountOverdue")
    private Double amountOverdue;

    @JacksonXmlProperty(localName = "PaymentHistory2")
    private String paymentHistory2;

    @JacksonXmlProperty(localName = "PaymentHistoryStartDate")
    private String paymentHistoryStartDate;

    @JacksonXmlProperty(localName = "CreditFacility")
    private String creditFacility;

    @JacksonXmlProperty(localName = "ValueOfCollateral")
    private String valueOfCollateral;

    @JacksonXmlProperty(localName = "RepaymentTenureFieldLength")
    private String repaymentTenureFieldLength;

    @JacksonXmlProperty(localName = "WrittenOffAmountPrincipal")
    private String writtenOffAmountPrincipal;

    @JacksonXmlProperty(localName = "SettlementAmount")
    private String settlementAmount;

    @JacksonXmlProperty(localName = "ErrorCode")
    private String errorCode;

    @JacksonXmlProperty(localName = "CIBILRemarksCode")
    private String cibilRemarksCode;

    @JacksonXmlProperty(localName = "CurrentBalanceFieldLength")
    private String currentBalanceFieldLength;

    @JacksonXmlProperty(localName = "AmountOverdueFieldLength")
    private String amountOverdueFieldLength;

    @JacksonXmlProperty(localName = "SettlementAmountFieldLength")
    private String settlementAmountFieldLength;

    @JacksonXmlProperty(localName = "RateOfInterest")
    private Double rateOfInterest;

}
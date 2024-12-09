package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.consumer.PaymentHistoryRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuccountSummerySegmentDTO {

    private String reportingMemberShortName;
    private String accountType;
    private String ownershipIndicator;

    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOpenedOrDisbursed;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateReportedAndCertified;
    private Double highCreditOrSanctionedAmount;
    private Double currentBalance;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date paymentHistoryEndDate;
    private Double creditLimit;
    private Double cashLimit;
    private Double writtenOffAmountTotal;
    private String paymentFrequency;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfEntryForCibilRemarksCode;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfEntryForErrorDisputeRemarksCode;
    @Column(name = "error_dispute_remarks_code_1")
    private String errorDisputeRemarksCode1;
    @Column(name = "error_dispute_remarks_code_2")
    private String errorDisputeRemarksCode2;
    private String fid;
    private String sNo;
    private String suppressFlag;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfSuppression;
    @Column(name = "payment_history_1")
    private String paymentHistory1;
    private boolean liveAccount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_non_summary_segment_id")
    private List<PaymentHistoryRecord> paymentHistoryRecordList;
    private String suitFiledOrWilfulDefault;
    private String typeOfCollateral;
    private Long repaymentTenure;
    private Double emiAmount;
    private Double actualPaymentAmount;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfEntryForErrorCode;
    private String accountNumber;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfLastPayment;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateClosed;
    private Double amountOverdue;
    @Column(name = "payment_history_2")
    private String paymentHistory2;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date paymentHistoryStartDate;
    private String creditFacility;
    private String valueOfCollateral;
    private String writtenOffAmountPrincipal;
    private String settlementAmount;
    private String errorCode;
    private String cibilRemarksCode;
    private Double rateOfInterest;

}

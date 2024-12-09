package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_summary_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AccountSummarySegment extends BaseID {

    // #TODO Remove this segment if needed in future.
    private String reportingMemberShortName;
    private Long numberOfAccounts;
    private String dateOfLastPayment;
    private Long highCreditOrSanctionedAmount;
    @Column(name = "payment_history_1")
    private String paymentHistory1;
    @Column(name = "payment_history_2")
    private String paymentHistory2;
    private String paymentHistoryEndDate;
    private String liveClosedIndicator;
    private String dateOpenedOrDisbursed;
    private String paymentHistoryStartDate;
    private String accountGroup;
    private String dateReported;
    private Long amountOverdue;
    private String dateClosed;
    private Long currentBalance;
}

package com.pf.mas.model.dto.cibil.ui;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ConsumerAccountSummaryDTO {

    private Long totalLoansOpened;
    private Long liveLoans;
    private Long closedLoans;
    private BigDecimal sanctionedAmountLiveLoans;
    private BigDecimal posLiveLoans;
    private BigDecimal overdueAmountLiveLoans;
    private Long stdAllAccounts;
    private Long smaAllAccounts;
    private Long dbtAllAccounts;
    private Long subAllAccounts;
    private Long lssAllAccounts;
    private Long notReportedAllAccounts;
    private Long stdLiveAccounts;
    private Long smaLiveAccounts;
    private Long dbtLiveAccounts;
    private Long subLiveAccounts;
    private Long lssLiveAccounts;
    private Long notReportedLiveAccounts;
    private Long stdClosedAccounts;
    private Long smaClosedAccounts;
    private Long dbtClosedAccounts;
    private Long subClosedAccounts;
    private Long lssClosedAccounts;
    private Long notReportedClosedAccounts;

    private Map<String ,Map<String ,Object>> individual;
    private Map<String ,Map<String ,Object>> joint;
    private Map<String ,Map<String ,Object>> guarantor;

    private long dpd0AllAccountsInd;
    private long dpd1to30AllAccountsInd;
    private long dpd31to90AllAccountsInd;
    private long dpdGreaterThan90AllAccountsInd;

    private long dpd0LiveAccountsInd;
    private long dpd1to30LiveAccountsInd;
    private long dpd31to90LiveAccountsInd;
    private long dpdGreaterThan90LiveAccountsInd;

    private long dpd0ClosedAccountsInd;
    private long dpd1to30ClosedAccountsInd;
    private long dpd31to90ClosedAccountsInd;
    private long dpdGreaterThan90ClosedAccountsInd;

    private long dpd0AllAccountsJoint;
    private long dpd1to30AllAccountsJoint;
    private long dpd31to90AllAccountsJoint;
    private long dpdGreaterThan90AllAccountsJoint;

    private long dpd0LiveAccountsJoint;
    private long dpd1to30LiveAccountsJoint;
    private long dpd31to90LiveAccountsJoint;
    private long dpdGreaterThan90LiveAccountsJoint;

    private long dpd0ClosedAccountsJoint;
    private long dpd1to30ClosedAccountsJoint;
    private long dpd31to90ClosedAccountsJoint;
    private long dpdGreaterThan90ClosedAccountsJoint;

    private long dpd0AllAccountsGuarantor;
    private long dpd1to30AllAccountsGuarantor;
    private long dpd31to90AllAccountsGuarantor;
    private long dpdGreaterThan90AllAccountsGuarantor;

    private long dpd0LiveAccountsGuarantor;
    private long dpd1to30LiveAccountsGuarantor;
    private long dpd31to90LiveAccountsGuarantor;
    private long dpdGreaterThan90LiveAccountsGuarantor;

    private long dpd0ClosedAccountsGuarantor;
    private long dpd1to30ClosedAccountsGuarantor;
    private long dpd31to90ClosedAccountsGuarantor;
    private long dpdGreaterThan90ClosedAccountsGuarantor;

    private long settled;
    private long resturctured;
    private long subStd;
    private long dubDebt;


    // Constructor to initialize all fields
    public ConsumerAccountSummaryDTO() {
        this.totalLoansOpened = 0L;
        this.liveLoans = 0L;
        this.closedLoans = 0L;
        this.sanctionedAmountLiveLoans = BigDecimal.ZERO;
        this.posLiveLoans = BigDecimal.ZERO;
        this.overdueAmountLiveLoans = BigDecimal.ZERO;
        this.stdAllAccounts = 0L;
        this.smaAllAccounts = 0L;
        this.dbtAllAccounts = 0L;
        this.subAllAccounts = 0L;
        this.lssAllAccounts = 0L;
        this.notReportedAllAccounts = 0L;
        this.stdLiveAccounts = 0L;
        this.smaLiveAccounts = 0L;
        this.dbtLiveAccounts = 0L;
        this.subLiveAccounts = 0L;
        this.lssLiveAccounts = 0L;
        this.notReportedLiveAccounts = 0L;
        this.stdClosedAccounts = 0L;
        this.smaClosedAccounts = 0L;
        this.subClosedAccounts = 0L;
        this.dbtClosedAccounts = 0L;
        this.lssClosedAccounts = 0L;
        this.notReportedClosedAccounts = 0L;

        // Initialize DPD fields
        this.dpd0AllAccountsInd = 0;
        this.dpd1to30AllAccountsInd = 0;
        this.dpd31to90AllAccountsInd = 0;
        this.dpdGreaterThan90AllAccountsInd = 0;

        this.dpd0LiveAccountsInd = 0;
        this.dpd1to30LiveAccountsInd = 0;
        this.dpd31to90LiveAccountsInd = 0;
        this.dpdGreaterThan90LiveAccountsInd = 0;

        this.dpd0ClosedAccountsInd = 0;
        this.dpd1to30ClosedAccountsInd = 0;
        this.dpd31to90ClosedAccountsInd = 0;
        this.dpdGreaterThan90ClosedAccountsInd = 0;

        this.dpd0AllAccountsJoint = 0;
        this.dpd1to30AllAccountsJoint = 0;
        this.dpd31to90AllAccountsJoint = 0;
        this.dpdGreaterThan90AllAccountsJoint = 0;

        this.dpd0LiveAccountsJoint = 0;
        this.dpd1to30LiveAccountsJoint = 0;
        this.dpd31to90LiveAccountsJoint = 0;
        this.dpdGreaterThan90LiveAccountsJoint = 0;

        this.dpd0ClosedAccountsJoint = 0;
        this.dpd1to30ClosedAccountsJoint = 0;
        this.dpd31to90ClosedAccountsJoint = 0;
        this.dpdGreaterThan90ClosedAccountsJoint = 0;

        this.dpd0AllAccountsGuarantor = 0;
        this.dpd1to30AllAccountsGuarantor = 0;
        this.dpd31to90AllAccountsGuarantor = 0;
        this.dpdGreaterThan90AllAccountsGuarantor = 0;

        this.dpd0LiveAccountsGuarantor = 0;
        this.dpd1to30LiveAccountsGuarantor = 0;
        this.dpd31to90LiveAccountsGuarantor = 0;
        this.dpdGreaterThan90LiveAccountsGuarantor = 0;

        this.dpd0ClosedAccountsGuarantor = 0;
        this.dpd1to30ClosedAccountsGuarantor = 0;
        this.dpd31to90ClosedAccountsGuarantor = 0;
        this.dpdGreaterThan90ClosedAccountsGuarantor = 0;

        this.subStd=0;
        this.settled=0;
        this.resturctured=0;
        this.dubDebt=0;
    }

}

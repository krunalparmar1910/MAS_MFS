package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_information_balance_sheet_dt_equity_and_liabilities_equity", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equity extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "equity_and_liabilities_id")
    private EquityAndLiabilities equityAndLiabilities;

    @JsonManagedReference
    @OneToOne(mappedBy = "equity", cascade = CascadeType.ALL, orphanRemoval = true)
    private EquityAttributableToOwnersOfParent equityAttributableToOwnersOfParent;

    @Column(name = "share_application_money_pending_allotment")
    private String shareApplicationMoneyPendingAllotment;

    @Column(name = "total_equity")
    private String totalEquity;

    @Column(name = "money_received_against_share_warrants")
    private String moneyReceivedAgainstShareWarrants;

    @Column(name = "non_controlling_interest")
    private String nonControllingInterest;
}

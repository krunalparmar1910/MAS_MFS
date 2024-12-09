package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "financial_information_balance_sheet", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSheet extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_id")
    private FinancialInformation financialInformation;

    @Column(name = "total_current_liability")
    private String totalCurrentLiability;

    @Column(name = "total_inventory")
    private String totalInventory;

    @Column(name = "total_el")
    private String totalEL;

    @Column(name = "trade_receivables")
    private String tradeReceivables;

    @Column(name = "total_liability")
    private String totalLiability;

    @Column(name = "trade_payables")
    private String tradePayables;

    @Column(name = "total_investment")
    private String totalInvestment;

    @Column(name = "total_assets")
    private String totalAssets;

    @Column(name = "total_fixed_asset")
    private String totalFixedAsset;

    @Column(name = "total_equity")
    private String totalEquity;

    @Column(name = "cash_and_cash_eqv")
    private String cashAndCashEqv;

    @Column(name = "total_current_asset")
    private String totalCurrentAsset;
}

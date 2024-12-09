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
@Table(name = "fi_bs_dt_assets_current_assets_current_financial_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentFinancialAssets extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "current_assets_id")
    private CurrentAssets currentAssets;

    @Column(name = "loans_current")
    private String loansCurrent;

    @Column(name = "total_current_financial_assets")
    private String totalCurrentFinancialAssets;

    @Column(name = "bank_balance_other_than_cash_and_cash_equivalents")
    private String bankBalanceOtherThanCashAndCashEquivalents;

    @Column(name = "other_current_financial_assets")
    private String otherCurrentFinancialAssets;

    @Column(name = "current_investments")
    private String currentInvestments;

    @Column(name = "cash_and_cash_equivalents")
    private String cashAndCashEquivalents;

    @Column(name = "trade_receivables_current")
    private String tradeReceivablesCurrent;
}

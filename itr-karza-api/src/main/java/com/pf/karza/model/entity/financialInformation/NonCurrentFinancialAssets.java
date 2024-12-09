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
@Table(name = "fi_bs_dt_assets_non_current_assets_non_current_financial_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonCurrentFinancialAssets extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "non_current_assets_id")
    private NonCurrentAssets nonCurrentAssets;

    @Column(name = "trade_receivables_non_current")
    private String tradeReceivablesNonCurrent;

    @Column(name = "loans_non_current")
    private String loansNonCurrent;

    @Column(name = "total_non_current_financial_assets")
    private String totalNonCurrentFinancialAssets;

    @Column(name = "other_non_current_financial_assets")
    private String otherNonCurrentFinancialAssets;

    @Column(name = "non_current_investments")
    private String nonCurrentInvestments;
}

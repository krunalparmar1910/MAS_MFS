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
@Table(name = "financial_information_ratios_profitability_ratios", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitabilityRatios extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_ratios_id")
    private Ratios ratios;

    @Column(name = "return_on_equity")
    private String returnOnEquity;

    @Column(name = "net_profit_margin")
    private String netProfitMargin;

    @Column(name = "operating_profit_margin")
    private String operatingProfitMargin;

    @Column(name = "return_on_assets")
    private String returnOnAssets;

    @Column(name = "gross_profit_margin")
    private String grossProfitMargin;

    @Column(name = "ebitda_margin")
    private String ebitdaMargin;
}

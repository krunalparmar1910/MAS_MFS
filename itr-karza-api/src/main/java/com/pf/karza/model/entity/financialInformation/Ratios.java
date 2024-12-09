package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "financial_information_ratios", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ratios extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_id")
    private FinancialInformation financialInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "ratios", cascade = CascadeType.ALL, orphanRemoval = true)
    private ActivityRatios activityRatios;

    @JsonManagedReference
    @OneToOne(mappedBy = "ratios", cascade = CascadeType.ALL, orphanRemoval = true)
    private SolvencyRatios solvencyRatios;

    @JsonManagedReference
    @OneToOne(mappedBy = "ratios", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfitabilityRatios profitabilityRatios;

    @JsonManagedReference
    @OneToOne(mappedBy = "ratios", cascade = CascadeType.ALL, orphanRemoval = true)
    private GrowthRatios growthRatios;

    @JsonManagedReference
    @OneToOne(mappedBy = "ratios", cascade = CascadeType.ALL, orphanRemoval = true)
    private LiquidityRatios liquidityRatios;
}

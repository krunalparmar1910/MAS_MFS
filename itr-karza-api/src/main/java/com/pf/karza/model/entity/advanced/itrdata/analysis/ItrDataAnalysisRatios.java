package com.pf.karza.model.entity.advanced.itrdata.analysis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_analysis_ratios", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalysisRatios extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_analysis_id")
    @JsonBackReference
    private ItrDataAnalysis itrDataAnalysis;

    @JsonProperty("activityRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisActivityRatios itrDataAnalysisActivityRatios;

    @JsonProperty("liquidityRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisLiquidityRatios itrDataAnalysisLiquidityRatios;

    @JsonProperty("solvencyRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisSolvencyRatios itrDataAnalysisSolvencyRatios;

    @JsonProperty("growthRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalsysisGrowthRatios itrDataAnalsysisGrowthRatios;

    @JsonProperty("profitabilityRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisProfitabilityRatios itrDataAnalysisProfitabilityRatios;

    @JsonProperty("cashFlowRatios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisRatios", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisCashFlowRatios itrDataAnalysisCashFlowRatios;
}

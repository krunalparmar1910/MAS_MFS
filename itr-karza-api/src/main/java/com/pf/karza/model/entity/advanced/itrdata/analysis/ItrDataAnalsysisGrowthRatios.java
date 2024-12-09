package com.pf.karza.model.entity.advanced.itrdata.analysis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_analysis_growth_ratios", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalsysisGrowthRatios extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_analysis_ratios_id")
    @JsonBackReference
    private ItrDataAnalysisRatios itrDataAnalysisRatios;

    private BigDecimal totalAssetsGrowth;

    private BigDecimal totalLiabilitiesGrowth;

    private BigDecimal netWorthGrowth;

    private BigDecimal netSalesGrowth;
}

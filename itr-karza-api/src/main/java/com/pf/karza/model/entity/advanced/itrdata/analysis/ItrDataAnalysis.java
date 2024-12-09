package com.pf.karza.model.entity.advanced.itrdata.analysis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.ItrDataAssmtYear;
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
@Table(name = "itr_data_analysis", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalysis extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_assmt_yr_id")
    @JsonBackReference
    private ItrDataAssmtYear itrDataAssmtYear;

    @JsonProperty("turnoverDetails")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysis", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisTurnoverDetails itrDataAnalysisTurnoverDetails;

    @JsonProperty("ratios")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysis", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisRatios itrDataAnalysisRatios;

    @JsonProperty("mpbf")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysis", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisMpbf itrDataAnalysisMpbf;

    @JsonProperty("incDtls")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysis", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisIncDtls itrDataAnalysisIncDtls;

    @JsonProperty("complianceData")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysis", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisComplianceData itrDataAnalysisComplianceData;
}

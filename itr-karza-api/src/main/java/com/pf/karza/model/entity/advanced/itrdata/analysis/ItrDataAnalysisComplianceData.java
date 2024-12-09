package com.pf.karza.model.entity.advanced.itrdata.analysis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Entity
@Table(name = "itr_data_analysis_compliance_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalysisComplianceData extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_analysis_id")
    @JsonBackReference
    private ItrDataAnalysis itrDataAnalysis;

    private Boolean revised;

    private Boolean delay;

    @JsonProperty("default")
    private Boolean defaultValue;

    private Boolean lateFee;

    private Boolean intrstFee;

    private Boolean demandNotice;

    private Boolean highValTrnsctn;
}

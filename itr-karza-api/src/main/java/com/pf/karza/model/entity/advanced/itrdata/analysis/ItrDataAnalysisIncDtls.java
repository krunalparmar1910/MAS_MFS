package com.pf.karza.model.entity.advanced.itrdata.analysis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "itr_data_analysis_inc_dtls", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalysisIncDtls extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_analysis_id")
    @JsonBackReference
    private ItrDataAnalysis itrDataAnalysis;

    @JsonProperty("hdWiseInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisIncDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisIncDtlsHdWiseInc itrDataAnalysisIncDtlsHdWiseInc;

    @JsonProperty("incTrnd")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisIncDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisIncDtlsIncTrnd itrDataAnalysisIncDtlsIncTrnd;

    @JsonProperty("incomeConcentration")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisIncDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisIncDtlsIncomeConcentration itrDataAnalysisIncDtlsIncomeConcentration;

    @JsonProperty("emplymntDtls")
    @JsonManagedReference
    @OneToMany(mappedBy = "itrDataAnalysisIncDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItrDataAnalysisIncDtlsEmplymntDtls> itrDataAnalysisIncDtlsEmplymntDtls;
}

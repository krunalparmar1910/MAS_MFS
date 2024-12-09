package com.pf.karza.model.entity.advanced.itrdata;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.analysis.ItrDataAnalysis;
import com.pf.karza.model.entity.advanced.itrdata.fininfo.ItrDataFinInfo;
import com.pf.karza.model.entity.advanced.itrdata.othdtls.ItrDataOthDtls;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.ItrDataTaxDetails;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_assmt_yr", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItrDataAssmtYear extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "itr_data_id")
    @JsonBackReference
    private ItrData itrData;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "fin_info_json")
    private String finInfoJson;

    @JsonProperty("finInfo")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAssmtYear", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfo itrDataFinInfo;

    @Column(name = "sal_dtls_json")
    private String salDtlsJson;

    @Column(name = "oth_dtls_json")
    private String othDtlsJson;

    @JsonProperty("othDtls")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAssmtYear", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataOthDtls itrDataOthDtls;

    @Column(name = "sch_json")
    private String schJson;

    @JsonProperty("taxDetails")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAssmtYear", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetails itrDataTaxDetails;

    @JsonProperty("analysis")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAssmtYear", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysis itrDataAnalysis;
}

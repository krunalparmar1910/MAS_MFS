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

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_analysis_mpbf", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataAnalysisMpbf extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_analysis_id")
    @JsonBackReference
    private ItrDataAnalysis itrDataAnalysis;

    @JsonProperty("methOne")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisMpbf", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisMpbfMethOne itrDataAnalysisMpbfMethOne;

    @JsonProperty("methTwo")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataAnalysisMpbf", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataAnalysisMpbfMethTwo itrDataAnalysisMpbfMethTwo;

    private BigDecimal ttlCurAsts;

    private BigDecimal ttlCurLiabExcptBnkBorr;

    private BigDecimal wkgCapGap;
}

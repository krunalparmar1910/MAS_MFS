package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

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
@Table(name = "itr_data_tax_details_cmptn", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsCmptn extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_id")
    @JsonBackReference
    private ItrDataTaxDetails itrDataTaxDetails;

    private BigDecimal sal;

    private BigDecimal incFrmHP;

    @JsonProperty("pgbp")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptn", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnPgbp itrDataTaxDetailsCmptnPgbp;

    @JsonProperty("capGain")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptn", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnCapGain itrDataTaxDetailsCmptnCapGain;

    @JsonProperty("ifos")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptn", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnIfos itrDataTaxDetailsCmptnIfos;

    private BigDecimal ttlOfHdWiseInc;

    private BigDecimal lssOfCurrYr;

    private BigDecimal balAfterSetOffLosses;

    private BigDecimal brtFwdLoss;

    private BigDecimal grsTtlInc;

    private BigDecimal incChrgblSpclRt;

    @JsonProperty("dedUnderChpVIA")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptn", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnDedUnderChpVIA itrDataTaxDetailsCmptnDedUnderChpVIA;

    @JsonProperty("incNotPartOfTTlInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptn", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnIncNotPartOfTTlInc itrDataTaxDetailsCmptnIncNotPartOfTTlInc;

    private BigDecimal ttlInc;

    private BigDecimal incChrgdSpclTxRt;

    private BigDecimal incChrgdNrmlTxRt;

    private BigDecimal netAgriInc;

    private BigDecimal aggrInc;

    private BigDecimal lossCrdFwd;

    private BigDecimal dmdIncUndSec115Jc;
}

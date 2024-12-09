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
@Table(name = "itr_data_tax_details_tax_liab", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsTaxLiab extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_id")
    @JsonBackReference
    private ItrDataTaxDetails itrDataTaxDetails;

    @JsonProperty("onDmdInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsTaxLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTaxLiabOnDmdInc itrDataTaxDetailsTaxLiabOnDmdInc;

    @JsonProperty("onTtlInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsTaxLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTaxLiabOnTtlInc itrDataTaxDetailsTaxLiabOnTtlInc;

    private BigDecimal grsTxPybl;

    private BigDecimal crdt;

    private BigDecimal txPyblAftrCrdt;

    private BigDecimal ttlTaxRlf;

    private BigDecimal netTaxLiability;

    @JsonProperty("intAndFeePybl")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsTaxLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTaxLiabIntAndFeePybl itrDataTaxDetailsTaxLiabIntAndFeePybl;

    private BigDecimal aggregateLiability;

    @JsonProperty("txPd")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsTaxLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTaxLiabTxPd itrDataTaxDetailsTaxLiabTxPd;

    private BigDecimal amountPayable;

    private BigDecimal refund;
}

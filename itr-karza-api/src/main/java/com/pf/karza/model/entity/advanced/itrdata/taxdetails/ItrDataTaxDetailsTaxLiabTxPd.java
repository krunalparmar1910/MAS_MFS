package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

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
@Table(name = "itr_data_tax_details_tax_liab_tx_pd", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsTaxLiabTxPd extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_tax_liab_id")
    @JsonBackReference
    private ItrDataTaxDetailsTaxLiab itrDataTaxDetailsTaxLiab;

    private BigDecimal totalAdvanceTaxPaid;

    private BigDecimal totalTdsClaimed;

    private BigDecimal totalTcsClaimed;

    private BigDecimal totalSelfAssessmentTaxPaid;

    private BigDecimal totalTaxesPaid;
}

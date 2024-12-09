package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_financial_info_tax_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxDetails extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_id", referencedColumnName = "id")
    private FinancialInfo financialInfo;

    private String refund;

    @Column(name = "taxes_paid")
    private String taxesPaid;

    @Column(name = "aggregate_liability")
    private String aggregateLiability;

    @Column(name = "net_tax_liability")
    private String netTaxLiability;

    @Column(name = "total_interest_and_fee_payable")
    private String totalInterestAndFeePayable;

    @Column(name = "total_advance_tax_paid")
    private String totalAdvanceTaxPaid;

    @Column(name = "total_tds_claimed")
    private String totalTdsClaimed;

    @Column(name = "total_tcs_claimed")
    private String totalTcsClaimed;

    @Column(name = "total_self_assessment_tax_paid")
    private String totalSelfAssessmentTaxPaid;

    @Column(name = "amount_payable")
    private String amountPayable;
}

package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ais_data_details_payment_of_taxes", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOfTaxes extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ais_data_details_id")
    @JsonBackReference
    private AisDetails aisDetails;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "cess")
    private String cess;

    @Column(name = "challan_iden_number")
    private String challanIdenNumber;

    @Column(name = "date_of_deposit")
    private String dateOfDeposit;

    @Column(name = "major_hd")
    private String majorHd;

    @Column(name = "minor_hd")
    private String minorHd;

    @Column(name = "others")
    private String others;

    @Column(name = "surcharge_education")
    private String surchargeEducation;

    @Column(name = "tax")
    private String tax;

    @Column(name = "total_tax")
    private String totalTax;
}

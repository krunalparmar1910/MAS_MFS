package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "26as_data_other_tax_paid", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherTaxPaid extends BaseId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "26as_data_id")
    private TwentySixASData twentySixASData;

    @Column(name = "surchg")
    @JsonProperty("surchg")
    private String surcharge;

    @Column(name = "oth")
    private String oth;

    @Column(name = "bsr_code")
    private String bsrCode;

    @Column(name = "major_head")
    private String majorHead;

    @Column(name = "tax_collected")
    private String taxCollected;

    @Column(name = "challan_no")
    private String challanNo;

    @Column(name = "minor_head")
    private String minorHead;

    @Column(name = "dt_of_deposit")
    private String dtOfDeposit;

    @Column(name = "total_tax")
    private String totalTax;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "ed_cess")
    private String edCess;

    private String penalty;

    private String interest;

    // Include other fields and relationships here as needed
}

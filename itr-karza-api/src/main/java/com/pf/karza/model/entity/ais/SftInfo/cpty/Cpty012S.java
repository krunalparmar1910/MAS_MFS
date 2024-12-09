package com.pf.karza.model.entity.ais.SftInfo.cpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.Sft012S;
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
@Table(name = "ais_data_details_sft_info_sft_012S_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpty012S extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sft012S_id")
    @JsonBackReference
    private Sft012S sft012S;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "filer_id")
    private String filerId;

    @Column(name = "filer_name")
    private String filerName;

    @Column(name = "joint_holder_count")
    private String jointHolderCount;

    @Column(name = "property_address")
    private String propertyAddress;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "related_value")
    private String relatedValue;

    @Column(name = "reported_on")
    private String reportedOn;

    @Column(name = "stamp_value")
    private String stampValue;

    @Column(name = "status")
    private String status;

    @Column(name = "total_value")
    private String totalValue;

    @Column(name = "trans_date")
    private String transDate;

    @Column(name = "trans_feedback")
    private String transFeedback;

    @Column(name = "tsn_id")
    private String tsnId;

    @Column(name = "txn_relation")
    private String txnRelation;

    @Column(name = "txn_type")
    private String txnType;

}

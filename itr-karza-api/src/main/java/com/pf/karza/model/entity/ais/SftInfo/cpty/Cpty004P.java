package com.pf.karza.model.entity.ais.SftInfo.cpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.Sft004P;
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
@Table(name = "ais_data_details_sft_info_sft_004P_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpty004P extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sft004P_id")
    @JsonBackReference
    private Sft004P sft004P;

    @Column(name = "account_holders_count")
    private String accountHoldersCount;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "amount_assigned")
    private String amountAssigned;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amt_paid")
    private String amtPaid;

    @Column(name = "amt_received")
    private String amtReceived;

    @Column(name = "filer_id")
    private String filerId;

    @Column(name = "filer_name")
    private String filerName;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "reported_on")
    private String reportedOn;

    @Column(name = "status")
    private String status;

    @Column(name = "trans_feedback")
    private String transFeedback;

    @Column(name = "tsn_id")
    private String tsnId;

    @Column(name = "txn_relation")
    private String txnRelation;

}

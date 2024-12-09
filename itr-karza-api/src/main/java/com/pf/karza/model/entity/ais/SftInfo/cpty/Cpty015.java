package com.pf.karza.model.entity.ais.SftInfo.cpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.Sft015;
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
@Table(name = "ais_data_details_sft_info_sft_015_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpty015 extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sft015_id")
    @JsonBackReference
    private Sft015 sft015;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amt_paid")
    private String amtPaid;

    @Column(name = "amt_received")
    private String amtReceived;

    @Column(name = "cash_received")
    private String cashReceived;

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

}

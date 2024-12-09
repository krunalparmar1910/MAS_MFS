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
@Table(name = "ais_data_details_tds_tcs_info_booking_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "tds_tcs_info_id")
    @JsonBackReference
    private TdsTcsInfo tdsTcsInfo;

    @Column(name = "amount_deducted")
    private String amountDeducted;

    @Column(name = "amount_deposited")
    private String amountDeposited;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "amt_paid")
    private String amtPaid;

    @Column(name = "filer_id")
    private String filerId;

    @Column(name = "filer_name")
    private String filerName;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "quarter")
    private String quarter;

    @Column(name = "status")
    private String status;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "trans_feedback")
    private String transFeedback;

    @Column(name = "tsn_id")
    private String tsnId;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "full_property_address")
    private String fullPropertyAddress;

    @Column(name = "prpty_val")
    private String prptyVal;


}


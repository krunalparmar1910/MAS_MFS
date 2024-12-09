package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "26as_data_tds_immv_tds_buyer_tenant_tds_buyer_tenant_record_tds_buyer_tenant_summary", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsBuyerOrTenantSummary extends BaseId {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "buyer_tenant_record_id")
    private TdsBuyerOrTenantRecord buyerTenantRecord;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "name_of_deductee")
    private String nameOfDeductee;

    @Column(name = "pan")
    private String pan;

    @Column(name = "transc_date")
    private String transcDate;

    @Column(name = "transc_amt")
    private String transcAmt;

    @Column(name = "tds_deposited")
    private String tdsDeposited;

    @Column(name = "ttl_oth_tds")
    private String ttlOthTds;

    @Column(name = "ttl_amt_oth_tds")
    private String ttlAmtOthTDS;

    @Column(name = "ttl_demd_paymnt")
    private String ttlDemdPaymnt;

    @Column(name = "ttl_tds_deposited")
    private String ttlTdsDeposited;
}
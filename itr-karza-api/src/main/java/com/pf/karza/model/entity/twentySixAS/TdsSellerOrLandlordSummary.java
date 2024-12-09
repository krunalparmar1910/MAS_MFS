package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonAlias;
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
@Table(name = "26as_data_tds_immv_tds_seller_landlord_tds_seller_landlord_record_tds_seller_landlord_summary", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsSellerOrLandlordSummary extends BaseId {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "seller_landlord_record_id")
    private TdsSellerOrLandlordRecord sellerLandlordRecord;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "name_of_deductor")
    @JsonAlias({"nameOfDeductor"})
    private String nameOfDeductor;

    @Column(name = "pan")
    private String pan;

    @Column(name = "transc_date")
    private String transcDate;

    @Column(name = "transc_amt")
    private String transcAmt;

    @Column(name = "tds_deposited")
    private String tdsDeposited;


    @Column(name = "ttl_ddmnd_pymnt")
    private String ttlDdmndPymnt;

    @Column(name = "ttl_tds_depstd")
    private String ttlTdsDepstd;

}
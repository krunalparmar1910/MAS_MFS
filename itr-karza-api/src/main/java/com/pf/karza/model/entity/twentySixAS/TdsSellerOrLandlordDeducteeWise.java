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
@Table(name = "26as_data_tds_immv_tds_seller_landlord_tds_seller_landlord_record_tds_seller_landlord_deductee_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsSellerOrLandlordDeducteeWise extends BaseId {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "seller_landlord_record_id")
    private TdsSellerOrLandlordRecord sellerLandlordRecord;

    @Column(name = "tds_cert_no")
    private String tdsCertNo;

    @Column(name = "name_of_deductor")
    @JsonAlias({"nameOfDeductor"})
    private String nameOfDeductor;

    @Column(name = "pan")
    private String pan;

    @Column(name = "date_of_dep")
    private String dateOfDep;

    @Column(name = "status")
    private String status;

    @Column(name = "date_of_booking")
    private String dateOfBooking;

    @Column(name = "demd_paymnt")
    private String demdPaymnt;

    @Column(name = "tds_deposited")
    private String tdsDeposited;

    // this field is not in documentation but is present in response
    @Column(name = "section")
    private String section;
}

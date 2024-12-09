package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.constant.ModelConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "advanced_twenty_six_as_tds_immv_buyer_deductee_wise_record", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTDSImmvBuyerDeducteeWiseRecord extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_immv_buyer_record_id")
    @JsonBackReference
    private AdvancedTwentySixASTDSImmvBuyerRecord advancedTwentySixASTDSImmvBuyerRecord;

    @Column(name = "tds_cert_no")
    private String tdsCertNo;

    @Column(name = "name_of_deductee")
    private String nameOfDeductee;

    @Column(name = "pan")
    private String pan;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "date_of_dep")
    private LocalDate dateOfDep;

    @Column(name = "status")
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "date_of_booking")
    private LocalDate dateOfBooking;

    @Column(name = "demd_paymnt")
    private BigDecimal demdPaymnt;

    @Column(name = "tds_deposited")
    private BigDecimal tdsDeposited;

    @Column(name = "amt_oth_tds")
    private String amtOthTDS;

    // this field is not in documentation but is present in response
    @Column(name = "section")
    private String section;
}

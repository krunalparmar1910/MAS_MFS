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
@Table(name = "advanced_twenty_six_as_tds_immv_seller_summary_record", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTDSImmvSellerSummaryRecord extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_immv_seller_record_id")
    @JsonBackReference
    private AdvancedTwentySixASTDSImmvSellerRecord advancedTwentySixASTDSImmvSellerRecord;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "name_of_deductor")
    private String nameOfDeductor;

    @Column(name = "pan")
    private String pan;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "transc_date")
    private LocalDate transcDate;

    @Column(name = "transc_amt")
    private BigDecimal transcAmt;

    @Column(name = "tds_deposited")
    private BigDecimal tdsDeposited;
}

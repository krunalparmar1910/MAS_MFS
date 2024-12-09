package com.pf.karza.model.entity.twentySixAS;

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
@Table(name = "26as_data_tds_details_tds_deductor_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsDeductorWise extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tds_id")
    private Tds tds;

    @Column(name = "section")
    private String section;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "amount_credited")
    private String amountCredited;

    @Column(name = "tax_deducted")
    private String taxDeducted;

    @Column(name = "name_of_deductor")
    private String nameOfDeductor;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status_of_booking")
    private String statusOfBooking;

    @Column(name = "tan")
    private String tan;

    @Column(name = "tds_deposited")
    private String tdsDeposited;

    @Column(name = "date_of_booking")
    private String dateOfBooking;
}
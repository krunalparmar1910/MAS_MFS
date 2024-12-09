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
@Table(name = "26as_data_tcs_details_tcs_collector_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcsCollectorWise extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tcs_details_id")
    private TcsDetails tcsDetails;

    @Column(name = "amount_debited")
    private String amountDebited;

    @Column(name = "section")
    private String section;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "tax_collected")
    private String taxCollected;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "name_of_collector")
    private String nameOfCollector;

    @Column(name = "status_of_booking")
    private String statusOfBooking;

    @Column(name = "tan")
    private String tan;

    @Column(name = "tcs_deposited")
    private String tcsDeposited;

    @Column(name = "date_of_booking")
    private String dateOfBooking;

    // Include other fields and relationships here as needed
}

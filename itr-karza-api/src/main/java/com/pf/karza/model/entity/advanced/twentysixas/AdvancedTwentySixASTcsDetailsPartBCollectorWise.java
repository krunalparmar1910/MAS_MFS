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
@Table(name = "advanced_twenty_six_as_tcs_details_part_b_collector_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTcsDetailsPartBCollectorWise extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tcs_details_part_b_id")
    @JsonBackReference
    private AdvancedTwentySixASTcsDetailsPartB advancedTwentySixASTcsDetailsPartB;

    @Column(name = "name_of_collector")
    private String nameOfCollector;

    @Column(name = "tan")
    private String tan;

    @Column(name = "section")
    private String section;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "status_of_booking")
    private String statusOfBooking;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "date_of_booking")
    private LocalDate dateOfBooking;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "amount_debited")
    private BigDecimal amountDebited;

    @Column(name = "tax_collected")
    private BigDecimal taxCollected;

    @Column(name = "tcs_deposited")
    private BigDecimal tcsDeposited;
}

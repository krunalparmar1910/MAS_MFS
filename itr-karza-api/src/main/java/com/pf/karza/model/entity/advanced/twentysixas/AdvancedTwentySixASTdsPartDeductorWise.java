package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.karza.constant.ModelConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AdvancedTwentySixASTdsPartDeductorWise extends BaseId {
    @Column(name = "name_of_deductor")
    private String nameOfDeductor;

    @Column(name = "tan")
    private String tan;

    @Column(name = "amount_credited")
    private BigDecimal amountCredited;

    @Column(name = "tax_deducted")
    private BigDecimal taxDeducted;

    @Column(name = "tds_deposited")
    private BigDecimal tdsDeposited;

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
}

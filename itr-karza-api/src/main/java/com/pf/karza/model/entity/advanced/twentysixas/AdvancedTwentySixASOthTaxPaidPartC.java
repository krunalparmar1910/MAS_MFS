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
@Table(name = "advanced_twenty_six_as_oth_tax_paid_part_c", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASOthTaxPaidPartC extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_data_entry_id")
    @JsonBackReference
    private AdvancedTwentySixASDataEntry advancedTwentySixASDataEntry;

    @Column(name = "major_head")
    private String majorHead;

    @Column(name = "minor_head")
    private String minorHead;

    @Column(name = "tax_collected")
    private BigDecimal taxCollected;

    @Column(name = "surchg")
    private BigDecimal surchg;

    @Column(name = "ed_cess")
    private BigDecimal edCess;

    @Column(name = "oth")
    private String oth;

    @Column(name = "total_tax")
    private BigDecimal totalTax;

    @Column(name = "bsr_code")
    private String bsrCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "dt_of_deposit")
    private LocalDate dtOfDeposit;

    @Column(name = "challan_no")
    private String challanNo;

    @Column(name = "remarks")
    private String remarks;
}

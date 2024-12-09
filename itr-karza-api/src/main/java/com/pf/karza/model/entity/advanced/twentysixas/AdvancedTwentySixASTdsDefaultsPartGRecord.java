package com.pf.karza.model.entity.advanced.twentysixas;

import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AdvancedTwentySixASTdsDefaultsPartGRecord extends BaseId {
    @Column(name = "shrt_pmt")
    private BigDecimal shrtPmt;

    @Column(name = "shrt_ded")
    private BigDecimal shrtDed;

    @Column(name = "int_tds_pay_def")
    private BigDecimal intTDSPayDef;

    @Column(name = "int_tds_ded_def")
    private BigDecimal intTDSDedDef;

    @Column(name = "lt_filing_fee")
    private BigDecimal ltFilingFee;

    @Column(name = "interest")
    private BigDecimal interest;

    @Column(name = "ttl_def")
    private BigDecimal ttlDef;
}

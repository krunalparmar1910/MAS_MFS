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
public abstract class AdvancedTwentySixASTdsPartSummary extends BaseId {
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
}

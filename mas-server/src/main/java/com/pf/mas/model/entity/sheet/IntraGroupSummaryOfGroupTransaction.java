package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetDateValueEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "intra_group_summary_of_group_transactions", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IntraGroupSummaryOfGroupTransaction extends BaseSheetDateValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "against_gstin")
    private String againstGSTIN;

    @Column(name = "revenue")
    private String revenue;

    @Column(name = "revenue_numeric")
    private BigDecimal revenueNumeric;

    @ManyToOne
    @JoinColumn(name = "intra_group_id")
    private IntraGroup intraGroup;
}

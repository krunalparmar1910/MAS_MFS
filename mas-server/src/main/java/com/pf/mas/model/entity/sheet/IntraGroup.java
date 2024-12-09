package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "intra_group", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IntraGroup extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gstin")
    private String gstin;

    @OneToMany(mappedBy = "intraGroup")
    private List<IntraGroupRevenueValue> intraGroupRevenueValues;

    @OneToMany(mappedBy = "intraGroup")
    private List<IntraGroupPurchasesAndExpensesValue> intraGroupPurchasesAndExpensesValues;

    @OneToMany(mappedBy = "intraGroup")
    private List<IntraGroupSummaryOfGroupTransaction> intraGroupSummaryOfGroupTransactions;
}

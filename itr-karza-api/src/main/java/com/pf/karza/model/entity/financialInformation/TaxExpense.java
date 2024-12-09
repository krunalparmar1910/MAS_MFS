package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_information_profit_and_loss_dt_tax_expense", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxExpense extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "profit_and_loss_dt_id")
    private ProfitAndLossDt profitAndLossDt;

    @Column(name = "current_tax")
    private String currentTax;

    @Column(name = "total_tax_expense")
    private String totalTaxExpense;

    @Column(name = "deferred_tax")
    private String deferredTax;
}

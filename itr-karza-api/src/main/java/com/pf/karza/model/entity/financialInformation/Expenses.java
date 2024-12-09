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
@Table(name = "financial_information_profit_and_loss_dt_expenses", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expenses extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "profit_and_loss_dt_id")
    private ProfitAndLossDt profitAndLossDt;

    @Column(name = "employee_benefit_expense")
    private String employeeBenefitExpense;

    @Column(name = "cost_of_materials_consumed")
    private String costOfMaterialsConsumed;

    @Column(name = "changes_in_inventories_of_finished_goods_work_in_progress_and_stock_in_trade")
    private String changesInInventoriesOfFinishedGoodsWorkInProgressAndStockInTrade;

    @Column(name = "expenditure_on_production_transportation_and_other_expenditure_pertaining_to_exploration_and_production_activities")
    private String expenditureOnProductionTransportationAndOtherExpenditurePertainingToExplorationAndProductionActivities;

    @Column(name = "total_expenses")
    private String totalExpenses;

    @Column(name = "other_expenses")
    private String otherExpenses;

    @Column(name = "depreciation_depletion_and_amortisation_expense")
    private String depreciationDepletionAndAmortisationExpense;

    @Column(name = "purchases_of_stock_in_trade")
    private String purchasesOfStockInTrade;

    @Column(name = "finance_costs")
    private String financeCosts;
}

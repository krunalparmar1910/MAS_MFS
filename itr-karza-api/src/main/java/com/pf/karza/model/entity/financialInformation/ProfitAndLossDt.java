package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "financial_information_profit_and_loss_dt", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitAndLossDt extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_id")
    private FinancialInformation financialInformation;

    @Column(name = "share_of_profit_or_loss_of_associates_and_joint_ventures")
    private String shareOfProfitOrLossOfAssociatesAndJointVenturesAccountedForUsingEquityMethod;

    @Column(name = "tax_expense_of_discontinued_operations")
    private String taxExpenseOfDiscontinuedOperations;

    @Column(name = "profit_or_loss_from_discontinued_operations_before_tax")
    private String profitOrLossFromDiscontinuedOperationsBeforeTax;

    @Column(name = "profit_before_exceptional_items_and_tax")
    private String profitBeforeExceptionalItemsAndTax;

    @Column(name = "income_from_bp")
    private String incomeFromBP;

    @Column(name = "exceptional_items_before_tax")
    private String exceptionalItemsBeforeTax;

    @Column(name = "income_from_os")
    private String incomeFromOS;

    @Column(name = "gross_profit")
    private String grossProfit;

    @Column(name = "total_profit_or_loss_for_period")
    private String totalProfitOrLossForPeriod;

    @Column(name = "total_profit_or_loss_from_discontinued_operations_after_tax")
    private String totalProfitOrLossFromDiscontinuedOperationsAfterTax;

    @Column(name = "net_movement_in_regulatory_deferral_account_balances_related_to_profit_or_loss_and_the_related_deferred_tax_movement")
    private String netMovementInRegulatoryDeferralAccountBalancesRelatedToProfitOrLossAndTheRelatedDeferredTaxMovement;

    @Column(name = "income_from_salary")
    private String incomeFromSalary;

    @Column(name = "total_profit_before_tax")
    private String totalProfitBeforeTax;

    @Column(name = "income_from_hp")
    private String incomeFromHP;

    @Column(name = "total_profit_or_loss_for_period_from_continuing_operations")
    private String totalProfitOrLossForPeriodFromContinuingOperations;

    @Column(name = "income_from_cg")
    private String incomeFromCG;

    @JsonManagedReference
    @OneToOne(mappedBy = "profitAndLossDt", cascade = CascadeType.ALL, orphanRemoval = true)
    private Expenses expenses;

    @JsonManagedReference
    @OneToOne(mappedBy = "profitAndLossDt", cascade = CascadeType.ALL, orphanRemoval = true)
    private Income income;

    @JsonManagedReference
    @OneToOne(mappedBy = "profitAndLossDt", cascade = CascadeType.ALL, orphanRemoval = true)
    private TaxExpense taxExpense;
}

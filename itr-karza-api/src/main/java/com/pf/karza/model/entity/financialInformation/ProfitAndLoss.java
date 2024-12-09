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
@Table(name = "financial_information_profit_and_loss", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitAndLoss extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_id")
    private FinancialInformation financialInformation;

    @Column(name = "purchases")
    private String purchases;

    @Column(name = "direct_cost")
    private String directCost;

    @Column(name = "profit_before_tax")
    private String profitBeforeTax;

    @Column(name = "interest_expense")
    private String interestExpense;

    @Column(name = "total_expense")
    private String totalExpense;

    @Column(name = "income_from_bp")
    private String incomeFromBP;

    @Column(name = "income_from_os")
    private String incomeFromOS;

    @Column(name = "profit_after_tax")
    private String profitAfterTax;

    @Column(name = "gross_profit")
    private String grossProfit;

    @Column(name = "income_from_salary")
    private String incomeFromSalary;

    @Column(name = "income_from_hp")
    private String incomeFromHP;

    @Column(name = "total_tax")
    private String totalTax;

    @Column(name = "revenue_from_operations")
    private String revenueFromOperations;

    @Column(name = "ebitda")
    private String ebitda;

    @Column(name = "total_revenue")
    private String totalRevenue;

    @Column(name = "income_from_cg")
    private String incomeFromCG;
}

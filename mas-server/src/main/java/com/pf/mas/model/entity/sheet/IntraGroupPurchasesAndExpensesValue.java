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
import java.util.Locale;

@Entity
@Table(name = "intra_group_purchases_expenses_values", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IntraGroupPurchasesAndExpensesValue extends BaseSheetDateValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross_adjusted_purchases_and_expenses")
    private String grossAdjustedPurchasesAndExpenses;

    @Column(name = "gross_adjusted_purchases_and_expenses_numeric")
    private BigDecimal grossAdjustedPurchasesAndExpensesNumeric;

    @Column(name = "intra_group_purchases_and_expenses_value")
    private String intraGroupPurchasesAndExpenses;

    @Column(name = "intra_group_purchases_and_expenses_value_numeric")
    private BigDecimal intraGroupPurchasesAndExpensesNumeric;

    @Column(name = "percent_of_intra_group_purchases_and_expenses")
    private String percentOfIntraGroupPurchasesAndExpenses;

    @Column(name = "percent_of_intra_group_purchases_and_expenses_numeric")
    private BigDecimal percentOfIntraGroupPurchasesAndExpensesNumeric;

    @Column(name = "adjusted_purchases_and_expenses")
    private String adjustedPurchasesAndExpenses;

    @Column(name = "adjusted_purchases_and_expenses_numeric")
    private BigDecimal adjustedPurchasesAndExpensesNumeric;

    @ManyToOne
    @JoinColumn(name = "intra_group_id")
    private IntraGroup intraGroup;

    public static void setValueForFieldHeader(
            String fieldHeader, IntraGroupPurchasesAndExpensesValue intraGroupPurchasesAndExpensesValue, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "GROSS ADJUSTED PURCHASE AND EXPENSES" -> {
                intraGroupPurchasesAndExpensesValue.setGrossAdjustedPurchasesAndExpenses(value);
                intraGroupPurchasesAndExpensesValue.setGrossAdjustedPurchasesAndExpensesNumeric(numericValue);
            }
            case "INTRA GROUP PURCHASE AND EXPENSES" -> {
                intraGroupPurchasesAndExpensesValue.setIntraGroupPurchasesAndExpenses(value);
                intraGroupPurchasesAndExpensesValue.setIntraGroupPurchasesAndExpensesNumeric(numericValue);
            }
            case "% OF INTRA GROUP PURCHASE AND EXPENSES" -> {
                intraGroupPurchasesAndExpensesValue.setPercentOfIntraGroupPurchasesAndExpenses(value);
                intraGroupPurchasesAndExpensesValue.setPercentOfIntraGroupPurchasesAndExpensesNumeric(numericValue);
            }
            case "ADJUSTED PURCHASE AND EXPENSES" -> {
                intraGroupPurchasesAndExpensesValue.setAdjustedPurchasesAndExpenses(value);
                intraGroupPurchasesAndExpensesValue.setAdjustedPurchasesAndExpensesNumeric(numericValue);
            }
            default -> {/* no field found */}
        }
    }
}

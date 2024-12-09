package com.pf.mas.model.entity.base;

import com.pf.mas.model.entity.FieldDateMonthYear;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseSheetBifurcatedValueEntity implements BaseEntity {
    @Column(name = "amount")
    private String amount;

    @Column(name = "amount_numeric")
    private BigDecimal amountNumeric;

    @Column(name = "pct_share_adjusted_revenue")
    private String percentShareInAdjustedRevenue;

    @Column(name = "pct_share_adjusted_revenue_numeric")
    private BigDecimal percentShareInAdjustedRevenueNumeric;

    @Column(name = "pct_share_adjusted_purchases_expenses")
    private String percentShareInAdjustedPurchasesExpenses;

    @Column(name = "pct_share_adjusted_purchases_expenses_numeric")
    private BigDecimal percentShareInAdjustedPurchasesExpensesNumeric;

    @ManyToOne
    @JoinColumn(name = "field_date_month_year_id")
    private FieldDateMonthYear fieldDateMonthYear;

    public static void setValueForFieldHeader(String fieldHeader, BaseSheetBifurcatedValueEntity baseSheetBifurcatedValueEntity, String value, BigDecimal numericValue) {
        String fieldHeaderTrim = fieldHeader.trim();
        if (StringUtils.containsIgnoreCase(fieldHeaderTrim, "AMOUNT")) {
            baseSheetBifurcatedValueEntity.setAmount(value);
            baseSheetBifurcatedValueEntity.setAmountNumeric(numericValue);
        } else if (StringUtils.containsIgnoreCase(fieldHeaderTrim, "ADJUSTED REVENUE")) {
            baseSheetBifurcatedValueEntity.setPercentShareInAdjustedRevenue(value);
            baseSheetBifurcatedValueEntity.setPercentShareInAdjustedRevenueNumeric(numericValue);
        } else if (StringUtils.containsIgnoreCase(fieldHeaderTrim, "ADJUSTED PURCHASE AND EXPENSES")) {
            baseSheetBifurcatedValueEntity.setPercentShareInAdjustedPurchasesExpenses(value);
            baseSheetBifurcatedValueEntity.setPercentShareInAdjustedPurchasesExpensesNumeric(numericValue);
        }
    }
}

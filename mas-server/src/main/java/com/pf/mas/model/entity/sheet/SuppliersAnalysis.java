package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Locale;

@Entity
@Table(name = "suppliers_analysis", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SuppliersAnalysis extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "supplier_gstin")
    private String supplierGSTIN;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_type")
    private String supplierType;

    @Column(name = "no_of_active_months")
    private Integer noOfActiveMonths;

    @Column(name = "adjusted_purchases_exp")
    private String adjustedPurchasesAndExpenses;

    @Column(name = "adjusted_purchases_exp_numeric")
    private BigDecimal adjustedPurchasesAndExpensesNumeric;

    @Column(name = "percent_of_adjusted_purchases_exp")
    private String percentOfAdjustedPurchasesAndExpenses;

    @Column(name = "percent_of_adjusted_purchases_exp_numeric")
    private BigDecimal percentOfAdjustedPurchasesAndExpensesNumeric;

    @Column(name = "avg_invoice_value")
    private String averageInvoiceValue;

    @Column(name = "avg_invoice_value_numeric")
    private BigDecimal averageInvoiceValueNumeric;

    @Column(name = "monthly_avg_value")
    private String monthlyAverageValue;

    @Column(name = "monthly_avg_value_numeric")
    private BigDecimal monthlyAverageValueNumeric;

    @Column(name = "flag_for_category_change")
    private String flagForCategoryChange;

    @Column(name = "new_or_existing")
    private String newOrExisting;

    public static void setValueForFieldHeader(String fieldHeader, SuppliersAnalysis suppliersAnalysis, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "RANKING" -> suppliersAnalysis.setRanking(SheetReaderUtils.parseIntegerValue(value));
            case "SUPPLIER'S GSTIN" -> suppliersAnalysis.setSupplierGSTIN(value);
            case "SUPPLIER'S NAME" -> suppliersAnalysis.setSupplierName(value);
            case "SUPPLIER TYPE" -> suppliersAnalysis.setSupplierType(value);
            case "NO. OF MONTHS ACTIVE" -> suppliersAnalysis.setNoOfActiveMonths(SheetReaderUtils.parseIntegerValue(value));
            case "ADJUSTED PURCHASES AND EXPENSES" -> {
                suppliersAnalysis.setAdjustedPurchasesAndExpenses(value);
                suppliersAnalysis.setAdjustedPurchasesAndExpensesNumeric(numericValue);
            }
            case "% OF ADJUSTED PURCHASES AND EXPENSES" -> {
                suppliersAnalysis.setPercentOfAdjustedPurchasesAndExpenses(value);
                suppliersAnalysis.setPercentOfAdjustedPurchasesAndExpensesNumeric(numericValue);
            }
            case "AVERAGE INVOICE VALUE" -> {
                suppliersAnalysis.setAverageInvoiceValue(value);
                suppliersAnalysis.setAverageInvoiceValueNumeric(numericValue);
            }
            case "MONTHLY AVERAGE VALUE" -> {
                suppliersAnalysis.setMonthlyAverageValue(value);
                suppliersAnalysis.setMonthlyAverageValueNumeric(numericValue);
            }
            case "FLAG FOR CATEGORY CHANGE" -> suppliersAnalysis.setFlagForCategoryChange(value);
            case "NEW/EXISTING" -> suppliersAnalysis.setNewOrExisting(value);
            default -> {/* no field found */}
        }
    }
}

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
@Table(name = "customers_analysis", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CustomersAnalysis extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "customer_gstin")
    private String customerGSTIN;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "no_of_active_months")
    private Integer noOfActiveMonths;

    @Column(name = "adjusted_revenue")
    private String adjustedRevenue;

    @Column(name = "adjusted_revenue_numeric")
    private BigDecimal adjustedRevenueNumeric;

    @Column(name = "percent_of_adjusted_revenue")
    private String percentOfAdjustedRevenue;

    @Column(name = "percent_of_adjusted_revenue_numeric")
    private BigDecimal percentOfAdjustedRevenueNumeric;

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

    public static void setValueForFieldHeader(String fieldHeader, CustomersAnalysis customersAnalysis, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "RANKING" -> customersAnalysis.setRanking(SheetReaderUtils.parseIntegerValue(value));
            case "CUSTOMER'S GSTIN" -> customersAnalysis.setCustomerGSTIN(value);
            case "CUSTOMER'S NAME" -> customersAnalysis.setCustomerName(value);
            case "CUSTOMER TYPE" -> customersAnalysis.setCustomerType(value);
            case "NO. OF MONTHS ACTIVE" -> customersAnalysis.setNoOfActiveMonths(SheetReaderUtils.parseIntegerValue(value));
            case "ADJUSTED REVENUE" -> {
                customersAnalysis.setAdjustedRevenue(value);
                customersAnalysis.setAdjustedRevenueNumeric(numericValue);
            }
            case "% OF ADJUSTED REVENUE" -> {
                customersAnalysis.setPercentOfAdjustedRevenue(value);
                customersAnalysis.setPercentOfAdjustedRevenueNumeric(numericValue);
            }
            case "AVERAGE INVOICE VALUE" -> {
                customersAnalysis.setAverageInvoiceValue(value);
                customersAnalysis.setAverageInvoiceValueNumeric(numericValue);
            }
            case "MONTHLY AVERAGE VALUE" -> {
                customersAnalysis.setMonthlyAverageValue(value);
                customersAnalysis.setMonthlyAverageValueNumeric(numericValue);
            }
            case "FLAG FOR CATEGORY CHANGE" -> customersAnalysis.setFlagForCategoryChange(value);
            case "NEW/EXISTING" -> customersAnalysis.setNewOrExisting(value);
            default -> {/* no field found */}
        }
    }
}

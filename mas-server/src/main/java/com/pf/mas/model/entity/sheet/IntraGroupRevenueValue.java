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
@Table(name = "intra_group_revenue_values", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IntraGroupRevenueValue extends BaseSheetDateValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross_adjusted_revenue")
    private String grossAdjustedRevenue;

    @Column(name = "gross_adjusted_revenue_numeric")
    private BigDecimal grossAdjustedRevenueNumeric;

    @Column(name = "intra_group_revenue_value")
    private String intraGroupRevenue;

    @Column(name = "intra_group_revenue_value_numeric")
    private BigDecimal intraGroupRevenueNumeric;

    @Column(name = "percent_of_intra_group_revenue")
    private String percentOfIntraGroupRevenue;

    @Column(name = "percent_of_intra_group_revenue_numeric")
    private BigDecimal percentOfIntraGroupRevenueNumeric;

    @Column(name = "adjusted_revenue")
    private String adjustedRevenue;

    @Column(name = "adjusted_revenue_numeric")
    private BigDecimal adjustedRevenueNumeric;

    @ManyToOne
    @JoinColumn(name = "intra_group_id")
    private IntraGroup intraGroup;

    public static void setValueForFieldHeader(String fieldHeader, IntraGroupRevenueValue intraGroupRevenueValue, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "GROSS ADJUSTED REVENUE" -> {
                intraGroupRevenueValue.setGrossAdjustedRevenue(value);
                intraGroupRevenueValue.setGrossAdjustedRevenueNumeric(numericValue);
            }
            case "INTRA GROUP REVENUE" -> {
                intraGroupRevenueValue.setIntraGroupRevenue(value);
                intraGroupRevenueValue.setIntraGroupRevenueNumeric(numericValue);
            }
            case "% OF INTRA GROUP REVENUE" -> {
                intraGroupRevenueValue.setPercentOfIntraGroupRevenue(value);
                intraGroupRevenueValue.setPercentOfIntraGroupRevenueNumeric(numericValue);
            }
            case "ADJUSTED REVENUE" -> {
                intraGroupRevenueValue.setAdjustedRevenue(value);
                intraGroupRevenueValue.setAdjustedRevenueNumeric(numericValue);
            }
            default -> {/* no field found */}
        }
    }
}

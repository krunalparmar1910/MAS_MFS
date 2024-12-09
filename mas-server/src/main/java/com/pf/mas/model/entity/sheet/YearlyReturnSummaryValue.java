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
@Table(name = "yearly_return_summary_values", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class YearlyReturnSummaryValue extends BaseSheetDateValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "amount_numeric")
    private BigDecimal amountNumeric;

    @Column(name = "percent_share_in_sales_during_period")
    private String percentShareInSalesDuringPeriod;

    @Column(name = "percent_share_in_sales_during_period_numeric")
    private BigDecimal percentShareInSalesDuringPeriodNumeric;

    @Column(name = "total_gst")
    private String totalGST;

    @Column(name = "total_gst_numeric")
    private BigDecimal totalGSTNumeric;

    @ManyToOne
    @JoinColumn(name = "yearly_return_summary_id")
    private YearlyReturnSummary yearlyReturnSummary;

    public static void setValueForFieldHeader(String fieldHeader, YearlyReturnSummaryValue yearlyReturnSummaryValue, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "AMOUNT" -> {
                yearlyReturnSummaryValue.setAmount(value);
                yearlyReturnSummaryValue.setAmountNumeric(numericValue);
            }
            case "% SHARE IN SALES DURING THE PERIOD" -> {
                yearlyReturnSummaryValue.setPercentShareInSalesDuringPeriod(value);
                yearlyReturnSummaryValue.setPercentShareInSalesDuringPeriodNumeric(numericValue);
            }
            case "TOTAL GST" -> {
                yearlyReturnSummaryValue.setTotalGST(value);
                yearlyReturnSummaryValue.setTotalGSTNumeric(numericValue);
            }
            default -> {/* no field found */}
        }
    }
}

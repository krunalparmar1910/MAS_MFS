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
@Table(name = "hsn_chapter_analysis", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class HSNChapterAnalysis extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "product")
    private String product;

    @Column(name = "description")
    private String description;

    @Column(name = "product_revenue")
    private String productRevenue;

    @Column(name = "product_revenue_numeric")
    private BigDecimal productRevenueNumeric;

    @Column(name = "percent_of_product_revenue")
    private String percentOfProductRevenue;

    @Column(name = "percent_of_product_revenue_numeric")
    private BigDecimal percentOfProductRevenueNumeric;

    @Column(name = "peak_months")
    private String peakMonths;

    @Column(name = "monthly_avg_product_revenue")
    private String monthlyAverageProductRevenue;

    @Column(name = "monthly_avg_product_revenue_numeric")
    private BigDecimal monthlyAverageProductRevenueNumeric;

    public static void setValueForFieldHeader(String fieldHeader, HSNChapterAnalysis hsnChapterAnalysis, String value, BigDecimal numericValue) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "RANKING" -> hsnChapterAnalysis.setRanking(SheetReaderUtils.parseIntegerValue(value));
            case "PRODUCT (HSN)" -> hsnChapterAnalysis.setProduct(value);
            case "CHAPTER" -> hsnChapterAnalysis.setChapter(value);
            case "DESCRIPTION" -> hsnChapterAnalysis.setDescription(value);
            case "PRODUCT REVENUE" -> {
                hsnChapterAnalysis.setProductRevenue(value);
                hsnChapterAnalysis.setProductRevenueNumeric(numericValue);
            }
            case "AS A % OF PRODUCT REVENUE" -> {
                hsnChapterAnalysis.setPercentOfProductRevenue(value);
                hsnChapterAnalysis.setPercentOfProductRevenueNumeric(numericValue);
            }
            case "PEAK MONTHS" -> hsnChapterAnalysis.setPeakMonths(value);
            case "MONTHLY AVERAGE PRODUCT REVENUE" -> {
                hsnChapterAnalysis.setMonthlyAverageProductRevenue(value);
                hsnChapterAnalysis.setMonthlyAverageProductRevenueNumeric(numericValue);
            }
            default -> {/* no field found */}
        }
    }
}

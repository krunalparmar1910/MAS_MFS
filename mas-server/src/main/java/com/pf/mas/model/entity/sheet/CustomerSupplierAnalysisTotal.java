package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
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
import java.util.Set;

@Entity
@Table(name = "customers_suppliers_analysis_total", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CustomerSupplierAnalysisTotal extends BaseSheetEntity {
    private static final Set<String> TOTAL_FIELDS = Set.of("ADJUSTED REVENUE", "ADJUSTED PURCHASES AND EXPENSES", "PRODUCT REVENUE");
    private static final Set<String> PERCENT_FIELDS = Set.of("% OF ADJUSTED REVENUE", "AS A % OF PRODUCT REVENUE", "% OF ADJUSTED PURCHASES AND EXPENSES");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "total_value")
    private String totalValue;

    @Column(name = "total_value_numeric")
    private BigDecimal totalValueNumeric;

    @Column(name = "percent_of_total_value")
    private String asPercentOfTotalValue;

    @Column(name = "percent_of_total_value_numeric")
    private BigDecimal asPercentOfTotalValueNumeric;

    public static void setValueForFieldHeader(String fieldHeader, CustomerSupplierAnalysisTotal total, String value, BigDecimal numericValue) {
        if (TOTAL_FIELDS.stream().anyMatch(field -> fieldHeader.trim().equalsIgnoreCase(field))) {
            total.setTotalValue(value);
            total.setTotalValueNumeric(numericValue);
        } else if (PERCENT_FIELDS.stream().anyMatch(field -> fieldHeader.trim().equalsIgnoreCase(field))) {
            total.setAsPercentOfTotalValue(value);
            total.setAsPercentOfTotalValueNumeric(numericValue);
        } else {
            total.setFieldName(value);
        }
    }
}

package com.pf.mas.model.entity;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseEntity;
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

@Entity
@Table(name = "gst_report_manual_banking_entry", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GSTReportManualBankingEntry implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "suppliers_analysis_numeric_entry_as_per_banking")
    private BigDecimal suppliersAnalysisNumericEntryAsPerBanking;

    @Column(name = "customers_analysis_numeric_entry_as_per_banking")
    private BigDecimal customersAnalysisNumericEntryAsPerBanking;

    @Column(name = "suppliers_analysis_numeric_entry_add_to_interfirm")
    private Boolean suppliersAnalysisNumericEntryAddToInterfirm;

    @Column(name = "customers_analysis_numeric_entry_add_to_interfirm")
    private Boolean customersAnalysisNumericEntryAddToInterfirm;

    @ManyToOne
    @JoinColumn(name = "gst_report_manual_entry_id")
    private GSTReportManualEntry gstReportManualEntry;
}

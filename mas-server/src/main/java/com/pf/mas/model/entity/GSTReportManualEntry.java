package com.pf.mas.model.entity;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "gst_report_manual_entry", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GSTReportManualEntry implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "circular_or_others_customers_analysis")
    private BigDecimal circularOrOthersCustomersAnalysis;

    @Column(name = "circular_or_others_suppliers_analysis")
    private BigDecimal circularOrOthersSuppliersAnalysis;

    @Column(name = "total_number_of_months_customers_analysis")
    private Integer totalNumberOfMonthsCustomersAnalysis;

    @Column(name = "total_number_of_months_suppliers_analysis")
    private Integer totalNumberOfMonthsSuppliersAnalysis;

    @OneToMany(mappedBy = "gstReportManualEntry", fetch = FetchType.EAGER)
    private List<GSTReportManualBankingEntry> gstReportManualBankingEntryList;

    // this is only needed for cascading delete of client order for deleting related field groups
    @ManyToOne
    @JoinColumn(name = "client_order_ref_id")
    private ClientOrder clientOrder;
}

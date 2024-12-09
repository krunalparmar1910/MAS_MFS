package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_information_ratios_activity_ratios", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRatios extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_information_ratios_id")
    private Ratios ratios;

    @Column(name = "days_of_inventory_outstanding")
    private String daysOfInventoryOutstanding;

    @Column(name = "receivables_turnover")
    private String receivablesTurnover;

    @Column(name = "cash_conversion_cycle")
    private String cashConversionCycle;

    @Column(name = "payables_turnover")
    private String payablesTurnover;

    @Column(name = "total_asset_turnover")
    private String totalAssetTurnover;

    @Column(name = "days_of_sales_outstanding")
    private String daysOfSalesOutstanding;

    @Column(name = "inventory_turnover")
    private String inventoryTurnover;

    @Column(name = "days_of_payables_outstanding")
    private String daysOfPayablesOutstanding;

    @Column(name = "working_capital_turnover")
    private String workingCapitalTurnover;

    @Column(name = "days_working_capital")
    private String daysWorkingCapital;
}

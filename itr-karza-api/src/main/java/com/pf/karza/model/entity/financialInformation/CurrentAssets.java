package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "financial_information_balance_sheet_dt_assets_current_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAssets extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "assets_id")
    private Assets assets;

    @JsonManagedReference
    @OneToOne(mappedBy = "currentAssets", cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentFinancialAssets currentFinancialAssets;

    @Column(name = "inventories")
    private String inventories;

    @Column(name = "total_current_assets")
    private String totalCurrentAssets;

    @Column(name = "current_tax_assets")
    private String currentTaxAssets;

    @Column(name = "other_current_assets")
    private String otherCurrentAssets;
}

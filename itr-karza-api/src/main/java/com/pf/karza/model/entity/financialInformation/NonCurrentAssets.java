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
@Table(name = "financial_information_balance_sheet_dt_assets_non_current_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonCurrentAssets extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "assets_id")
    private Assets assets;

    @Column(name = "property_plant_and_equipment")
    private String propertyPlantAndEquipment;

    @Column(name = "biological_assets_other_than_bearer_plants")
    private String biologicalAssetsOtherThanBearerPlants;

    @Column(name = "goodwill")
    private String goodwill;

    @Column(name = "intangible_assets_under_development")
    private String intangibleAssetsUnderDevelopment;

    @Column(name = "other_non_current_assets")
    private String otherNonCurrentAssets;

    @Column(name = "deferred_tax_assets_net")
    private String deferredTaxAssetsNet;

    @Column(name = "investments_accounted_for_using_equity_method")
    private String investmentsAccountedForUsingEquityMethod;

    @Column(name = "capital_work_in_progress")
    private String capitalWorkInProgress;

    @JsonManagedReference
    @OneToOne(mappedBy = "nonCurrentAssets", cascade = CascadeType.ALL, orphanRemoval = true)
    private NonCurrentFinancialAssets nonCurrentFinancialAssets;

    @Column(name = "total_non_current_assets")
    private String totalNonCurrentAssets;

    @Column(name = "investment_property")
    private String investmentProperty;

    @Column(name = "other_intangible_assets")
    private String otherIntangibleAssets;

}

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
@Table(name = "financial_information_balance_sheet_dt_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assets extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "balance_sheet_dt_id")
    private BalanceSheetDt balanceSheetDt;

    @Column(name = "regulatory_deferral_account_debit_balances_and_related_deferred_tax_assets")
    private String regulatoryDeferralAccountDebitBalancesAndRelatedDeferredTaxAssets;

    @Column(name = "non_current_assets_classified_as_held_for_sale")
    private String nonCurrentAssetsClassifiedAsHeldForSale;

    @JsonManagedReference
    @OneToOne(mappedBy = "assets", cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentAssets currentAssets;

    @Column(name = "total_assets")
    private String totalAssets;

    @JsonManagedReference
    @OneToOne(mappedBy = "assets", cascade = CascadeType.ALL, orphanRemoval = true)
    private NonCurrentAssets nonCurrentAssets;
}

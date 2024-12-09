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
@Table(name = "fi_bs_dt_equity_and_liabilities_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Liabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "equity_and_liabilities_id")
    private EquityAndLiabilities equityAndLiabilities;

    @JsonManagedReference
    @OneToOne(mappedBy = "liabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private NonCurrentLiabilities nonCurrentLiabilities;

    @Column(name = "regulatory_deferral_account_credit_balances_and_related_deferred_tax_liability")
    private String regulatoryDeferralAccountCreditBalancesAndRelatedDeferredTaxLiability;

    @JsonManagedReference
    @OneToOne(mappedBy = "liabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentLiabilities currentLiabilities;

    @Column(name = "total_liabilities")
    private String totalLiabilities;

    @Column(name = "liabilities_directly_associated_with_assets_in_disposal_group_classified_as_held_for_sale")
    private String liabilitiesDirectlyAssociatedWithAssetsInDisposalGroupClassifiedAsHeldForSale;

}

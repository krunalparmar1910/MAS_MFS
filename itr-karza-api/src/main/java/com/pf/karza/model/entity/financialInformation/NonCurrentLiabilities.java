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
@Table(name = "fi_bs_dt_equity_and_l_l_non_current_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonCurrentLiabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "liabilities_id")
    private Liabilities liabilities;

    @Column(name = "deferred_government_grants_non_current")
    private String deferredGovernmentGrantsNonCurrent;

    @Column(name = "other_non_current_liabilities")
    private String otherNonCurrentLiabilities;

    @Column(name = "deferred_tax_liabilities_net")
    private String deferredTaxLiabilitiesNet;

    @JsonManagedReference
    @OneToOne(mappedBy = "nonCurrentLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private NonCurrentFinancialLiabilities nonCurrentFinancialLiabilities;

    @Column(name = "total_non_current_liabilities")
    private String totalNonCurrentLiabilities;

    @Column(name = "provisions_non_current")
    private String provisionsNonCurrent;
}

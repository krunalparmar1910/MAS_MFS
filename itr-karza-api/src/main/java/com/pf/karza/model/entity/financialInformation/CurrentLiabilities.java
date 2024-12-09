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
@Table(name = "fi_bs_dt_equity_and_l_l_current_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLiabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "liabilities_id")
    private Liabilities liabilities;

    @Column(name = "total_current_liabilities")
    private String totalCurrentLiabilities;

    @Column(name = "other_current_liabilities")
    private String otherCurrentLiabilities;

    @Column(name = "deferred_government_grants_current")
    private String deferredGovernmentGrantsCurrent;

    @Column(name = "current_tax_liabilities")
    private String currentTaxLiabilities;

    @Column(name = "provisions_current")
    private String provisionsCurrent;

    @JsonManagedReference
    @OneToOne(mappedBy = "currentLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private CurrentFinancialLiabilities currentFinancialLiabilities;
}

package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "filling_data_financial_info_foreign_interests", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignInterests extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "financial_info_id", referencedColumnName = "id")
    @JsonBackReference
    private FinancialInfo financialInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForeignAccountsAndSecurities> foreignAccountsAndSecurities;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialInterestInAnyEntity> financialInterestInAnyEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImmovableOtherAssets> immvOthAssets;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SignatoryDetails> signatoryDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForeignTrusts> foreignTrusts;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OtherIncome> otherIncome;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForeignCustodialAccounts> foreignCustodialAccounts;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForeignEquityAndDebtInterest> foreignEquityAndDebtInterest;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForeignCashValueInsurance> foreignCashValueInsurance;

    @JsonManagedReference
    @OneToMany(mappedBy = "foreignInterests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnyOtherCapitalAsset> anyOtherCapitalAsset;

}


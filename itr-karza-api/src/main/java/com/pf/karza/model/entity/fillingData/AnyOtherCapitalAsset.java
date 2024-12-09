package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_financial_info_foreign_interests_any_other_capital_asset", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnyOtherCapitalAsset extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "nature_of_asset")
    private String natureOfAsset;

    @Column(name = "ownership")
    private String ownership;

    @Column(name = "date_of_acquisition")
    private String dateOfAcquisition;

    @Column(name = "total_investment")
    private String totalInvestment;

    @Column(name = "income_derived_from_the_asset")
    private String incomeDerivedFromTheAsset;

    @Column(name = "nature_of_income")
    private String natureOfIncome;

    @Column(name = "interest_taxable_and_offered_amount")
    private String interestTaxableAndOfferedAmount;

}

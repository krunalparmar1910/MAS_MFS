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
@Table(name = "filling_data_financial_info_foreign_interests_immovable_other_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImmovableOtherAssets extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "add_of_prop")
    private String addOfProp;

    @Column(name = "ownership")
    private String ownership;

    @Column(name = "date_of_acq")
    private String dateOfAcq;

    @Column(name = "tot_inv")
    private String totInv;

    @Column(name = "income_from_prop")
    private String incomeFromProp;

    @Column(name = "nat_of_income")
    private String natOfIncome;

    @Column(name = "tax_amt")
    private String taxAmt;

    @Column(name = "nature_of_asset")
    private String natureOfAsset;

}

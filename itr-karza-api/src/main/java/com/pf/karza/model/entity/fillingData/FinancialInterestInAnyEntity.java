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
@Table(name = "filling_data_financial_info_foreign_interests_financial_interest_in_any_entity", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInterestInAnyEntity extends BaseId {
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

    @Column(name = "nature_of_entity")
    private String natureOfEntity;

    @Column(name = "name_of_entity")
    private String nameOfEntity;

    @Column(name = "add_of_entity")
    private String addOfEntity;

    @Column(name = "nature_of_interest")
    private String natureOfInterest;

    @Column(name = "date_since_held")
    private String dateSinceHeld;

    @Column(name = "tot_inv")
    private String totInv;

    @Column(name = "inc_accrd")
    private String incAccrd;

    @Column(name = "nat_of_income")
    private String natOfIncome;

    @Column(name = "tax_inc_amt")
    private String taxIncAmt;

}

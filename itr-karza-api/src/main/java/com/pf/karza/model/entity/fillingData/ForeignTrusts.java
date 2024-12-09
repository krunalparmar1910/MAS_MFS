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
@Table(name = "filling_data_financial_info_foreign_interests_foreign_trusts", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignTrusts extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "name_of_institution")
    private String nameOfInstitution;

    @Column(name = "add_of_institution")
    private String addOfInstitution;

    @Column(name = "trustee_details")
    private String trusteeDetails;

    @Column(name = "add_trustee_details")
    private String addTrusteeDetails;

    @Column(name = "name_of_settlor")
    private String nameOfSettlor;

    @Column(name = "address_of_settlor")
    private String addressOfSettlor;

    @Column(name = "benf_details")
    private String benfDetails;

    @Column(name = "date_since_held")
    private String dateSinceHeld;

    @Column(name = "income_derived")
    private String incomeDerived;

    @Column(name = "tax_amt")
    private String taxAmt;

}

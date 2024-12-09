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
@Table(name = "filling_data_financial_info_foreign_interests_other_income", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherIncome extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_address")
    private String entityAddress;

    @Column(name = "inc_derv")
    private String incDerv;

    @Column(name = "nature_of_income")
    private String natureOfIncome;

    @Column(name = "tax_amt")
    private String taxAmt;
}

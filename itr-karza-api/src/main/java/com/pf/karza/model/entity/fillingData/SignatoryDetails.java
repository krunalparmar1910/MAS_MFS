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
@Table(name = "filling_data_financial_info_foreign_interests_signatory_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignatoryDetails extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "name_of_institution")
    private String nameOfInstitution;

    @Column(name = "add_inst")
    private String addInst;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "name_acc_holder")
    private String nameAccHolder;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "peak_bal_val")
    private String peakBalVal;

    @Column(name = "inc_accrd")
    private String incAccrd;

    @Column(name = "tax_amt")
    private String taxAmt;

}

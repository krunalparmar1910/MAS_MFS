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
@Table(name = "filling_data_financial_info_foreign_interests_foreign_account_and_securities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignAccountsAndSecurities extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "name_of_entity")
    private String nameOfEntity;

    @Column(name = "address_of_entity")
    private String addressOfEntity;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "status")
    private String status;

    @Column(name = "iban_code")
    private String ibanCode;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "date_acc_int")
    private String dateAccInt;

    @Column(name = "peak_bal_val")
    private String peakBalVal;

    @Column(name = "closing_balance")
    private String closingBalance;

    @Column(name = "gross_amt_earn")
    private String grossAmtEarn;

    @Column(name = "gross_prcd")
    private String grossPrcd;

    @Column(name = "nature_of_entity")
    private String natureOfEntity;

    @Column(name = "int_value")
    private String intValue;

}

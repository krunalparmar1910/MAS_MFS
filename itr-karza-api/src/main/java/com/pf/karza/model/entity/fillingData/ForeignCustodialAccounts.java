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
@Table(name = "filling_data_financial_info_foreign_interests_foreign_custodial_accounts", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignCustodialAccounts extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "name_of_the_financial_institution")
    private String nameOfTheFinancialInstitution;

    @Column(name = "address_of_the_financial_institution")
    private String addressOfTheFinancialInstitution;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "account_opening_date")
    private String accountOpeningDate;

    @Column(name = "peak_balance_during_the_period")
    private String peakBalanceDuringThePeriod;

    @Column(name = "closing_balance")
    private String closingBalance;

    @Column(name = "gross_amount_paid_credited_nature")
    private String grossAmountPaidCreditedNature;

    @Column(name = "gross_amount_paid_credited_amount")
    private String grossAmountPaidCreditedAmount;

}

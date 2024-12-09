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
@Table(name = "filling_data_financial_info_foreign_interests_foreign_equity_and_debt_interest", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignEquityAndDebtInterest extends BaseId {
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

    @Column(name = "nature_of_entity")
    private String natureOfEntity;

    @Column(name = "date_of_acquiring_interest")
    private String dateOfAcquiringInterest;

    @Column(name = "initial_value_of_the_investment")
    private String initialValueOfTheInvestment;

    @Column(name = "peak_value_of_investment_during_the_period")
    private String peakValueOfInvestmentDuringThePeriod;

    @Column(name = "closing_value")
    private String closingValue;

    @Column(name = "total_gross_amount_paid_credited")
    private String totalGrossAmountPaidCredited;

    @Column(name = "total_gross_proceeds_from_sale_or_redemption")
    private String totalGrossProceedsFromSaleOrRedemption;
}

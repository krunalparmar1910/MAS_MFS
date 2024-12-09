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
@Table(name = "filling_data_financial_info_foreign_interests_foreign_cash_value_insurance", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignCashValueInsurance extends BaseId {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "financial_info_foreign_interests_id", referencedColumnName = "id")
    private ForeignInterests foreignInterests;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "name_of_financial_institution")
    private String nameOfFinancialInstitution;

    @Column(name = "address_of_financial_institution")
    private String addressOfFinancialInstitution;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "date_of_contract")
    private String dateOfContract;

    @Column(name = "cash_value_or_surrender_value")
    private String cashValueOrSurrenderValue;

    @Column(name = "total_gross_amt_paid_or_credited")
    private String totalGrossAmtPaidOrCredited;

}

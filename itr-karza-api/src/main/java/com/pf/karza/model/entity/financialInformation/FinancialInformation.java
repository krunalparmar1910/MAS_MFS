package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "financial_information", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInformation extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private BalanceSheet balanceSheet;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinancialInformationIncomeConcentration incomeConcentration;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinancialInformationTaxDetails taxDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private TurnoverDetails turnoverDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ratios ratios;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfitAndLossDt profitAndLossDt;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfitAndLoss profitAndLoss;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private BalanceSheetDt balanceSheetDt;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private QuantitativeDetails quantitativeDetails;

    private String assessmentYear;
    private String financialYear;
}

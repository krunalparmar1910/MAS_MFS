package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_financial_info_exempt_income", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExemptIncome extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "financial_info_id", referencedColumnName = "id")
    @JsonBackReference
    private FinancialInfo financialInfo;

    @Column(name = "interest_income")
    private String interestIncome;

    @Column(name = "dividend_income")
    private String dividendIncome;

    @Column(name = "agricultural_income")
    private String agriculturalIncome;

    @Column(name = "other_income")
    private String otherIncome;

    @Column(name = "income_not_chg_dtaa")
    private String incomeNotChgDTAA;

    @Column(name = "pass_through_income")
    private String passThroughIncome;

}

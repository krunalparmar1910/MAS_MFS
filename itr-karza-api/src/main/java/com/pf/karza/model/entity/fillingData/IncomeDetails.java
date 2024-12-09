package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "filling_data_financial_info_income_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDetails extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "financial_info_id", referencedColumnName = "id")
    @JsonBackReference
    private FinancialInfo financialInfo;

    @OneToOne(mappedBy = "incomeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private HeadwiseIncome headwiseIncome;

    @OneToOne(mappedBy = "incomeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Trend trend;

    @OneToOne(mappedBy = "incomeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private IncomeConcentration incomeConcentration;
}

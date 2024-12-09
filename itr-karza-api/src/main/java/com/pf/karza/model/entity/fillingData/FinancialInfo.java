package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "filling_data_financial_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfo extends BaseId {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "filling_data_id")
    @JsonBackReference
    private FillingData fillingData;

    @OneToOne(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AssetLiabilityDetails assetLiabilityDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private TaxDetails taxDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private IncomeDetails incomeDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExemptIncome exemptIncome;

    @JsonManagedReference
    @OneToOne(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ForeignInterests foreignInterests;

    @JsonManagedReference
    @OneToMany(mappedBy = "financialInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialInfoTdsDetails> tdsDetails;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "financial_year")
    private String financialYear;
}

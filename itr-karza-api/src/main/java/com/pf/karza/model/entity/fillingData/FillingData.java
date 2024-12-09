package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "filling_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillingData extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @OneToOne(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private FillingDataFormDetails formDetails;

    @OneToMany(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DirectorDetails> directorDetails;

    @OneToMany(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShareHoldingPattern> shareHoldingPattern;

    @JsonManagedReference
    @OneToOne(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinancialInfo financialInfo;

    @JsonManagedReference
    @OneToOne(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    private FillingDataBankDetails bankDetails;

    @JsonManagedReference
    @OneToOne(mappedBy = "fillingData", cascade = CascadeType.ALL, orphanRemoval = true)
    private ComplianceData complianceData;

}

package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
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
@Table(name = "filling_data_form_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillingDataFormDetails extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "filling_data_id")
    @JsonBackReference
    private FillingData fillingData;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "form_version")
    private String formVersion;

    @Column(name = "description")
    private String description;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "schema_version")
    private String schemaVersion;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "filing_date")
    private String filingDate;
}

package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_tax_details_filing_status_form_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsFilingStatusFormDetails extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_filing_status_info_id")
    @JsonBackReference
    private ItrDataTaxDetailsFilingStatusInfo itrDataTaxDetailsFilingStatusInfo;

    private String description;

    private String assessmentYear;

    private String financialYear;

    private String formName;

    private String schemaVersion;

    private String formVersion;
}

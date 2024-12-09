package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.ItrDataAssmtYear;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "itr_data_tax_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetails extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_assmt_yr_id")
    @JsonBackReference
    private ItrDataAssmtYear itrDataAssmtYear;

    @JsonProperty("setOffLoss")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSetOffLoss itrDataTaxDetailsSetOffLoss;

    @JsonProperty("cmptn")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptn itrDataTaxDetailsCmptn;

    @JsonProperty("taxLiab")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTaxLiab itrDataTaxDetailsTaxLiab;

    @JsonProperty("filingStatusInfo")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsFilingStatusInfo itrDataTaxDetailsFilingStatusInfo;
}

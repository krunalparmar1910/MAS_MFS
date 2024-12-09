package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
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
@Table(name = "itr_data_tax_details_set_off_loss_sch_cyla", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsSetOffLossSchCyla extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLoss itrDataTaxDetailsSetOffLoss;

    @JsonProperty("ttlLossToBeSetOff")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCyla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsTtlLossToBeSetOff itrDataTaxDetailsTtlLossToBeSetOff;

    @JsonProperty("cyLossSetOff")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCyla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOff itrDataTaxDetailsCyLossSetOff;
}

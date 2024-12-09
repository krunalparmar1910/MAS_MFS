package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossAdjTotBFLossInBFLA;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossBalAvlTotalOfEarlierYr;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFCurrAssmntYr;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev2ndYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev3rdYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev4thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev5thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev6thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev7thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev8thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrev9thYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCFFromPrevYrFrmAY;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCurrAYLoss;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossCurrYrDistrUnitHldr;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossLossDstrbtdUnitHldr;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossTtlLossCFSummary;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl.ItrDataTaxDetailsLossTtlOfBFLossesEarlierYrs;
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
@Table(name = "itr_data_tax_details_set_off_loss_sch_cfl", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsSetOffLossSchCfl extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLoss itrDataTaxDetailsSetOffLoss;

    @JsonProperty("lossCFFromPrev9thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev9thYrFrmAY itrDataTaxDetailsLossCFFromPrev9thYrFrmAY;

    @JsonProperty("lossCFFromPrev8thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev8thYrFrmAY itrDataTaxDetailsLossCFFromPrev8thYrFrmAY;

    @JsonProperty("lossCFFromPrev7thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev7thYrFrmAY itrDataTaxDetailsLossCFFromPrev7thYrFrmAY;

    @JsonProperty("lossCFFromPrev6thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev6thYrFrmAY itrDataTaxDetailsLossCFFromPrev6thYrFrmAY;

    @JsonProperty("lossCFFromPrev5thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev5thYrFrmAY itrDataTaxDetailsLossCFFromPrev5thYrFrmAY;

    @JsonProperty("lossCFFromPrev4thYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev4thYrFrmAY itrDataTaxDetailsLossCFFromPrev4thYrFrmAY;

    @JsonProperty("lossCFFromPrev3rdYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev3rdYrFrmAY itrDataTaxDetailsLossCFFromPrev3rdYrFrmAY;

    @JsonProperty("lossCFFromPrev2ndYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrev2ndYrFrmAY itrDataTaxDetailsLossCFFromPrev2ndYrFrmAY;

    @JsonProperty("lossCFFromPrevYrFrmAY")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFFromPrevYrFrmAY itrDataTaxDetailsLossCFFromPrevYrFrmAY;

    @JsonProperty("lossCFCurrAssmntYr")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCFCurrAssmntYr itrDataTaxDetailsLossCFCurrAssmntYr;

    @JsonProperty("ttlOfBFLossesEarlierYrs")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossTtlOfBFLossesEarlierYrs itrDataTaxDetailsLossTtlOfBFLossesEarlierYrs;

    @JsonProperty("lossDstrbtdUnitHldr")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossLossDstrbtdUnitHldr itrDataTaxDetailsLossLossDstrbtdUnitHldr;

    @JsonProperty("balAvlTotalOfEarlierYr")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossBalAvlTotalOfEarlierYr itrDataTaxDetailsLossBalAvlTotalOfEarlierYr;

    @JsonProperty("adjTotBFLossInBFLA")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossAdjTotBFLossInBFLA itrDataTaxDetailsLossAdjTotBFLossInBFLA;

    @JsonProperty("currAYloss")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCurrAYLoss itrDataTaxDetailsLossCurrAYLoss;

    @JsonProperty("ttlLossCFSummary")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossTtlLossCFSummary itrDataTaxDetailsLossTtlLossCFSummary;

    @JsonProperty("currYrDistrUnitHldr")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchCfl", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsLossCurrYrDistrUnitHldr itrDataTaxDetailsLossCurrYrDistrUnitHldr;
}

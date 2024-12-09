package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffHp;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffIfosAsPerDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffIncFrmPgbp;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffLossRmngAftSetOff;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffLtcgAsPerDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffLtcgAt10;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffLtcgAt20;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffNetIFOSNrmlRt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffPrftGainLifeInsBus;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffPrftOwngMntngHorses;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffSal;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffSpcfdBusInc;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffSpecltnInc;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffStcgApplcblRate;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffStcgAsPerDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffStcgTaxAt15;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffStcgTaxAt30;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla.ItrDataTaxDetailsCyLossSetOffTtlLossSetOff;
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
@Table(name = "itr_data_tax_details_cy_loss_set_off", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsCyLossSetOff extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_sch_cyla_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLossSchCyla itrDataTaxDetailsSetOffLossSchCyla;

    @JsonProperty("sal")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffSal itrDataTaxDetailsCyLossSetOffSal;

    @JsonProperty("hp")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffHp itrDataTaxDetailsCyLossSetOffHp;

    @JsonProperty("incFrmPGBP")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffIncFrmPgbp itrDataTaxDetailsCyLossSetOffIncFrmPgbp;

    @JsonProperty("prftGainLifeInsBus")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffPrftGainLifeInsBus itrDataTaxDetailsCyLossSetOffPrftGainLifeInsBus;

    @JsonProperty("specltnInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffSpecltnInc itrDataTaxDetailsCyLossSetOffSpecltnInc;

    @JsonProperty("spcfdBusInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffSpcfdBusInc itrDataTaxDetailsCyLossSetOffSpcfdBusInc;

    @JsonProperty("stcgTaxAt15")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffStcgTaxAt15 itrDataTaxDetailsCyLossSetOffStcgTaxAt15;

    @JsonProperty("stcgTaxAt30")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffStcgTaxAt30 itrDataTaxDetailsCyLossSetOffStcgTaxAt30;

    @JsonProperty("stcgApplcblRate")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffStcgApplcblRate itrDataTaxDetailsCyLossSetOffStcgApplcblRate;

    @JsonProperty("stcgAsPerDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffStcgAsPerDTAARt itrDataTaxDetailsCyLossSetOffStcgAsPerDTAARt;

    @JsonProperty("ltcgAt10")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffLtcgAt10 itrDataTaxDetailsCyLossSetOffLtcgAt10;

    @JsonProperty("ltcgAt20")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffLtcgAt20 itrDataTaxDetailsCyLossSetOffLtcgAt20;

    @JsonProperty("ltcgAsPerDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffLtcgAsPerDTAARt itrDataTaxDetailsCyLossSetOffLtcgAsPerDTAARt;

    @JsonProperty("netIFOSNrmlRt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffNetIFOSNrmlRt itrDataTaxDetailsCyLossSetOffNetIFOSNrmlRt;

    @JsonProperty("prftOwngMntngHorses")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffPrftOwngMntngHorses itrDataTaxDetailsCyLossSetOffPrftOwngMntngHorses;

    @JsonProperty("ifosAsPerDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffIfosAsPerDTAARt itrDataTaxDetailsCyLossSetOffIfosAsPerDTAARt;

    @JsonProperty("ttlLossSetOff")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffTtlLossSetOff itrDataTaxDetailsCyLossSetOffTtlLossSetOff;

    @JsonProperty("lossRmngAftSetOff")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCyLossSetOff", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCyLossSetOffLossRmngAftSetOff itrDataTaxDetailsCyLossSetOffLossRmngAftSetOff;
}

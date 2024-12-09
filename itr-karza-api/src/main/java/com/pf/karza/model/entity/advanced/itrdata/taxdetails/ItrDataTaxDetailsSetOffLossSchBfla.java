package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaBus;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaHp;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaIfosAsPerDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaLtcgAsPerDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaLtcgAt10;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaLtcgAt20;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaNetIFOSNormlRt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaPrftGainLifeInsBus;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaPrftOwngMntngHorses;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaSal;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaSpcfdBusInc;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaSpcltnInc;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaStcgAtApplcblRt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaStcgDTAARt;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaStcgTaxAt15;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaStcgTaxAt30;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla.ItrDataTaxDetailsSchBflaTtlLossSetOff;
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

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_tax_details_set_off_loss_sch_bfla", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsSetOffLossSchBfla extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLoss itrDataTaxDetailsSetOffLoss;

    @JsonProperty("sal")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaSal itrDataTaxDetailsSchBflaSal;

    @JsonProperty("hp")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaHp itrDataTaxDetailsSchBflaHp;

    @JsonProperty("bus")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaBus itrDataTaxDetailsSchBflaBus;

    @JsonProperty("prftGainLifeInsBus")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaPrftGainLifeInsBus itrDataTaxDetailsSchBflaPrftGainLifeInsBus;

    @JsonProperty("spcltnInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaSpcltnInc itrDataTaxDetailsSchBflaSpcltnInc;

    @JsonProperty("spcfdBusInc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaSpcfdBusInc itrDataTaxDetailsSchBflaSpcfdBusInc;

    @JsonProperty("stcgTaxAt15")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaStcgTaxAt15 itrDataTaxDetailsSchBflaStcgTaxAt15;

    @JsonProperty("stcgTaxAt30")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaStcgTaxAt30 itrDataTaxDetailsSchBflaStcgTaxAt30;

    @JsonProperty("stcgAtApplcblRt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaStcgAtApplcblRt itrDataTaxDetailsSchBflaStcgAtApplcblRt;

    @JsonProperty("stcgDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaStcgDTAARt itrDataTaxDetailsSchBflaStcgDTAARt;

    @JsonProperty("ltcgAt10")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaLtcgAt10 itrDataTaxDetailsSchBflaLtcgAt10;

    @JsonProperty("ltcgAt20")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaLtcgAt20 itrDataTaxDetailsSchBflaLtcgAt20;

    @JsonProperty("ltcgAsPerDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaLtcgAsPerDTAARt itrDataTaxDetailsSchBflaLtcgAsPerDTAARt;

    @JsonProperty("netIFOSNormlRt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaNetIFOSNormlRt itrDataTaxDetailsSchBflaNetIFOSNormlRt;

    @JsonProperty("prftOwngMntngHorses")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaPrftOwngMntngHorses itrDataTaxDetailsSchBflaPrftOwngMntngHorses;

    @JsonProperty("ifosAsPerDTAARt")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaIfosAsPerDTAARt itrDataTaxDetailsSchBflaIfosAsPerDTAARt;

    @JsonProperty("ttlLossSetOff")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsSetOffLossSchBfla", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsSchBflaTtlLossSetOff itrDataTaxDetailsSchBflaTtlLossSetOff;

    private BigDecimal incAftSetOffCYLoss;
}

package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.ItrDataTaxDetailsCyLossSetOff;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class ItrDataTaxDetailsCyLossSetOffRecord extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_cy_loss_set_off_id")
    @JsonBackReference
    private ItrDataTaxDetailsCyLossSetOff itrDataTaxDetailsCyLossSetOff;

    private BigDecimal cyInc;

    private BigDecimal hpLossCY;

    private BigDecimal busLossCY;

    private BigDecimal othSourLossCY;

    private BigDecimal cyIncAftSetOff;
}

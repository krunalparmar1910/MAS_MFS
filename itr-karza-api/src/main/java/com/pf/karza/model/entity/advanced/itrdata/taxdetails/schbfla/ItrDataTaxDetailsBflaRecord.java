package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.ItrDataTaxDetailsSetOffLossSchBfla;
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
public abstract class ItrDataTaxDetailsBflaRecord extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_sch_bfla_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLossSchBfla itrDataTaxDetailsSetOffLossSchBfla;

    private BigDecimal incAftSetOffCYLoss;

    private BigDecimal brtFwdLossStOff;

    private BigDecimal brtFwdDepStOff;

    private BigDecimal brtFwdAllwnceStOff;

    private BigDecimal cyIncAftStOff;
}

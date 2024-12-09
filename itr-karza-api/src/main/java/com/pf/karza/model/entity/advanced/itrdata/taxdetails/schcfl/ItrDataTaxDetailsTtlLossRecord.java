package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.ItrDataTaxDetailsSetOffLossSchCfl;
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
public abstract class ItrDataTaxDetailsTtlLossRecord extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_sch_cfl_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLossSchCfl itrDataTaxDetailsSetOffLossSchCfl;

    private BigDecimal hpTtl;

    private BigDecimal othThnSpcltvSpcfdInsBus;

    private BigDecimal spcltvBus;

    private BigDecimal spcfdBus;

    private BigDecimal insBus;

    private BigDecimal stcl;

    private BigDecimal ltcl;

    private BigDecimal lossFrmMntngRcHrses;
}

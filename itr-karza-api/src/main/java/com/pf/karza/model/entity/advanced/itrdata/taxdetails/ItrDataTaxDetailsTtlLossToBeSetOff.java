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

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_tax_details_set_off_loss_to_be_set_off", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsTtlLossToBeSetOff extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_sch_cyla_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLossSchCyla itrDataTaxDetailsSetOffLossSchCyla;

    private BigDecimal ttlHpLoss;

    private BigDecimal ttlBusLoss;

    private BigDecimal ttlOthSrc;
}

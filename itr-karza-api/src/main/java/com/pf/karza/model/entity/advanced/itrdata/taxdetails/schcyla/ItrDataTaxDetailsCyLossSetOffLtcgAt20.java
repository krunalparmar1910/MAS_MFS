package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcyla;

import com.pf.karza.constant.DbConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_tax_details_cy_loss_set_off_ltcg_20", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsCyLossSetOffLtcgAt20 extends ItrDataTaxDetailsCyLossSetOffRecord {
}

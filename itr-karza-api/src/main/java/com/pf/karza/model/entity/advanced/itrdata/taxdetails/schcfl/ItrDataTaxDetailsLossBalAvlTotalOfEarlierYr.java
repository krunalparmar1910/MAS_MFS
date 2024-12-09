package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl;

import com.pf.karza.constant.DbConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_tax_details_loss_bal_avl_total_earlier_yr", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsLossBalAvlTotalOfEarlierYr extends ItrDataTaxDetailsTtlLossRecord {
}

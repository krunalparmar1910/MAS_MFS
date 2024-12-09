package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schbfla;

import com.pf.karza.constant.DbConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_tax_details_sch_bfla_spcfd_bus_inc", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsSchBflaSpcfdBusInc extends ItrDataTaxDetailsBflaRecord {
}

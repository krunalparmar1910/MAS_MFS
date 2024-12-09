package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_facility_overdue_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityOverdueDetails extends BaseID {
	private Long dpd1to30amt;
	private Long dpd31to60amt;
	private Long dpd61t090amt;
	private Long dpd91to180amt;
	@Column(name = "dpd_above_180_amt")
	private Long dpdAbove180amt;
}
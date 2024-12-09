package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_facility_current_misc_detail", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityCurrentMiscDetail extends BaseID {
	private String repaymentFrequency;
	private Long tenure;
	private String assetBasedSecurityCoverage;
	private String weightedAverageMaturityPeriodOfContracts;
	private String restructingReason;
	private String guaranteeCoverage;

}
package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cps_institution_wise_total", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CpsInstitutionWiseTotal extends BaseID {
	private String message;
	private Long totalLenders;
	private Long totalCf;
	private Long openCf;
	private Long totalOutstanding;
	private String latestCfOpenedDate;
	private Long delinquentCf;
	private Long delinquentOutstanding;
}
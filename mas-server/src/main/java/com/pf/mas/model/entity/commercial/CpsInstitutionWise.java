package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cps_institution_wise", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CpsInstitutionWise extends BaseID {
	private String message;
	private Long totalLenders;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "total_cf_id")
	private TotalCf totalCf;
	private Long openCf;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "total_outstanding_id")
	private TotalOutstanding totalOutstanding;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "delinquent_cf_id")
	private DelinquentCf delinquentCf;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "delinquent_outstanding_id")
	private DelinquentOutstanding delinquentOutstanding;
	private String latestCfOpenedDate;
}
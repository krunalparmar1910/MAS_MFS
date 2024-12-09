package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "non_std_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NonStdVec extends BaseID {
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "dpd_91_180_id")
	private DpdDetails dpd91to180;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "greater_than_180_id")
	private DpdDetails greaterThan180DPD;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_id")
	private DpdDetails sub;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "dbt_id")
	private DpdDetails dbt;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "loss_id")
	private DpdDetails loss;
}
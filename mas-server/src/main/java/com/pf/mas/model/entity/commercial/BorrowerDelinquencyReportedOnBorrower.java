package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "borrower_delinquency_reported_on_borrower", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerDelinquencyReportedOnBorrower extends BaseID {
	private String message;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "bdr_your_institution_id")
	private BdrYourInstitution bdrYourInstitution;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "bdr_outside_institution_id")
	private BdrOutsideInstitution bdrOutsideInstitution;
}
package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credit_facility_details_as_borrower_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityDetailsAsBorrowerSec extends BaseID {
	private String message;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "credit_facility_details_as_borrower_sec_id")
	private List<CreditFacilityCurrentDetails> creditFacilityCurrentDetailsList;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "credit_facility_details_as_borrower_sec_id")
	private List<CfHistoryForAcOrDpd> cfHistoryForAcOrDpdList;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_facility_overdue_details_vec_id")
	private CreditFacilityOverdueDetailsVec creditFacilityOverdueDetailsVec;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "cheque_dishonoured_due_to_insufficient_funds_id")
	private ChequeDishounouredDuetoInsufficientFunds chequeDishounouredDuetoInsufficientFunds;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_facility_security_details_vec_id")
	private CreditFacilitySecurityDetailsVec creditFacilitySecurityDetailsVec;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_facility_guarantor_details_vec_id")
	private CreditFacilityGuarantorDetailsVec creditFacilityGuarantorDetailsVec;
}
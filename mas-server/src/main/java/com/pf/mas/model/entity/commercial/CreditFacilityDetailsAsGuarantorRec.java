package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_facility_details_as_guarantor_rec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityDetailsAsGuarantorRec extends BaseID {
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_facility_current_details_id")
	private CreditFacilityCurrentDetails creditFacilityCurrentDetails;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "borrower_profile_guarantor_sec_id")
	private BorrowerProfileGuarantorSec borrowerProfileGuarantorSec;
}
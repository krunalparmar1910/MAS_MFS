package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applicant_registered_address_comm", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicantRegisteredAddressCommercial extends BaseID {

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_telephone_commercial_id")
	private ApplicantTelephoneCommercial applicantTelephoneCommercial;
	private String addressPinCode;
	private String addressStateCode;
	private String addressCity;
	private String addressLine3;
	private String addressLine2;
	private String addressLine1;
	private String addressType;
}
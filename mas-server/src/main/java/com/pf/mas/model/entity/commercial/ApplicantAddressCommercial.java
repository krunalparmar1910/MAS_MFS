package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applicant_address_comm", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicantAddressCommercial extends BaseID {
	private String residenceType;

	private String stateCode;

	private String city;

	private String pinCode;

	private String addressLine5;

	private String addressLine4;

	private String addressLine3;

	private String addressLine2;

	private String addressLine1;

	private String addressType;
}
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
@Table(name = "borrower_id_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerIdDetails extends BaseID {
	private String cin;
	private String pan;
	private String tin;
	private String din;
	@Column(name = "voter_id")
	private String voterID;
	private String passportNumber;
	private String drivingLicenseNo;
	private String uid;
	private String rationCard;
	private String registrationNumber;
	private String serviceTaxNumber;
}
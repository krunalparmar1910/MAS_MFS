package com.pf.mas.model.entity.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "applicant_commercial", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicantCommercial extends BaseID {
	private String facilityType;

	private String facilityCategory;

	private String memberReferenceNumber;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "applicant_commercial_id")
	private List<ApplicantTelephoneCommercial> telephones;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_registered_address_comm_id")
	private ApplicantRegisteredAddressCommercial applicantRegisteredAddressCommercial;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "applicant_commercial_id")
	private List<ApplicantRegisteredAddressCommercial> otherAddresses;

	private String dateOfRegistration;

	private String typeOfEntity;

	private String classOfActivity;

	private String companyName;

	private String crn;

	private String tin;

	private String pan;

	private String cin;

	private String applicantType;

	private String nodeIdentifier;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ds_commercial_cir_id")
	private DsCommercialCir dsCommercialCIR;

	private String dsLitigationReport;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "applicant_commercial_id")
	private List<ApplicantAddressCommercial> addresses;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "applicant_commercial_id")
	private List<IdentifierCommercial> identifiers;

	private String gender;

	private String dateOfBirth;

	private String applicantLastName;

	private String applicantMiddleName;

	private String applicantFirstName;

	private String din;
	private String individualPanCheck;
	private String consumerBureauCheck;
	private String relationType;
}

package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Applicant")
public class ApplicantCommercialDTO {
	@JacksonXmlProperty(localName = "FacilityType")
	private String facilityType;

	@JacksonXmlProperty(localName = "FacilityCategory")
	private String facilityCategory;

	@JacksonXmlProperty(localName = "MemberReferenceNumber")
	private String memberReferenceNumber;

	@JacksonXmlProperty(localName = "Telephones")
	private ApplicantTelephonesCommercialDTO applicantTelephonesCommercialDTO;

	@JacksonXmlProperty(localName = "RegisteredAddress")
	private ApplicantRegisteredAddressDTO registeredAddress;

	@JacksonXmlProperty(localName = "OtherAddresses")
	private ApplicantOtherAddressesCommercialDTO otherAddressesVec;

	@JacksonXmlProperty(localName = "DateOfRegistration")
	private String dateOfRegistration;

	@JacksonXmlProperty(localName = "TypeOfEntity")
	private String typeOfEntity;

	@JacksonXmlProperty(localName = "ClassOfActivity")
	private String classOfActivity;

	@JacksonXmlProperty(localName = "CompanyName")
	private String companyName;

	@JacksonXmlProperty(localName = "CRN")
	private String crn;

	@JacksonXmlProperty(localName = "TIN")
	private String tin;

	@JacksonXmlProperty(localName = "PAN")
	private String pan;

	@JacksonXmlProperty(localName = "CIN")
	private String cin;

	@JacksonXmlProperty(localName = "ApplicantType")
	private String applicantType;

	@JacksonXmlProperty(localName = "NodeIdentifier")
	private int nodeIdentifier;

	@JacksonXmlProperty(localName = "DsCommercialCIR")
	private DsCommercialCIRDTO dsCommercialCIR;

	// #TODO not sure about this
	@JacksonXmlProperty(localName = "DsLitigationReport")
	private String dsLitigationReport;

	@JacksonXmlProperty(localName = "Addresses")
	private ApplicantAddressesCommercialDTO addresses;

	@JacksonXmlProperty(localName = "Identifiers")
	private IdentifiersDTO identifiers;

	@JacksonXmlProperty(localName = "Gender")
	private String gender;

	@JacksonXmlProperty(localName = "DateOfBirth")
	private String dateOfBirth;

	@JacksonXmlProperty(localName = "ApplicantLastName")
	private String applicantLastName;

	@JacksonXmlProperty(localName = "ApplicantMiddleName")
	private String applicantMiddleName;

    @JacksonXmlProperty(localName = "ApplicantFirstName")
	private String applicantFirstName;

	@JacksonXmlProperty(localName = "DIN")
	private String din;
	@JacksonXmlProperty(localName = "IndividualPanCheck")
	private String individualPanCheck;
	@JacksonXmlProperty(localName = "ConsumerBureauCheck")
	private String consumerBureauCheck;
	@JacksonXmlProperty(localName = "RelationType")
	private String relationType;
}

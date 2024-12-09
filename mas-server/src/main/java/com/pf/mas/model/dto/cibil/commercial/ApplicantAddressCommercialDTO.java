package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Address")
public class ApplicantAddressCommercialDTO {
	@JacksonXmlProperty(localName = "ResidenceType")
	private String residenceType;

	@JacksonXmlProperty(localName = "StateCode")
	private String stateCode;

	@JacksonXmlProperty(localName = "City")
	private String city;

	@JacksonXmlProperty(localName = "PinCode")
	private String pinCode;

	@JacksonXmlProperty(localName = "AddressLine5")
	private String addressLine5;

	@JacksonXmlProperty(localName = "AddressLine4")
	private String addressLine4;

	@JacksonXmlProperty(localName = "AddressLine3")
	private String addressLine3;

	@JacksonXmlProperty(localName = "AddressLine2")
	private String addressLine2;

	@JacksonXmlProperty(localName = "AddressLine1")
	private String addressLine1;

	@JacksonXmlProperty(localName = "AddressType")
	private String addressType;
}
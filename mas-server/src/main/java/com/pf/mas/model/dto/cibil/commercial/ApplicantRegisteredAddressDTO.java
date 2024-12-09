package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "RegisteredAddress")
public class ApplicantRegisteredAddressDTO {

	@JacksonXmlProperty(localName = "Telephone")
	private ApplicantTelephoneCommercialDTO applicantTelephoneCommercialDTO;

	@JacksonXmlProperty(localName = "AddressPincode")
	private String addressPinCode;

	@JacksonXmlProperty(localName = "AddressStateCode")
	private String addressStateCode;

	@JacksonXmlProperty(localName = "AddressCity")
	private String addressCity;

	@JacksonXmlProperty(localName = "AddressLine3")
	private String addressLine3;

	@JacksonXmlProperty(localName = "AddressLine2")
	private String addressLine2;

	@JacksonXmlProperty(localName = "AddressLine1")
	private String addressLine1;

	@JacksonXmlProperty(localName = "AddressType")
	private String addressType;
}
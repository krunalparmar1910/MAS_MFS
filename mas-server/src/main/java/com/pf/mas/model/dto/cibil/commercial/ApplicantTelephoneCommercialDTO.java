package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Telephone")
public class ApplicantTelephoneCommercialDTO {
	@JacksonXmlProperty(localName = "TelephoneType")
	private String telephoneType;

	@JacksonXmlProperty(localName = "TelephoneNumber")
	private String telephoneNumber;

	@JacksonXmlProperty(localName = "TelephoneExtension")
	private String telephoneExtension;

	@JacksonXmlProperty(localName = "Contact_Prefix")
	private String contactPrefix;

	@JacksonXmlProperty(localName = "Contact_Area")
	private String contactArea;

}
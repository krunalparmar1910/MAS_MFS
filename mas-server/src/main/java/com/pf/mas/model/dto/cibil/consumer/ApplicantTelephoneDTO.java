package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Telephone")
public class ApplicantTelephoneDTO {
	@JacksonXmlProperty(localName = "TelephoneType")
	private String telephoneType;

	@JacksonXmlProperty(localName = "TelephoneNumber")
	private String telephoneNumber;

	@JacksonXmlProperty(localName = "TelephoneExtension")
	private String telephoneExtension;
}
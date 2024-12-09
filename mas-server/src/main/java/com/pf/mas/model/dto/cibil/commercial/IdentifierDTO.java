package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Identifier")
public class IdentifierDTO {
	@JacksonXmlProperty(localName = "IdType")
	private Long idType;

	@JacksonXmlProperty(localName = "IdNumber")
	private String idNumber;
}
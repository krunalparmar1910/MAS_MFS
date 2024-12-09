package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Identifiers")
public class IdentifiersDTO {
	@JacksonXmlProperty(localName = "Identifier")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<IdentifierDTO> identifierDTOList;
}
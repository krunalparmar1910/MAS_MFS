package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "yourInstitution")
public class BdrYourInstitutionDTO {
	private String current;
	private String last24Months;
}
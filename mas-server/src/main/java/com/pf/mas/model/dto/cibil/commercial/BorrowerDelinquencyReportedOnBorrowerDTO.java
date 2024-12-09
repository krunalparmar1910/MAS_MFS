package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrowerDelinquencyReportedOnBorrower")
public class BorrowerDelinquencyReportedOnBorrowerDTO {
	@JacksonXmlProperty(localName = "yourInstitution")
	private BdrYourInstitutionDTO bdrYourInstitutionDTO;
	@JacksonXmlProperty(localName = "outsideInstitution")
	private BdrOutsideInstitutionDTO bdrOutsideInstitutionDTO;
	private String message;
}
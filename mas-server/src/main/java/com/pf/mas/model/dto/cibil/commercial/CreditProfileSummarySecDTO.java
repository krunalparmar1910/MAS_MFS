package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditProfileSummarySec")
public class CreditProfileSummarySecDTO {
	@JacksonXmlProperty(localName = "yourInstitution")
	public CpsYourInstitutionDTO cpsYourInstitutionDTO;
	@JacksonXmlProperty(localName = "outsideInstitution")
	public CpsOutsideInstitutionDTO cpsOutsideInstitutionDTO;
	@JacksonXmlProperty(localName = "total")
	public CpsInstitutionWiseTotalDTO totalDTO;
}

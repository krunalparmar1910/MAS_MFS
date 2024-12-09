package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "yourInstitution")
public class CpsYourInstitutionDTO {
	private String message;
	private Long totalLenders;
	@JacksonXmlProperty(localName = "totalCF")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "totalCF")
	private TotalCfDTO totalCfDTO;
	@JacksonXmlProperty(localName = "openCF")
	private Long openCf;
	private TotalOutstandingDTO totalOutstanding;
	@JacksonXmlProperty(localName = "delinquentCF")
	private DelinquentCfDTO delinquentCfDTO;
	@JacksonXmlProperty(localName = "latestCFOpenedDate")
	private String latestCfOpenedDate;
	@JacksonXmlProperty(localName = "delinquentOutstanding")
	private DelinquentOutstandingDTO delinquentOutstandingDTO;
}
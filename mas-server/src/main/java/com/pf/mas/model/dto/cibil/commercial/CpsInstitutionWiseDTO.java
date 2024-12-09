package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class CpsInstitutionWiseDTO {
	private String message;
	private Long totalLenders;
	@JacksonXmlProperty(localName = "totalCF")
	private TotalCfDTO totalCfDTO;
	@JacksonXmlProperty(localName = "openCF")
	private Long openCf;
	@JacksonXmlProperty(localName = "totalOutstanding")
	private TotalOutstandingDTO totalOutstandingDTO;
	@JacksonXmlProperty(localName = "latestCFOpenedDate")
	private String latestCfOpenedDate;
	@JacksonXmlProperty(localName = "delinquentCF")
	private DelinquentCfDTO delinquentCfDTO;
	@JacksonXmlProperty(localName = "delinquentOutstanding")
	private DelinquentOutstandingDTO delinquentOutstandingDTO;
}
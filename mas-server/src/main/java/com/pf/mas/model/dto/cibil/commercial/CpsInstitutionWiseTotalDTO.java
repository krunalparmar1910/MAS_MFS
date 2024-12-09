package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class CpsInstitutionWiseTotalDTO {
	private String message;
	private Long totalLenders;
	@JacksonXmlProperty(localName = "totalCF")
	private Long totalCf;
	@JacksonXmlProperty(localName = "openCF")
	private Long openCf;
	@JacksonXmlProperty(localName = "totalOutstanding")
	private Long totalOutstanding;
	@JacksonXmlProperty(localName = "latestCFOpenedDate")
	private String latestCfOpenedDate;
	@JacksonXmlProperty(localName = "delinquentCF")
	private Long delinquentCf;
	@JacksonXmlProperty(localName = "delinquentOutstanding")
	private Long delinquentOutstanding;
}
package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "otherDetails")
public class CreditFacilityCurrentMiscDetailDTO {
	private String repaymentFrequency;
	private Long tenure;
	private String assetBasedSecurityCoverage;
	private String weightedAverageMaturityPeriodOfContracts;
	private String restructingReason;
	private String guaranteeCoverage;
}
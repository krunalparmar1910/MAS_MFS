package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilitySecurityDetails")
public class CreditFacilitySecurityDetailsDTO {
	private String relatedType;
	private String classification;
	private Long value;
	private String currency;
	private String validationDt;
	private String lastReportedDt;
}
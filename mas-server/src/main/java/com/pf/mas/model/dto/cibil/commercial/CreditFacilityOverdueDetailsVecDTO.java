package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityOverdueDetailsVec")
public class CreditFacilityOverdueDetailsVecDTO {
	private String message;
	private CreditFacilityOverdueDetailsDTO creditFacilityOverdueDetails;
}
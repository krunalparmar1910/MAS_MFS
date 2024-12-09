package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityDetailsasGuarantorVec")
public class CreditFacilityDetailsAsGuarantorRecDTO {
	@JacksonXmlProperty(localName = "creditFacilityCurrentDetails")
	private CreditFacilityCurrentDetailsDTO creditFacilityCurrentDetailsDTO;
	@JacksonXmlProperty(localName = "borrwerInfo")
	private BorrowerProfileGuarantorSecDTO borrowerProfileGuarantorSecDTO;
}
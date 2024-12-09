package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "creditFacilitySecurityDetailsVec")
public class CreditFacilitySecurityDetailsVecDTO {
	private String message;
	@JacksonXmlProperty(localName = "creditFacilitySecurityDetails")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<CreditFacilitySecurityDetailsDTO> creditFacilitySecurityDetailsDTOList;
}
package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "creditFacilityDetailsasBorrowerSecVec")
public class CreditFacilityDetailsAsBorrowerSecVecDTO {
	private String message;
	@JacksonXmlProperty(localName = "creditFacilityDetailsasBorrowerSec")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<CreditFacilityDetailsAsBorrowerSecDTO> creditFacilityDetailsAsBorrowerSecDTOList;
}
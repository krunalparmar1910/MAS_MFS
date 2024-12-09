package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "countOfCreditFacilities")
public class CountOfCreditFacilitiesDTO {
	private NoOfCreditProvidersDTO noOfCreditFacilities;
	private NoOfCreditProvidersDTO noOfCreditGrantors;
}
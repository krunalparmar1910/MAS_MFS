package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "creditFacilitiesSummary")
public class CreditFacilitiesSummaryDTO {
	@JacksonXmlProperty(localName = "countOfCreditFacilities")
	private CountOfCreditFacilitiesDTO countOfCreditFacilitiesDTO;
	@JacksonXmlProperty(localName = "summaryOfCreditFacilitiesVec")
	private SummaryOfCreditFacilitiesVecDTO summaryOfCreditFacilitiesVecDTO;

}
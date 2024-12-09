package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "NONSTDVec")
public class NonStdVecDTO {
	@JacksonXmlProperty(localName = "DPD91to180")
	private DpdDetailsDTO dpd91to180;
	@JacksonXmlProperty(localName = "greaterthan180DPD")
	private DpdDetailsDTO greaterThan180DPD;
	private DpdDetailsDTO sub;
	private DpdDetailsDTO dbt;
	private DpdDetailsDTO loss;
}
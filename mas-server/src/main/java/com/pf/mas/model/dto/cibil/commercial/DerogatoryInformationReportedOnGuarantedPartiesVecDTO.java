package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "derogatoryInformationReportedOnGuarantedPartiesVec")
public class DerogatoryInformationReportedOnGuarantedPartiesVecDTO {
	private List<String> derogatoryInformationReportedOnGuarantedParties;
}
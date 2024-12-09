package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "borrwerIDDetailsVec")
public class BorrowerIdDetailsVecDTO {
	private String lastReportedDate;
	@JacksonXmlProperty(localName = "borrwerIDDetails")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BorrowerIdDetailsDTO> borrowerIdDetailsDTOS;
	@JacksonXmlProperty(localName = "otherIDDetails")
	private OtherIdDetailsDTO otherIdDetailsDTOS;
}
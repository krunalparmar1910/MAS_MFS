package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Telephones")
public class ApplicantTelephonesCommercialDTO {
	@JacksonXmlProperty(localName = "Telephone")
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<ApplicantTelephoneCommercialDTO> applicantTelephoneCommercialDTO;
}
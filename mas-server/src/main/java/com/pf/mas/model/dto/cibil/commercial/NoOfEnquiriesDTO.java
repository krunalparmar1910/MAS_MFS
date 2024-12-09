package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.dto.cibil.commercial.EnquiryDetailsDTO;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "noOfEnquiries")
public class NoOfEnquiriesDTO {
	@JacksonXmlProperty(localName = "noOfEnquiries")
	private EnquiryDetailsDTO enquiryDetailsDTO;

}
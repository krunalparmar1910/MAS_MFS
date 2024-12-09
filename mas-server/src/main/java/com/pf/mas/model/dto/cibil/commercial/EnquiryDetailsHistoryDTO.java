package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "enquiryDetailsHistory")
public class EnquiryDetailsHistoryDTO {
	private String creditLender;
	private String enquiryDt;
	private String enquiryPurpose;
	private Long enquiryAmt;
}
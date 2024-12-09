package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class EnquiryDetailsDTO {
	private Long month1;
	private Long month2to3;
	private Long month4to6;
	private Long month7to12;
	private Long month12to24;
	@JacksonXmlProperty(localName = "greaterthan24Month")
	private Long greaterThan24Month;
	private Long total;
	private String mostRecentDate;
}
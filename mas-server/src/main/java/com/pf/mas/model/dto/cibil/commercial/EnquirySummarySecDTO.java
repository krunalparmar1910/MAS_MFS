package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "enquirySummarySec")
public class EnquirySummarySecDTO {
	private NoOfEnquiriesDTO enquiryYourInstitution;
	private NoOfEnquiriesDTO enquiryOutsideInstitution;
	private NoOfEnquiriesDTO enquiryTotal;
}
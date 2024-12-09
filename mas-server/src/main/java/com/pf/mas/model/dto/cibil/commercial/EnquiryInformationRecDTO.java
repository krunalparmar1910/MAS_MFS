package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "enquiryInformationRec")
public class EnquiryInformationRecDTO {

	private String borrowerName;
	private String dateOfRegistration;
	private String pan;
	private String cin;
	private String tin;
	private String crn;
	private Long addressCount;
	@JacksonXmlProperty(localName = "addressVec")
	private AddressVecDTO addressVecDTO;
}
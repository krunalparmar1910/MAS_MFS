package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "address")
public class EnquiryInfoAddressDTO {
	private String addressLine;
	private String city;
	private String state;
	private String pinCode;
}
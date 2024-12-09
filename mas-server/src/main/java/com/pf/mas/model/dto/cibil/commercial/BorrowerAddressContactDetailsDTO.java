package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrwerAddressContactDetails")
public class BorrowerAddressContactDetailsDTO {
	private String address;
	private String telephoneNumber;
	private String faxNumber;
	private String mobileNumber;
}
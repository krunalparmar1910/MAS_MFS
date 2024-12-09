package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrwerIDDetails")
public class BorrowerIdDetailsDTO {
	private String cin;
	private String pan;
	private String tin;
	private String din;
	private String voterID;
	private String passportNumber;
	private String drivingLicenseNo;
	private String uid;
	private String rationCard;
	private String registrationNumber;
	private String serviceTaxNumber;
}
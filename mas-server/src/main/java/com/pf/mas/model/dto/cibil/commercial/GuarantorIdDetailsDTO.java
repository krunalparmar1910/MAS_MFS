package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "guarantorIDDetails")
public class GuarantorIdDetailsDTO {
	private String pan;
	private String registrationNumber;
	private String cin;
	private String tin;
	private String serviceTaxNumber;
	private String din;
	private String voterID;
	private String passportNumber;
	private String uid;
	private String drivingLicenseNumber;
	private String rationCard;
	private String otherID;
}

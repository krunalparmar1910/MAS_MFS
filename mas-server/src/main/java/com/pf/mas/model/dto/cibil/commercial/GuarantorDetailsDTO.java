package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "guarantorDetails")
public class GuarantorDetailsDTO {
	private String name;
	private String relatedType;
	private String dateOfBirth;
	private String gender;
	private String dateOfIncorporation;
	private String businessCategory;
	private String businessIndustryType;
	private String message;
}
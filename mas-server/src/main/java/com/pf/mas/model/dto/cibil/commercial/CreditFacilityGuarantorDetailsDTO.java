package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityGuarantorDetails")
public class CreditFacilityGuarantorDetailsDTO {
	@JacksonXmlProperty(localName = "guarantorDetails")
	private GuarantorDetailsDTO guarantorDetailsDTO;
	@JacksonXmlProperty(localName = "guarantorAddressContactDetails")
	private GuarantorAddressContactDetailsDTO guarantorAddressContactDetailsDTO;
	@JacksonXmlProperty(localName = "guarantorDetailsBorrwerIDDetailsVec")
	private GuarantorDetailsBorrowerIdDetailsVecDTO guarantorDetailsBorrowerIdDetailsVecDTO;
}
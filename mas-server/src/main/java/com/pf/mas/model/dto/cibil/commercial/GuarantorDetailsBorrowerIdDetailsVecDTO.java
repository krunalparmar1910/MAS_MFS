package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "guarantorDetailsBorrwerIDDetailsVec")
public class GuarantorDetailsBorrowerIdDetailsVecDTO {
	private String lastReportedDate;
	@JacksonXmlProperty(localName = "guarantorIDDetails")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<GuarantorIdDetailsDTO> guarantorIdDetailsDTOList;

	@JacksonXmlProperty(localName = "otherIDDetails")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<OtherIdDetailsDTO> otherIdDetailsDTOList;
}
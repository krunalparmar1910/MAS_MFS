package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "locationDetailsSec")
public class LocationDetailsSecDTO {
	private String message;
	@JacksonXmlProperty(localName = "locationInformationVec")
	private List<LocationInformationDTO> locationInformationDTOList;
	private String contactNumber;
	private String faxNumber;
	@JacksonXmlProperty(localName = "locationDispute")
	private DisputeRemarksDTO locationDispute;
}

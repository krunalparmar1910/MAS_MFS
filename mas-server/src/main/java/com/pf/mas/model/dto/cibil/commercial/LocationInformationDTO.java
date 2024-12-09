package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "locationInformation")
public class LocationInformationDTO {
	private String borrowerOfficeLocationType;
	private String address;
	private String firstReportedDate;
	private String lastReportedDate;
	private Long numberOfInstitutions;
}
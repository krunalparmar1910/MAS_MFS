package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "relationshipDetails")
public class RelationshipDetailsDTO {
	private String relationshipHeader;
	@JacksonXmlProperty(localName = "relationshipInformation")
	private RelationshipInformationDTO relationshipInformationDTO;
	@JacksonXmlProperty(localName = "borrwerAddressContactDetails")
	private BorrowerAddressContactDetailsDTO borrowerAddressContactDetailsDTO;
	@JacksonXmlProperty(localName = "borrwerIDDetailsVec")
	private BorrowerIdDetailsVecDTO borrowerIdDetailsVecDTO;
}
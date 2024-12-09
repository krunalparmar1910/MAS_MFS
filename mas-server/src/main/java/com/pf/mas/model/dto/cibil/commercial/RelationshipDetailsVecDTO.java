package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "relationshipDetailsVec")
public class RelationshipDetailsVecDTO {
	private String message;
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<RelationshipDetailsDTO> relationshipDetails;
	private DisputeRemarksDTO relationshipDispute;
}
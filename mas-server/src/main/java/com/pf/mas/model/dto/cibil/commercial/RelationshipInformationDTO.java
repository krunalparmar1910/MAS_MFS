package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "relationshipInformation")
public class RelationshipInformationDTO {
	private String name;
	private String relatedType;
	private String relationship;
	private String percentageOfControl;
	private String dateOfIncorporation;
	private String dateOfBirth;
	private String gender;
	private String businessCategory;
	private String businessIndustryType;
	private String classOfActivity1;
}

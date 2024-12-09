package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "borrwerDetails")
public class BorrowerDetailsDTO {
	private String name;
	private String borrowersLegalConstitution;
	@JacksonXmlElementWrapper(localName = "classOfActivityVec")
	@JacksonXmlProperty(localName = "classOfActivity")
	private List<String> classOfActivityVec;
	private String businessCategory;
	private String businessIndustryType;
	private String dateOfIncorporation;
	private String salesFigure;
	private String numberOfEmployees;
	private String year;
}
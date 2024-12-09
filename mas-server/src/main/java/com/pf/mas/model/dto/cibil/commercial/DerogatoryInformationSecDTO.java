package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "derogatoryInformationSec")
public class DerogatoryInformationSecDTO {
	private String messageOfBorrower;
	private String messageOfBorrowerYourInstitution;
	private String messageOfBorrowerOutsideInstitution;
	private String messageOfRelatedParties;
	private String messageOfRelatedPartiesYourInstitution;
	private String messageOfRelatedPartiesOutsideInstitution;
	private String messageOfGuarantedParties;
	@JacksonXmlProperty(localName = "derogatoryInformationBorrower")
	private DerogatoryInformationBorrowerDTO derogatoryInformationBorrowerDTO;
	@JacksonXmlProperty(localName = "derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec")
	private DerogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSecDTO derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSecDTO;
	private DerogatoryInformationReportedOnGuarantedPartiesVecDTO derogatoryInformationReportedOnGuarantedPartiesVec;
}
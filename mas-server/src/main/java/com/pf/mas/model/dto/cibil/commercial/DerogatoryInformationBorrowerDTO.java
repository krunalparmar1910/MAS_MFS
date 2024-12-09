package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "derogatoryInformationBorrower")
public class DerogatoryInformationBorrowerDTO {
	private DerogatoryInformationFinancialHistoryDTO yourInstitution;
	private DerogatoryInformationFinancialHistoryDTO outsideInstitution;
	private DerogatoryInformationFinancialHistoryDTO total;
}
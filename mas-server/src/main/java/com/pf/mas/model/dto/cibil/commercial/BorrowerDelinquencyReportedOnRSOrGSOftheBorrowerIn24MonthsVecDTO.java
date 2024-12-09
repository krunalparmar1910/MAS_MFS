package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVec")
public class BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO {
	@JacksonXmlProperty(localName = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months")
	private List<BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTO> borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTOList;
	private String message;
}
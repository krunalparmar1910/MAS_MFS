package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "yourInstitution")
public class DerogatoryInformationFinancialHistoryDTO {
	private Long wilfulDefault;
	private SuitFiledDetailsDTO suitFilled;
	private SuitFiledDetailsDTO writtenOff;
	private SuitFiledDetailsDTO settled;
	private SuitFiledDetailsDTO invoked;
	private SuitFiledDetailsDTO overdueCF;
	private Long dishonoredCheque;
}

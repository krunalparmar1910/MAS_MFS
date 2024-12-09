package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilitiesDetails")
public class CreditFacilitiesDetailsSecDTO {
	private String creditType;
	private String group;
	private String ownership;
	private String accountNo;
	private String reportedBy;
	private String currentBalance;
	private String closedDate;
	private String lastReported;
}
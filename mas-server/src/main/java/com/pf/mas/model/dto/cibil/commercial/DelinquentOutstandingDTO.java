package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "delinquentOutstanding")
public class DelinquentOutstandingDTO {
	private Long borrower;
	private String borrowerPercentage;
	private Long guarantor;
	private String guarantorPercentage;
}
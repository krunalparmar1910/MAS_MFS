package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "delinquentCF")
public class DelinquentCfDTO {
	private Long borrower;
	private Long guarantor;
	private Long borrowerPercentage;
	private Long guarantorPercentage;
}
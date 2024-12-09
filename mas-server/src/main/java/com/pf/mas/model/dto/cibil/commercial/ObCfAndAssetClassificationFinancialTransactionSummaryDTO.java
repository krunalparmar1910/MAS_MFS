package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class ObCfAndAssetClassificationFinancialTransactionSummaryDTO {
	@JacksonXmlProperty(localName = "nonFunded")
	private ObCfAndAssetClassificationTransactionTypeDetailsDTO nonFunded;
	@JacksonXmlProperty(localName = "workingCapital")
	private ObCfAndAssetClassificationTransactionTypeDetailsDTO workingCapital;
	@JacksonXmlProperty(localName = "termLoan")
	private ObCfAndAssetClassificationTransactionTypeDetailsDTO termLoan;
	@JacksonXmlProperty(localName = "forex")
	private ObCfAndAssetClassificationTransactionTypeDetailsDTO forex;
	@JacksonXmlProperty(localName = "total")
	private ObCfAndAssetClassificationTransactionTypeDetailsDTO total;
}
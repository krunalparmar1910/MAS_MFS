package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "oustandingBalanceByCFAndAssetClasificationSec")
public class ObCfAndAssetClassificationSecDTO {
	private String message;
	private ObCfAndAssetClassificationFinancialTransactionSummaryDTO yourInstitution;
	private ObCfAndAssetClassificationFinancialTransactionSummaryDTO outsideInstitution;
}
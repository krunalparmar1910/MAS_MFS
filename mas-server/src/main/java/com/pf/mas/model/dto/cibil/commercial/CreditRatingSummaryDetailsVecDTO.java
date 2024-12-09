package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditRatingSummaryDetailsVec")
public class CreditRatingSummaryDetailsVecDTO {
	private String creditRating;
	private String ratingAsOn;
	private String ratingExpiryDt;
	private String lastReportedDt;

}
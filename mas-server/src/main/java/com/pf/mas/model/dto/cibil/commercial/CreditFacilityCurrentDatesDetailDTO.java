package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "dates")
public class CreditFacilityCurrentDatesDetailDTO {
	private String sanctionedDt;
	private String loanExpiryDt;
	private String loanRenewalDt;
	private String suitFiledDt;
	private String wilfulDefault;
	private String guaranteeInvocationDate;
}
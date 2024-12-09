package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityCurrentDetails")
public class CreditFacilityCurrentDetailsDTO {
	private String derivative;
	private String accountNumber;
	private String cfSerialNumber;
	private String cfType;
	private String cfMember;
	private String assetClassificationDaysPastDueDpd;
	private String status;
	private String statusDate;
	private String lastReportedDate;
	@JacksonXmlProperty(localName = "amount")
	private CreditFacilityCurrentAmountDetailsDTO amount;
	@JacksonXmlProperty(localName = "dates")
	private CreditFacilityCurrentDatesDetailDTO dates;
	@JacksonXmlProperty(localName = "otherDetails")
	private CreditFacilityCurrentMiscDetailDTO otherDetails;
	@JacksonXmlProperty(localName = "creditFacilityDispute")
	private DisputeRemarksDTO creditFacilityDispute;
}
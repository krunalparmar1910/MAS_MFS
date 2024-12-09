package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months")
public class BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTO {

	@JacksonXmlProperty(localName = "detail")
	private String detail;

	@JacksonXmlProperty(localName = "relationship")
	private String relationship;

	@JacksonXmlProperty(localName = "tl_outstanding")
	private String tlOutstanding;

	@JacksonXmlProperty(localName = "wc_count")
	private String wcCount;

	@JacksonXmlProperty(localName = "wc_outstanding")
	private String wcOutstanding;

	@JacksonXmlProperty(localName = "nf_count")
	private String nfCount;

	@JacksonXmlProperty(localName = "fx_count")
	private String fxCount;

	@JacksonXmlProperty(localName = "fx_outstanding")
	private String fxOutstanding;

	@JacksonXmlProperty(localName = "tl_count")
	private String tlCount;

	@JacksonXmlProperty(localName = "nf_outstanding")
	private String nfOutstanding;
}
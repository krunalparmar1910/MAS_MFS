package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class EnquiryNumberSegmentDTO {
	@JacksonXmlProperty(localName = "Length")
	private  String length;
	@JacksonXmlProperty(localName = "SegmentTag")
	private  String segmentTag;
	@JacksonXmlProperty(localName = "AccountNumberFieldLength")
	private  String accountNumberFieldLength;
	@JacksonXmlProperty(localName = "AccountNumber")
	private  String accountNumber;
}
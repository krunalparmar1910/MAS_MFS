package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ConsumerDisputeRemarksDTO {
	@JacksonXmlProperty(localName = "Length")
	private String length;

	@JacksonXmlProperty(localName = "SegmentTag")
	private String segmentTag;

	@JacksonXmlProperty(localName = "DateOfEntry")
	private String dateOfEntry;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks1FieldLength")
	private String consumerDisputeRemarks1FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks1")
	private String consumerDisputeRemarks1;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks2FieldLength")
	private String consumerDisputeRemarks2FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks2")
	private String consumerDisputeRemarks2;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks3FieldLength")
	private String consumerDisputeRemarks3FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks3")
	private String consumerDisputeRemarks3;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks4FieldLength")
	private String consumerDisputeRemarks4FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks4")
	private String consumerDisputeRemarks4;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks5FieldLength")
	private String consumerDisputeRemarks5FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks5")
	private String consumerDisputeRemarks5;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks6FieldLength")
	private String consumerDisputeRemarks6FieldLength;

	@JacksonXmlProperty(localName = "ConsumerDisputeRemarks6")
	private String consumerDisputeRemarks6;

}
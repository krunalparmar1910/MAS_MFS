package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DsCommercialCIR")
public class DsCommercialCIRDTO {
	@JacksonXmlProperty(localName = "IsSuccess")
	private boolean isSuccess;

	@JacksonXmlProperty(localName = "ErrorCode")
	private Long errorCode;

	@JacksonXmlProperty(localName = "ErrorResponse")
	private String errorResponse;

	@JacksonXmlProperty(localName = "RawResponse")
	private RawResponseDTO rawResponseDTO;
}

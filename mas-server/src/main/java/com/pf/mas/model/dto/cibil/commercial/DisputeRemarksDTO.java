package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrowerDispute")
public class DisputeRemarksDTO {
	private String disputeRemarks;
	private String disputeRemarksDate;
}
package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditRatingSummaryVec")
public class CreditRatingSummaryVecDTO {
	private String message;
	@JacksonXmlProperty(localName = "creditRatingSummary")
	private CreditRatingSummaryRecDTO creditRatingSummaryRecDTO;
}
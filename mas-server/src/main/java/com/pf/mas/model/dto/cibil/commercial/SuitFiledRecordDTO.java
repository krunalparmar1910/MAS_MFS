package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "suitFilled")
public class SuitFiledRecordDTO {
	private String suitFilledBy;
	private String suitStatus;
	private String suitRefNumber;
	private String suitAmt;
	private String dateSuit;
}
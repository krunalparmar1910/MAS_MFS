package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.entity.base.BaseID;
import lombok.Data;


@Data
@JacksonXmlRootElement(localName = "summaryOfCreditFacilitiesRec")
public class SummaryOfCreditFacilitiesRecDTO extends BaseID {
	private String creditType;
	private String yourBank;
	private String othersBank;
}
package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class NoOfCreditProvidersDTO {
	private Long yourBank;
	private Long others;
	private Long total;
}
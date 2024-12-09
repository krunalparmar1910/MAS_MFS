package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "chequeDishounouredDuetoInsufficientFunds")
public class ChequeDishounouredDuetoInsufficientFundsDTO {
	private String message;
	@JacksonXmlProperty(localName = "CD3monthcount")
	private String cd3monthCount;
	@JacksonXmlProperty(localName = "CD4to6monthcount")
	private String cd4to6monthCount;
	@JacksonXmlProperty(localName = "CD7to9monthcount")
	private String cd7to9monthCount;
	@JacksonXmlProperty(localName = "CD10to12monthcount")
	private String cd10to12monthCount;
}
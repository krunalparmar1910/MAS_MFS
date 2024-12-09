package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STDVec")
public class StdVecDTO {
	@JacksonXmlProperty(localName = "DPD0")
	private DpdDetailsDTO dpd0;
	@JacksonXmlProperty(localName = "DPD1to30")
	private DpdDetailsDTO dpd1to30;
	@JacksonXmlProperty(localName = "DPD31to60")
	private DpdDetailsDTO dpd31to60;
	@JacksonXmlProperty(localName = "DPD61to90")
	private DpdDetailsDTO dpd61to90;
}
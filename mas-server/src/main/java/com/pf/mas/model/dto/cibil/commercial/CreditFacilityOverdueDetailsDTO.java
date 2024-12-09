package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityOverdueDetails")
public class CreditFacilityOverdueDetailsDTO {
	@JacksonXmlProperty(localName = "DPD1to30amt")
	private int dpd1to30amt;
	@JacksonXmlProperty(localName = "DPD31to60amt")
	private int dpd31to60amt;
	@JacksonXmlProperty(localName = "DPD61t090amt")
	private int dpd61t090amt;
	@JacksonXmlProperty(localName = "DPD91to180amt")
	private int dpd91to180amt;
	@JacksonXmlProperty(localName = "DPDabove180amt")
	private int dpdAbove180amt;
}
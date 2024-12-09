package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class ObCfAndAssetClassificationTransactionTypeDetailsDTO {
	@JacksonXmlProperty(localName = "STDVec")
	private StdVecDTO stdVecDTO;
	@JacksonXmlProperty(localName = "NONSTDVec")
	private NonStdVecDTO nonStdVecDTO;
	@JacksonXmlProperty(localName = "total")
	private DpdDetailsDTO total;
}

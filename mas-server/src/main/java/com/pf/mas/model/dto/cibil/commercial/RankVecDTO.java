package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "rankVec")
public class RankVecDTO {
	private String rankName;
	private String rankValue;
	private String exclusionReason;
}
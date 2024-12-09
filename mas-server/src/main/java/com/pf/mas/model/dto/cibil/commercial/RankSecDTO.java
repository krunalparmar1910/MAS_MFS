package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "rankSec")
public class RankSecDTO {
	private String message;
	@JacksonXmlElementWrapper(useWrapping = false, localName = "rankVec")
	@JacksonXmlProperty(localName = "rankVec")
	private List<RankVecDTO> rankVecDTO;
}
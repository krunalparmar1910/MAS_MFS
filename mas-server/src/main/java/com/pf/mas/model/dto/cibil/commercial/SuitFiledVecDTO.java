package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "suitFiledVec")
public class SuitFiledVecDTO {
	private String message;
	@JacksonXmlProperty(localName = "suitFilled")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<SuitFiledRecordDTO> suitFiledRecordDTOList;
}
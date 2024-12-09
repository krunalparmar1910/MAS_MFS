package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.entity.commercial.CreditFacilitiesDetailsSec;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "creditFacilitiesDetailsVec")
public class CreditFacilitiesDetailsVecDTO {
	private String message;
	private String total;

	@JacksonXmlProperty(localName = "creditFacilitiesDetails")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<CreditFacilitiesDetailsSecDTO> creditFacilitiesDetailsSecDTOList;

}
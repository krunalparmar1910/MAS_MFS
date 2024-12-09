package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "addressVec")
public class AddressVecDTO {
	@JacksonXmlProperty(localName = "address")
	@JacksonXmlElementWrapper(localName = "address", useWrapping = false)
	private List<EnquiryInfoAddressDTO> enquiryInfoAddressDTOList;
}
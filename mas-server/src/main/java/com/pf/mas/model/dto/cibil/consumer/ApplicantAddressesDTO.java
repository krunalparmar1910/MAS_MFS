package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Addresses")
public class ApplicantAddressesDTO {
    @JacksonXmlProperty(localName = "Address")
    @JacksonXmlElementWrapper(localName = "Address", useWrapping = false)
    private List<ApplicantAddressDTO> addressList;
}

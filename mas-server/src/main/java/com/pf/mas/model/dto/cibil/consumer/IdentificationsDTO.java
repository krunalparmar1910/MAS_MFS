package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Identifications")
public class IdentificationsDTO {
    @JacksonXmlProperty(localName = "Identification")
    @JacksonXmlElementWrapper(localName = "Identification", useWrapping = false)
    private List<IdentificationDTO> identificationDTOList;
}
package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Body")
public class BodyDTO {
    @JacksonXmlProperty(localName = "ExecuteXMLStringResponse", namespace = "http://tempuri.org/")
    private ExecuteXMLStringResponseDTO executeXMLStringResponseDTO;
}

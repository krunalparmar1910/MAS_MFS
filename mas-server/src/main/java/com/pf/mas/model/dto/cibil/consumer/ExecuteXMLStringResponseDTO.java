package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ExecuteXMLStringResponse")
public class ExecuteXMLStringResponseDTO {
    @JacksonXmlProperty(localName = "ExecuteXMLStringResult", namespace = "http://tempuri.org/")
    private ExecuteXMLStringResultDTO executeXMLStringResultDTO;

}
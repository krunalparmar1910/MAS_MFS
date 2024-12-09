package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Body")
public class BodyCommercialDTO {
    @JacksonXmlProperty(localName = "ExecuteXMLStringResponse", namespace = "http://tempuri.org/")
    private ExecuteXMLStringResponseCommercialDTO executeXMLStringResponseCommercialDTO;
}

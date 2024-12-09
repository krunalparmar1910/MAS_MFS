package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ExecuteXMLStringResult")
public class ExecuteXMLStringResultCommercialDTO {
    @JacksonXmlProperty(localName = "DCResponse")
    private DcResponseCommercialDTO dcResponseCommercialDTO;
}

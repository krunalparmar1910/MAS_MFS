package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DsCibilBureauStatus")
public class DsCibilBureauStatusDTO {
    @JacksonXmlProperty(localName = "Trail")
    private String trail;
}
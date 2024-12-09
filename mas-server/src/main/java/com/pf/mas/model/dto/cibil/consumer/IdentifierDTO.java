package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Identifier")
public class IdentifierDTO {
    @JacksonXmlProperty(localName = "IdNumber")
    private String idNumber;
    @JacksonXmlProperty(localName = "IdType")
    private String idType;
}
package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Document")
public class DocumentDTO {
    @JacksonXmlProperty(localName = "Id")
    private Long documentId;
    @JacksonXmlProperty(localName = "Name")
    private String name;
}
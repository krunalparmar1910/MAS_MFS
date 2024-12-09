package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;


@Data
@JacksonXmlRootElement(localName = "Authentication")
public class AuthenticationDTO {
    @JacksonXmlProperty(localName = "Status")
    private String status;
    @JacksonXmlProperty(localName = "Token")
    private String token;
}

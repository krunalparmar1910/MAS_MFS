package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DCResponse")
public class DcResponseDTO {
    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "Authentication")
    private AuthenticationDTO authentication;

    @JacksonXmlProperty(localName = "ResponseInfo")
    private ResponseInfoDTO responseInfo;

    @JacksonXmlProperty(localName = "ContextData")
    private ContextDataDTO contextDataDTO;


}

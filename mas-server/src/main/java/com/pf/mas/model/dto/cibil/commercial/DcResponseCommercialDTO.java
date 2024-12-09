package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.dto.cibil.consumer.AuthenticationDTO;
import com.pf.mas.model.dto.cibil.consumer.ResponseInfoDTO;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DCResponse")
public class DcResponseCommercialDTO {
    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "Authentication")
    private AuthenticationDTO authentication;

    @JacksonXmlProperty(localName = "ResponseInfo")
    private ResponseInfoDTO responseInfo;

    @JacksonXmlProperty(localName = "ContextData")
    private ContextDataCommercialDTO contextDataCommercialDTO;


}

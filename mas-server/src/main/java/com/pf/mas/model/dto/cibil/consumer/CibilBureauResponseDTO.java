package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CibilBureauResponse")
public class CibilBureauResponseDTO {

    @JacksonXmlProperty(localName = "BureauResponseRaw")
    private String bureauResponseRaw;

    @JacksonXmlProperty(localName = "SecondaryReportXml")
    private SecondaryReportXmlDTO secondaryReportXmlDTO;

    @JacksonXmlProperty(localName = "IsSucess")
    private Boolean isSuccess;

    @JacksonXmlProperty(localName = "BureauResponseXml")
    private BureauResponseXmlDTO bureauResponseXmlDTO;
}
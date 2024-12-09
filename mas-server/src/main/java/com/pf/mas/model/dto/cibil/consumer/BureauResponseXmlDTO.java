package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "BureauResponseXml")
public class BureauResponseXmlDTO {
    @JacksonXmlProperty(localName = "CreditReport")
    private CreditReportDTO creditReportDTO;
}
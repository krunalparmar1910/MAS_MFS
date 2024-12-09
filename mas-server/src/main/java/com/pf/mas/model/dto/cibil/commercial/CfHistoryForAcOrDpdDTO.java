package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CFHistoryforACOrDPD")
public class CfHistoryForAcOrDpdDTO {
    private String month;
    @JacksonXmlProperty(localName = "ACorDPD")
    private String acOrDpd;
    @JacksonXmlProperty(localName = "OSAmount")
    private Long osAmount;
}
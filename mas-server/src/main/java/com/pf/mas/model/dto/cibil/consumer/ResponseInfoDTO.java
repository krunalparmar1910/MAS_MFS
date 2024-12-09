package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;


@JacksonXmlRootElement(localName = "ResponseInfo")
@Data
public class ResponseInfoDTO {
    @JacksonXmlProperty(localName = "ApplicationId")
    private String applicationId;
    @JacksonXmlProperty(localName = "SolutionSetInstanceId")
    private String solutionSetInstanceId;
    @JacksonXmlProperty(localName = "CurrentQueue")
    private String currentQueue;
}

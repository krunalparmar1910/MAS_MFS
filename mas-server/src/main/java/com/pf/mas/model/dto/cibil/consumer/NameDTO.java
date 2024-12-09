package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Name")
public class NameDTO {
    @JacksonXmlProperty(localName = "ConsumerName2")
    private String consumerName2;

    @JacksonXmlProperty(localName = "DateOfBirth")
    private String dateOfBirth;

    @JacksonXmlProperty(localName = "ConsumerName1")
    private String consumerName1;

    @JacksonXmlProperty(localName = "ConsumerName4")
    private String consumerName4;

    @JacksonXmlProperty(localName = "ConsumerName3")
    private String consumerName3;

    @JacksonXmlProperty(localName = "ConsumerName5")
    private String consumerName5;

    @JacksonXmlProperty(localName = "Gender")
    private String gender;

}
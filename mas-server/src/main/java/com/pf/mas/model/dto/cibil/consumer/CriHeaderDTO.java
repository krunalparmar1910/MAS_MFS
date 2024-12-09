package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@JacksonXmlRootElement(localName = "CriHeader")
public class CriHeaderDTO {
    @JacksonXmlProperty(localName = "ReferenceNumber")
    private BigInteger referenceNumber;

    @JacksonXmlProperty(localName = "Amount")
    private BigDecimal amount;

    @JacksonXmlProperty(localName = "OutputFormat")
    private String outputFormat;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "Purpose")
    private String purpose;

    @JacksonXmlProperty(localName = "AuthenticationMethod")
    private String authenticationMethod;

    @JacksonXmlProperty(localName = "FutureUse1")
    private String futureUse1;

    @JacksonXmlProperty(localName = "ResponseSize")
    private BigInteger responseSize;

    @JacksonXmlProperty(localName = "FutureUse2")
    private String futureUse2;

    @JacksonXmlProperty(localName = "Version")
    private BigInteger version;

    @JacksonXmlProperty(localName = "MediaType")
    private String mediaType;

    @JacksonXmlProperty(localName = "ScoreType")
    private String scoreType;

    @JacksonXmlProperty(localName = "MemberCode")
    private String memberCode;

    @JacksonXmlProperty(localName = "Password")
    private String password;
}
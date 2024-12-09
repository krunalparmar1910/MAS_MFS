package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Map;

@Data
@JacksonXmlRootElement(localName = "ScoreSegment")
public class ScoreSegmentDTO {
    @JacksonXmlProperty(localName = "ScoreCardName")
    private String scoreCardName;

    @JacksonXmlProperty(localName = "ScoreName")
    private String scoreName;

    @JacksonXmlProperty(localName = "ScoreDate")
    private String scoreDate;

    @JacksonXmlProperty(localName = "Score")
    private String score;

    @JacksonXmlProperty(localName = "Length")
    private Long length;

    @JacksonXmlProperty(localName = "ScoreCardVersion")
    private Long scoreCardVersion;

    @JacksonXmlProperty(localName = "ExclusionCode1FieldLength")
    private String exclusionCode1FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode1")
    private String exclusionCode1;

    @JacksonXmlProperty(localName = "ExclusionCode2FieldLength")
    private String exclusionCode2FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode2")
    private String exclusionCode2;

    @JacksonXmlProperty(localName = "ExclusionCode3FieldLength")
    private String exclusionCode3FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode3")
    private String exclusionCode3;

    @JacksonXmlProperty(localName = "ExclusionCode4FieldLength")
    private String exclusionCode4FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode4")
    private String exclusionCode4;

    @JacksonXmlProperty(localName = "ExclusionCode5FieldLength")
    private String exclusionCode5FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode5")
    private String exclusionCode5;

    @JacksonXmlProperty(localName = "ExclusionCode6FieldLength")
    private String exclusionCode6FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode6")
    private String exclusionCode6;

    @JacksonXmlProperty(localName = "ExclusionCode7FieldLength")
    private String exclusionCode7FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode7")
    private String exclusionCode7;

    @JacksonXmlProperty(localName = "ExclusionCode8FieldLength")
    private String exclusionCode8FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode8")
    private String exclusionCode8;

    @JacksonXmlProperty(localName = "ExclusionCode9FieldLength")
    private String exclusionCode9FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode9")
    private String exclusionCode9;

    @JacksonXmlProperty(localName = "ExclusionCode10FieldLength")
    private String exclusionCode10FieldLength;

    @JacksonXmlProperty(localName = "ExclusionCode10")
    private String exclusionCode10;

    @JacksonXmlProperty(localName = "ReasonCode1FieldLength")
    private String reasonCode1FieldLength;

    @JacksonXmlProperty(localName = "ReasonCode1")
    private String reasonCode1;

    @JacksonXmlProperty(localName = "ReasonCode2FieldLength")
    private String reasonCode2FieldLength;

    @JacksonXmlProperty(localName = "ReasonCode2")
    private String reasonCode2;

    @JacksonXmlProperty(localName = "ReasonCode3FieldLength")
    private String reasonCode3FieldLength;

    @JacksonXmlProperty(localName = "ReasonCode3")
    private String reasonCode3;

    @JacksonXmlProperty(localName = "ReasonCode4FieldLength")
    private String reasonCode4FieldLength;

    @JacksonXmlProperty(localName = "ReasonCode4")
    private String reasonCode4;

    @JacksonXmlProperty(localName = "ReasonCode5FieldLength")
    private String reasonCode5FieldLength;

    @JacksonXmlProperty(localName = "ReasonCode5")
    private String reasonCode5;

    @JacksonXmlProperty(localName = "ErrorCode")
    private String errorCode;

    @JacksonXmlProperty(localName = "TRLScore")
    private String trlScore;

    @JacksonXmlProperty(localName = "BureauCharacterstics", isAttribute = true)
    private Map<String, String> bureauCharacteristics;
}
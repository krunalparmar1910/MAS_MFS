package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JacksonXmlRootElement(localName = "reportIssuesVec")
public class ReportIssuesVecDTO {
    private String code;
    private String message;
    private String description;
}

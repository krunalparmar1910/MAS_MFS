package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "reportHeaderRec")
public class ReportHeaderRecDTO {
    private String daysPasswordToExpire;
    private String reportOrderNumber;
    private String reportOrderDate;
    private String reportOrderedBy;
    private String memberDetails;
    private Long applicationReferenceNumber;
    private String memberReferenceNumber;
    private String inquiryPurpose;
}

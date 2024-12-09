package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "responseReport")
public class ResponseReportDTO {
    @JacksonXmlProperty(localName = "reportIssuesVec")
    private List<ReportIssuesVecDTO> reportIssuesVecDTOList;
    @JacksonXmlProperty(localName = "reportHeaderRec")
    private ReportHeaderRecDTO reportHeaderRecDTO;
    @JacksonXmlProperty(localName = "enquiryInformationRec")
    private EnquiryInformationRecDTO enquiryInformationRecDTO;
    @JacksonXmlProperty(localName = "productSec")
    private ProductSecDTO productSecDTO;
}

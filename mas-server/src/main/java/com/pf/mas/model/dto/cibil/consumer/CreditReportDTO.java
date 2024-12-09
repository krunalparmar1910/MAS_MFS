package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.entity.consumer.EnquiryNumberSegment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "CreditReport")
public class CreditReportDTO {
    @JacksonXmlProperty(localName = "NameSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NameSegmentDTO> nameSegmentDTOList;

    @JacksonXmlProperty(localName = "Account")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<AccountDTO> accountDTOList = new ArrayList<>();

    @JacksonXmlProperty(localName = "Enquiry")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EnquiryDTO> enquiryDTOList = new ArrayList<>();

    @JacksonXmlProperty(localName = "EmailContactSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EmailContactSegmentDTO> emailContactSegmentDTOList;

    @JacksonXmlProperty(localName = "EnquiryNumberSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EnquiryNumberSegmentDTO> enquiryNumberSegmentDTOList;

    @JacksonXmlProperty(localName = "Header")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CreditReportHeaderDTO> creditReportHeaderDTOList;

    @JacksonXmlProperty(localName = "Address")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CreditReportAddressDTO> creditReportAddressDTOList = new ArrayList<>();

    @JacksonXmlProperty(localName = "EmploymentSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EmploymentSegmentDTO> employmentSegmentDTOList;

    @JacksonXmlProperty(localName = "IDSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<IdSegmentDTO> idSegmentDTOList = new ArrayList<>();

    @JacksonXmlProperty(localName = "End")
    @JacksonXmlElementWrapper(useWrapping = false)
    private EndDTO endDTO;

    @JacksonXmlProperty(localName = "TelephoneSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TelephoneSegmentDTO> telephoneSegmentDTOList = new ArrayList<>();

    @JacksonXmlProperty(localName = "ScoreSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ScoreSegmentDTO> scoreSegmentDTOList;

    @JacksonXmlProperty(localName = "ConsumerDisputeRemarks")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ConsumerDisputeRemarksDTO> consumerDisputeRemarksDTOList;
}
package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credit_report", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditReport extends BaseID {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<CreditReportHeader> creditReportHeaderList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<NameSegment> nameSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<IdSegment> idSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<TelephoneSegment> telephoneSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<EmailContactSegment> emailContactSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<EmploymentSegment> employmentSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<EnquiryNumberSegment> enquiryNumberSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<ScoreSegment> scoreSegmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<CreditReportAddress> creditReportAddressList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<Account> accountList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<Enquiry> enquiryList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_id")
    private List<ConsumerDisputeRemarks> consumerDisputeRemarksList;


}

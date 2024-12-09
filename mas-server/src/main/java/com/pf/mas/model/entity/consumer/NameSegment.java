package com.pf.mas.model.entity.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "name_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NameSegment extends BaseID {
    private Long length;
    private String segmentTag;
    @Column(name = "consumer_name_1")
    private String consumerName1;
    @Column(name = "consumer_name_2")
    private String consumerName2;
    @Column(name = "consumer_name_3")
    private String consumerName3;
    @Column(name = "consumer_name_4")
    private String consumerName4;
    @Column(name = "consumer_name_5")
    private String consumerName5;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfBirth;
    private String gender;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfEntryForErrorCode;
    private String errorSegmentTag;
    private String errorCode;
    private Date dateOfEntryForCibilRemarksCode;
    private String cibilRemarksCode;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfEntryForErrorDisputeRemarksCode;
    @Column(name = "error_dispute_remarks_code_1")
    private String errorDisputeRemarksCode1;
    @Column(name = "error_dispute_remarks_code_2")
    private String errorDisputeRemarksCode2;
    private String enrichThroughEnquiryForName;
    private String fid;
    private String enrichThroughEnquiryForDateOfBirth;
    private String enrichThroughEnquiryForGender;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfSuppression;
}

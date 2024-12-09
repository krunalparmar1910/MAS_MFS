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
@Table(name = "credit_report_header", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditReportHeader extends BaseID {
    private String segmentTag;
    private Long version;
    private Long referenceNumber;
    @Column(name = "future_use_1")
    private String futureUse1;
    @Column(name = "future_use_2")
    private String futureUse2;
    private String memberCode;
    private Long subjectReturnCode;
    private Long enquiryControlNumber;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateProcessed;
    private String timeProcessed;
}

package com.pf.mas.model.entity.consumer;

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
@Table(name = "employment_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EmploymentSegment extends BaseID {
    private Long length;
    private String segmentTag;
    private Long accountType;
    private Date dateReportedCertified;
    private String occupationCode;
    private String income;
    private String netGrossIndicator;
    private String monthlyAnnualIndicator;
    private Date dateOfEntryForErrorCode;
    private String errorCode;
    private Date dateOfEntryForCibilRemarksCode;
    private String cibilRemarksCode;
    private Date dateOfEntryForErrorDisputeRemarksCode;
    @Column(name = "error_dispute_remarks_code_1")
    private String errorDisputeRemarksCode1;
    @Column(name = "error_dispute_remarks_code_2")
    private String errorDisputeRemarksCode2;
    private String fid;
    private String sNo;
    private String suppressFlag;
    private Date dateOfSuppression;
}

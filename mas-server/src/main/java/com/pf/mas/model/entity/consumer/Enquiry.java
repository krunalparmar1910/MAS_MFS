package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "enquiry", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Enquiry extends BaseID {
    private Long length;
    private String segmentTag;
    private Date dateOfEnquiryFields;
    private String enquiringMemberShortName;
    private String enquiryPurpose;
    private Long enquiryAmount;
    private String fid;
    private String enquiryControlNumber;
    private String suppressFlag;
    private Date dateOfSuppression;
}

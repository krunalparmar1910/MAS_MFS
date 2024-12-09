package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_report_address", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditReportAddress extends BaseID {
    private Long length;
    private String addressSegmentTag;
    private String segmentTag;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String stateCode;
    private String pinCode;
    private String addressCategory;
    private String dateReported;
    private String residenceCode;
    private String memberShortName;
    private String fid;
    private String sNo;
    private String suppressFlag;
    private String dateOfSuppression;
}

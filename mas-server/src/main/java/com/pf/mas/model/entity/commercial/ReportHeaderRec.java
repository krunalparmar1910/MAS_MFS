package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report_header_rec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ReportHeaderRec extends BaseID {
    private String daysPasswordToExpire;
    private String reportOrderNumber;
    private String reportOrderDate;
    private String reportOrderedBy;
    private String memberDetails;
    private Long applicationReferenceNumber;
    private String memberReferenceNumber;
    private String inquiryPurpose;
}

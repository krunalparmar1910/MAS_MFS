package com.pf.mas.model.entity.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "id_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IdSegment extends BaseID {
    private Long length;
    private String segmentTag;
    private String idType;
    private String idNumber;
    private Date issueDate;
    private String enrichedThroughEnquiry;
    private String fid;
    private String sNo;
    private String suppressFlag;
    private Date dateOfSuppression;
}

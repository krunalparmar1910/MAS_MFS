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
@Table(name = "telephone_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TelephoneSegment extends BaseID {
    private Long length;
    private String segmentTag;
    private String telephoneNumber;
    private String telephoneType;
    private String enrichedThroughEnquiry;
    private String telephoneExtension;
    private String fid;
    private String sNo;
    private String suppressFlag;
    private Date dateOfSuppression;
}

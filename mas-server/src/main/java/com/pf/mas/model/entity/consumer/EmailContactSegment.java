package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email_contact_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EmailContactSegment extends BaseID {
    private Long length;
    private String segmentTag;
    private String emailId;
    private String fid;
    private String sNo;
    private String suppressFlag;
    private String dateOfSuppression;
}

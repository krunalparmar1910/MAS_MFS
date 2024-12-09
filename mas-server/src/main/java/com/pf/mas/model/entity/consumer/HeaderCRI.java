package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "header_cri", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class HeaderCRI extends BaseID {
    private String segmentTag;
    private Long version;
    private Long referenceNumber;
    private String futureUse1;
    private String memberCode;
    private String password;
    private Long purpose;
    private Long amount;
    private String futureUse2;
    private String scoreType;
    private String outputFormat;
    private Long responseSize;
    private String mediaType;
    private String authenticationMethod;
}

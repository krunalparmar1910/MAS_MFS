package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "identification_cri", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IdentificationCRI extends BaseID {
    private String panNo;
    private String passportNumber;
    @Column(name = "dl_no")
    private String dLNo;
    private String voterId;
    private String uId;
    private String rationCardNo;
    private String additionalId1;
    private String additionalId2;
    private String emailId;
}

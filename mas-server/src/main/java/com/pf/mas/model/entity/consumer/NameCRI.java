package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "name_cri", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NameCRI extends BaseID {
    private String consumerName1;
    private String consumerName2;
    private String consumerName3;
    private String consumerName4;
    private String consumerName5;
    private String dateOfBirth;
    private String gender;
}

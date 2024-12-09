package com.pf.mas.model.entity.cibil;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "los_identification_raw", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LosIdentificationRaw extends BaseUUID {
    private String idType;
    private String idNumber;
}

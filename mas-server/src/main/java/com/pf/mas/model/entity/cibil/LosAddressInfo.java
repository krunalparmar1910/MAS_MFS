package com.pf.mas.model.entity.cibil;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "los_address_info", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LosAddressInfo extends BaseUUID {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String stateCode;
    private String pinCode;
    private String category;
    private String dateReported;
}
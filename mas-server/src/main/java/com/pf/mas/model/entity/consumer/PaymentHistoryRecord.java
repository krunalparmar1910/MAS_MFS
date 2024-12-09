package com.pf.mas.model.entity.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.enums.AssetClassificationStatus;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "payment_history_record", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PaymentHistoryRecord extends BaseID {
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date date;
    private Long dpd;
    private AssetClassificationStatus status;
}

package com.pf.mas.model.entity.cibil;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.ui.DataSaveDTO;
import com.pf.mas.model.dto.cibil.ui.LosDetailsDTO;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "raw_data", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RawData extends BaseID {
    private String requestId;
    private String rawXmlString;
    private String ccFlag;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "los_details_raw_id")
    private LosDetailsRaw losDetailsRaw;
    private LocalDateTime dateRecorded;
    private String ipAddress;
    private String createdBy;
}

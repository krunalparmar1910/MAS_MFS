package com.pf.karza.model.entity;

import com.pf.common.enums.ProgressStatus;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "itr_raw_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItrRawData extends BaseId {
    private String rawData;
    private String error;
    private LocalDateTime date;
    private String username;
    private String password;
    private String consent;
    private String masRefId;
    private String requestType;
    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;
}

package com.pf.karza.model.entity;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "itr_error_log", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItrErrorLog extends BaseId {
    @Lob
    private String rawData;
    @Lob
    private String error;
    private LocalDateTime date;
    private String username;
    private String consent;
    private String masRefId;
    private String requestType;
    private String password;
}

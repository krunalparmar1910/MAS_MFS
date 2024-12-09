package com.pf.karza.model.entity;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "request", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends BaseId {
    @Column(name = "mas_ref_id", nullable = false, unique = true)
    private String masRefId;
    private String username;
    private String password;
    private Integer numberOfYears;
    private String apiVersion;
    private String consent;
    private String requestType;
    private Boolean additionalData;
    private String ipAddress;
    private String createdBy;
}

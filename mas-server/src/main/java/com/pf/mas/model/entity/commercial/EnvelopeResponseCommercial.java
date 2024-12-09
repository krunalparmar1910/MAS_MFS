package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import com.pf.mas.model.entity.consumer.AuthenticationInfo;
import com.pf.mas.model.entity.consumer.ResponseInfo;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "envelope_response_commercial", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EnvelopeResponseCommercial extends BaseID {
    private String status;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "combined_response_commercial_id")
    private CombinedResponseCommercial combinedResponseCommercial;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_info_id")
    private AuthenticationInfo authenticationInfo;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "response_info_id")
    private ResponseInfo responseInfo;
    private String requestId;
    private String ipAddress;
    private String createdBy;
}


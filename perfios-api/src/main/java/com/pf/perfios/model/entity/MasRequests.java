package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(schema = DbConst.SCHEMA_NAME)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class MasRequests extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MasReqStatus status = MasReqStatus.TRANSACTION_INITIATED;

    private String masFinancialId;

    private String customerTransactionId;

    private String perfiosTransactionId;

    private String transactionCompleteCallbackUrl;

    @Lob
    private String errorMessage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

    private String createdBy;
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private MasWebhookStatus masWebhookStatus;

    @Lob
    private String masWebhookFailedError;

    @Lob
    private String errorJsonString;

    private Boolean reportFetched;

    private String perfiosStatus;
    private String perfiosErrorMessage;
    private String perfiosErrorCode;

    private Boolean reportExpired;

    private BigDecimal creditLimit;

    @Column(name = "unique_Firm_Id")
    private String uniqueFirmId;

}

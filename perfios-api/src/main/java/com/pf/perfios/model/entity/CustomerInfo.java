package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(schema = DbConst.SCHEMA_NAME)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class CustomerInfo extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    @ToString.Include
    @EqualsAndHashCode.Include
    private String name;

    @Lob
    private String address;

    private String email;

    private String pan;

    private String mobileNumber;

    private String landline;

    @Column(unique = true)
    private String customerTransactionId;

    @Column(unique = true)
    private String perfiosTransactionId;

    private String masFinancialId;

    private String bankName;

    private Long instId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.Pending;

    @OneToMany(mappedBy = "customerInfo", fetch = FetchType.LAZY)
    private List<AccountSummary> accountSummaries;

    @OneToMany(mappedBy = "customerInfo", fetch = FetchType.LAZY)
    private List<StatementsConsidered> statementsConsidered;

    @OneToOne(mappedBy = "customerInfo", fetch = FetchType.LAZY)
    private MasRequests masRequests;
}

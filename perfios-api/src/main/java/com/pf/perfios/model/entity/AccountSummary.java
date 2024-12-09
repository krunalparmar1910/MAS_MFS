package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class AccountSummary extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

    private String institutionName;

    private String accountNumber;

    private Integer fullMonthCount;

    private String accountType;

    @OneToMany(mappedBy = "accountSummary", fetch = FetchType.LAZY)
    private List<TopFiveFunds> topFiveFunds;

    @OneToMany(mappedBy = "accountSummary", fetch = FetchType.LAZY)
    private List<MonthwiseDetails> monthwiseDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountSummary", fetch = FetchType.LAZY)
    private AccountSummaryCustomFields accountSummaryCustomFields;

    private String fcuScore;
}

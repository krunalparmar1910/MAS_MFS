package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = DbConst.SCHEMA_NAME)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class MonthwiseDetails extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_summary_id")
    private AccountSummary accountSummary;

    private LocalDate month;

    private LocalDate startDate;


    private Long totalNoOfCreditTransactions;

    private BigDecimal totalAmountOfCreditTransactions;

    private Long totalNoOfDebitTransactions;

    private BigDecimal totalAmountOfDebitTransactions;

    private Long totalNoOfCashDeposits;

    private BigDecimal totalAmountOfCashDeposits;

    private Long totalNoOfCashWithdrawals;

    private BigDecimal totalAmountOfCashWithdrawals;

    private Long totalNoOfChequeDeposits;

    private BigDecimal totalAmountOfChequeDeposits;

    private Long totalNoOfChequeIssues;

    private BigDecimal totalAmountOfChequeIssues;

    private Long totalNoOfInwardChequeBounces;

    private Long totalNoOfOutwardChequeBounces;

    private BigDecimal minEodBalance;

    private BigDecimal maxEodBalance;

    private BigDecimal avgEodBalance;

    private BigDecimal lastBalance;

    private BigDecimal atmWithdrawals;


    private Long transactionsCount;

    private Long totalNoOfSalaries;

    private BigDecimal totalAmountOfSalaries;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "monthwiseDetail", fetch = FetchType.LAZY)
    private MonthwiseCustomFields monthWiseCustomField;

    @Enumerated(EnumType.STRING)
    private DetailType type;
}

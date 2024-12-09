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
public class AccountSummaryCustomFields extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_summary_id")
    private AccountSummary accountSummary;
    // Update for custom Fields (BankStatement)

    private BigDecimal customCreditLoanReceipts;
    private BigDecimal customDebitLoanReceipts;


//    @Column(name = "total_credit_loan_receipts")
//    private BigDecimal customCreditLoanReceipts;
//
//    @Column(name = "total_debit_loan_receipts")
//    private BigDecimal customDebitLoanReceipts;
//
//    @Column(name = "total_credit_inter_bank")
//    private BigDecimal totalCreditInterBank;
//
//    @Column(name = "total_debit_inter_bank")
//    private BigDecimal totalDebitInterBank;
//
//    @Column(name = "total_credit_inter_firm")
//    private BigDecimal totalCreditInterFirm;
//
//    @Column(name = "total_debit_inter_firm")
//    private BigDecimal totalDebitInterFirm;
//
//    @Column(name = "total_credit_non_business_transaction")
//    private BigDecimal totalCreditNonBusinessTransaction;
//
//    @Column(name = "total_debit_non_business_transaction")
//    private BigDecimal totalDebitNonBusinessTransaction;
}

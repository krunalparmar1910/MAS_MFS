package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class StatementAccounts extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statement_considered_id")
    private StatementsConsidered statementsConsidered;

    private String accountNumber;

    private String accountType;

    private LocalDate startTransactionDate;

    private LocalDate endTransactionDate;
}

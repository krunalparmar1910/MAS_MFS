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
import jakarta.persistence.Lob;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Transactions extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_summary_id")
    private AccountSummary accountSummary;

    private LocalDate date;

    private Long chequeNo;

    @Lob
    private String description;

    private BigDecimal amount;

    @Lob
    private String category;

    private BigDecimal balance;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transactions", fetch = FetchType.EAGER)
    private TransactionsCustomField transactionsCustomField;

    public String getComment(){
        return transactionsCustomField != null ? transactionsCustomField.getComment() : null;
    }

    public interface Amount{
        BigDecimal getAmount();
    }
}

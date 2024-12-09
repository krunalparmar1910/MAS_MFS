package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
public class MasterRule extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "master_rule_transaction_type_identifiers",
            schema = DbConst.SCHEMA_NAME,
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_type_id")
    )
    private List<MasterIdentifiers> transactionTypeList;

    @ManyToMany
    @JoinTable(
            name = "master_rule_category_identifiers",
            schema = DbConst.SCHEMA_NAME,
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<MasterIdentifiers> categoryList;

    @ManyToMany
    @JoinTable(
            name = "master_rule_parties_merchant_identifiers",
            schema = DbConst.SCHEMA_NAME,
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "parties_merchant_id")
    )
    private List<MasterIdentifiers> partiesMerchantList;
    @Lob
    private String identificationValue;

    @Enumerated(EnumType.STRING)
    private DebitCredit debitOrCredit;

    @Column(unique = true)
    private String transactionFlag;

    @Lob
    private String ruleQuery;

    private boolean deletable;

    private boolean completed;
}

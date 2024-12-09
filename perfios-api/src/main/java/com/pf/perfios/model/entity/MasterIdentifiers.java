package com.pf.perfios.model.entity;

import com.pf.perfios.model.entity.base.BaseAuditableEntity;
import com.pf.perfios.utils.DbConst;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = DbConst.SCHEMA_NAME,
        uniqueConstraints=
        @UniqueConstraint(columnNames={"identifier_name", "identifier_type"})
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class MasterIdentifiers extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private IdentifierType identifierType;

    private String identifierName;

    @Lob
    private String identificationValue;

    @Enumerated(EnumType.STRING)
    private DebitCredit debitOrCredit;

    private boolean deletable;
}

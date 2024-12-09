package com.pf.perfios.model.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseUUIDEntity implements BaseEntity {
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "uuid")
    private final UUID uuid;

    protected BaseUUIDEntity() {
        super();
        this.uuid = UUID.randomUUID();
    }
}

package com.pf.mas.model.entity.base;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@AllArgsConstructor
public abstract class BaseUUID implements Serializable {

    @JdbcTypeCode(SqlTypes.CHAR)
    @Id
    private UUID id;

    public BaseUUID() {
        this.id = UUID.randomUUID();
    }

}

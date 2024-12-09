package com.pf.mas.model.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseAuditableEntity extends BaseUUIDEntity {
    @CreatedDate
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdTimestamp;

    @LastModifiedDate
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant modifiedTimestamp;

    protected BaseAuditableEntity() {
        super();
    }
}

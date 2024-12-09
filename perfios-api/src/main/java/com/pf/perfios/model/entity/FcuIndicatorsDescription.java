package com.pf.perfios.model.entity;

import com.pf.perfios.utils.DbConst;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = DbConst.SCHEMA_NAME)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcuIndicatorsDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    private String name;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private IndicatorType indicatorType;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private IndicatorSubType indicatorSubType;
}

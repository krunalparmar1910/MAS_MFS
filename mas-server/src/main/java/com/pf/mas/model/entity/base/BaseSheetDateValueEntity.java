package com.pf.mas.model.entity.base;

import com.pf.mas.model.entity.FieldDateMonthYear;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseSheetDateValueEntity implements BaseEntity {
    @ManyToOne
    @JoinColumn(name = "field_date_month_year_id")
    private FieldDateMonthYear fieldDateMonthYear;
}

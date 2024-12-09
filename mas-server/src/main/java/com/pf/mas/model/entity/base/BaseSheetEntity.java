package com.pf.mas.model.entity.base;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
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
public abstract class BaseSheetEntity implements BaseEntity {
    @ManyToOne
    @JoinColumn(name = "field_group_id")
    private FieldGroup fieldGroup;

    @ManyToOne
    @JoinColumn(name = "field_date_month_year_id")
    private FieldDateMonthYear fieldDateMonthYear;

    @ManyToOne
    @JoinColumn(name = "client_order_ref_id")
    private ClientOrder clientOrder;
}

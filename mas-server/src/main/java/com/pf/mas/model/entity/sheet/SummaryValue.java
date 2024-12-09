package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetValueEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "summary_values", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SummaryValue extends BaseSheetValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quarter")
    private String quarter;

    @ManyToOne
    @JoinColumn(name = "summary_id")
    private Summary summary;
}

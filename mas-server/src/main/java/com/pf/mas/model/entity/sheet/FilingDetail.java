package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.base.BaseSheetEntity;
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

import java.time.LocalDate;

@Entity
@Table(name = "filing_detail", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FilingDetail extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "financial_year_id")
    private FieldDateMonthYear financialYear;

    @ManyToOne
    @JoinColumn(name = "tax_period_id")
    private FieldDateMonthYear taxPeriod;

    @Column(name = "return_type")
    private String returnType;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "date_of_filing")
    private LocalDate dateOfFiling;

    @Column(name = "delayed_days")
    private Integer delayedDays;
}

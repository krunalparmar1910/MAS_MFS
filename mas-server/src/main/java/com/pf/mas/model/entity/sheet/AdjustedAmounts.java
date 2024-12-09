package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "adjusted_amounts", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AdjustedAmounts extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @OneToMany(mappedBy = "adjustedAmounts")
    private List<AdjustedAmountsValue> values;

    public static void setValueForFieldHeader(String fieldHeader, AdjustedAmounts adjustedAmounts, String value) {
        String fieldHeaderUpper = fieldHeader.trim().toUpperCase(Locale.ROOT);
        if ("PARTICULARS".equals(fieldHeaderUpper)) {
            adjustedAmounts.setParticulars(value);
        }
    }
}

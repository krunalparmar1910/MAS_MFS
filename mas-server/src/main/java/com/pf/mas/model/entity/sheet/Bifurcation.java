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
@Table(name = "bifurcation", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Bifurcation extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @OneToMany(mappedBy = "bifurcation")
    private List<BifurcationValue> values;

    public static void setValueForFieldHeader(String fieldHeader, Bifurcation bifurcation, String value) {
        String fieldHeaderUpper = fieldHeader.trim().toUpperCase(Locale.ROOT);
        if ("PARTICULARS".equals(fieldHeaderUpper)) {
            bifurcation.setParticulars(value);
        }
    }
}

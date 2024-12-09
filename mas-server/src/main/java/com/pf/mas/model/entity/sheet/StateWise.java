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
@Table(name = "state_wise", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StateWise extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "state_code")
    private String stateCode;

    @OneToMany(mappedBy = "stateWise")
    private List<StateWiseValue> values;

    public static void setValueForFieldHeader(String fieldHeader, StateWise stateWise, String value) {
        String fieldHeaderUpper = fieldHeader.trim().toUpperCase(Locale.ROOT);
        if ("PARTICULARS".equals(fieldHeaderUpper)) {
            stateWise.setParticulars(value);
        } else if ("STATE CODE".equals(fieldHeaderUpper)) {
            stateWise.setStateCode(value);
        }
    }
}

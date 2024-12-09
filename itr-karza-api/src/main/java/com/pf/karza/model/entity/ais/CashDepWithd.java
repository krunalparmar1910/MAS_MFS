package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ais_data_analysis_cash_dep_with_d", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashDepWithd extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "ais_analysis_id")
    @JsonBackReference
    private AisAnalysis aisAnalysis;

    @Column(name = "cash_withd")
    private String cashWithd;

    @Column(name = "cash_dep")
    private String cashDep;

    @Column(name = "change_dep")
    private String changeDep;

    @Column(name = "change_withd")
    private String changeWithd;

    @Column(name = "change_withd_percent")
    @JsonProperty("changeWithd%")
    private String changeWithdPercent;

    @Column(name = "change_dep_percent")
    @JsonProperty("changeDep%")
    private String changeDepPercent;
}

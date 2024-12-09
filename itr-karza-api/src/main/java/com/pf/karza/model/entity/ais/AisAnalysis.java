package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "ais_data_analysis", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AisAnalysis extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "ais_data_id", referencedColumnName = "id")
    @JsonBackReference
    private AisData aisData;

    @OneToOne(mappedBy = "aisAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CashDepWithd cashDepWithd;

    @OneToOne(mappedBy = "aisAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CashFlowInvestAct cashFlowInvestAct;

    private String year;
}

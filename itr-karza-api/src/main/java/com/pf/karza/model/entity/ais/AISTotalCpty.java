package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ais_data_total_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AISTotalCpty extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "ais_total_id")
    @JsonBackReference
    private AisTotal aisTotal;

    @Column(name = "info_cat")
    private String infoCat;

    @Column(name = "info_cat_code")
    private String infoCatCode;

    @Column(name = "info_code")
    private String infoCode;

    @Column(name = "info_desc")
    private String infoDesc;

    @Column(name = "info_src")
    private String infoSrc;

    @Column(name = "info_src_id")
    private String infoSrcId;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "amount_reported")
    private String amountReported;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "info_code_seq_no")
    private String infoCodeSeqNo;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "amt_desc")
    private String amtDesc;

    @Column(name = "part")
    private String part;

    @Column(name = "duplicate_flag")
    private String duplicateFlag;
}

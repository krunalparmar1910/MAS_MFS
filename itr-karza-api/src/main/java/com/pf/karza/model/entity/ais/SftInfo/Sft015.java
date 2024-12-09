package com.pf.karza.model.entity.ais.SftInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.cpty.Cpty015;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ais_data_details_sft_info_sft_015", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sft015 extends BaseId {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "sft_info_id", referencedColumnName = "id")
    private SftInfo sftInfo;

    @Column(name = "info_cat")
    private String infoCat;

    @Column(name = "info_cat_code")
    private String infoCatCode;

    @Column(name = "info_code")
    private String infoCode;

    @Column(name = "info_code_seq_no")
    private String infoCodeSeqNo;

    @Column(name = "info_src")
    private String infoSrc;

    @Column(name = "info_src_id")
    private String infoSrcId;

    @Column(name = "info_desc")
    private String infoDesc;

    @Column(name = "amt_desc")
    private String amtDesc;

    @Column(name = "amount_reported")
    private String amountReported;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "duplicate_flag")
    private String duplicateFlag;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "info_count")
    private String infoCount;

    @Column(name = "amount_updated")
    private String amountUpdated;

    @JsonManagedReference
    @OneToMany(mappedBy = "sft015", cascade = CascadeType.ALL)
    @JsonProperty("cpty")
    private List<Cpty015> cpty015List;
}

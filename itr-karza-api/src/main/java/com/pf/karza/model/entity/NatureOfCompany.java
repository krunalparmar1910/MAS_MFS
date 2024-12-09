package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nature_of_company", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NatureOfCompany extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "nat_of_comp_flg")
    @JsonProperty("natOfCompFlg")
    private String natOfCompFlg;

    @Column(name = "non_bank_fii_comp_flg")
    @JsonProperty("nonBankFIICompFlg")
    private String nonBankFiiCompFlg;

    @Column(name = "unlisted")
    private String unlisted;

    @Column(name = "sched_bank_of_rbi_act_flg")
    @JsonProperty("schedBankOfRBIActFlg")
    private String schedBankOfRbiActFlg;

    @Column(name = "comp_with_irda_register_flg")
    @JsonProperty("compWithIRDARegisterFlg")
    private String compWithIrdaRegisterFlg;

    @Column(name = "comp_les_40_perc_shar_gov_rbi_flg")
    @JsonProperty("compLes40PercSharGovRBIFlg")
    private String compLes40PercSharGovRbiFlg;

    @Column(name = "pub_sect_comp_us_236_a_flg")
    @JsonProperty("pubSectCompUs236AFlg")
    private String pubSectCompUs236AFlg;

    @Column(name = "rbi_comp_flg")
    @JsonProperty("rbiCompFlg")
    private String rbiCompFlg;

    @Column(name = "bank_comp_us_5_flg")
    @JsonProperty("bankCompUs5Flg")
    private String bankCompUs5Flg;
}

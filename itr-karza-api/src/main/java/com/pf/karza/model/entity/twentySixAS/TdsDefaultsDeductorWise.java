package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "26as_data_tds_defaults_tds_defaults_deductor_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsDefaultsDeductorWise extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tds_defaults_id")
    private TdsDefaults tdsDefaults;

    @Column(name = "int_tds_pay_def")
    private String intTDSPayDef;

    @Column(name = "shrt_pmt")
    private String shrtPmt;

    @Column(name = "ttl_def")
    private String ttlDef;

    @Column(name = "int_tds_ded_def")
    private String intTDSDedDef;

    @Column(name = "shrt_ded")
    private String shrtDed;

    @Column(name = "interest")
    private String interest;

    @Column(name = "tan")
    private String tan;

    @Column(name = "lt_filing_fee")
    private String ltFilingFee;
}
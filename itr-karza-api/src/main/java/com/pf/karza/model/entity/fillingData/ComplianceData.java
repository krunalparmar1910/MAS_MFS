package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "filling_data_compilance_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceData extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "filling_data_id")
    @JsonBackReference
    private FillingData fillingData;

    @Column(name = "delay")
    private Boolean delay;

    @Column(name = "is_default")
    @JsonProperty("default")
    private Boolean isDefault;

    @Column(name = "revised")
    private Boolean revised;

    @Column(name = "late_fee")
    private Boolean lateFee;

    @Column(name = "intrst_fee")
    private Boolean intrstFee;

    @Column(name = "demand_notice")
    private Boolean demandNotice;

    @Column(name = "high_val_transaction")
    private Boolean highValTransaction;
}

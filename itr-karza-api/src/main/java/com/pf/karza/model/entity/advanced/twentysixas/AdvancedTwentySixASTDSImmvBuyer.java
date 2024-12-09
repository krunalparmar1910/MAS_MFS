package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "advanced_twenty_six_as_tds_immv_buyer", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTDSImmvBuyer extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_immv_id")
    @JsonBackReference
    private AdvancedTwentySixASTdsImmv advancedTwentySixASTdsImmv;

    @Column(name = "ttl_demd_paymnt")
    private BigDecimal ttlDemdPaymnt;

    @Column(name = "ttl_tds_deposited")
    private BigDecimal ttlTdsDeposited;

    @Column(name = "ttl_amt_oth_tds")
    private BigDecimal ttlAmtOthTDS;

    @JsonProperty("records")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTDSImmvBuyer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTDSImmvBuyerRecord> advancedTwentySixASTDSImmvBuyerRecordList;
}

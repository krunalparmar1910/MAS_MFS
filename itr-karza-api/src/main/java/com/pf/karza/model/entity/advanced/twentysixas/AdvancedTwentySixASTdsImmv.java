package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advanced_twenty_six_as_tds_immv", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTdsImmv extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_data_entry_id")
    @JsonBackReference
    private AdvancedTwentySixASDataEntry advancedTwentySixASDataEntry;

    @JsonProperty("buyerOrTenantPartF")
    @JsonManagedReference
    @OneToOne(mappedBy = "advancedTwentySixASTdsImmv", orphanRemoval = true, cascade = CascadeType.ALL)
    private AdvancedTwentySixASTDSImmvBuyer advancedTwentySixASTDSImmvBuyer;

    @JsonProperty("sellerOrLandlordPartA2")
    @JsonManagedReference
    @OneToOne(mappedBy = "advancedTwentySixASTdsImmv", orphanRemoval = true, cascade = CascadeType.ALL)
    private AdvancedTwentySixASTDSImmvSeller advancedTwentySixASTDSImmvSeller;
}
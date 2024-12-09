package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "advanced_twenty_six_as_tds_immv_buyer_record", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTDSImmvBuyerRecord extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_immv_buyer_id")
    @JsonBackReference
    private AdvancedTwentySixASTDSImmvBuyer advancedTwentySixASTDSImmvBuyer;

    @JsonProperty("summary")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTDSImmvBuyerRecord", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTDSImmvBuyerSummaryRecord> advancedTwentySixASTDSImmvBuyerSummaryRecords;

    @JsonProperty("deducteeWise")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTDSImmvBuyerRecord", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTDSImmvBuyerDeducteeWiseRecord> advancedTwentySixASTDSImmvBuyerDeducteeWiseRecords;
}

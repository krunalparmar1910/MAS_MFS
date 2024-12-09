package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advanced_twenty_six_as_tds_15g_15h_part_a1_deductor_wise", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTds15g15hPartA1DeductorWise extends AdvancedTwentySixASTdsPartDeductorWise {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_15g_15h_part_a1_id")
    @JsonBackReference
    private AdvancedTwentySixASTds15g15hPartA1 advancedTwentySixASTds15g15hPartA1;
}

package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import jakarta.persistence.Column;
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
@Table(name = "advanced_twenty_six_as_tds_defaults_part_g_summary", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTdsDefaultsPartGSummary extends AdvancedTwentySixASTdsDefaultsPartGRecord {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_defaults_part_g_id")
    @JsonBackReference
    private AdvancedTwentySixASTdsDefaultsPartG advancedTwentySixASTdsDefaultsPartG;

    @Column(name = "fin_year")
    private String finYear;
}

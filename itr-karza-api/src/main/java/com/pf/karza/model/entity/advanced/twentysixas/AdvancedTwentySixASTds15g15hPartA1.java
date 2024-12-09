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
@Table(name = "advanced_twenty_six_as_tds_15g_15h_part_a1", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTds15g15hPartA1 extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_dtls_id")
    @JsonBackReference
    private AdvancedTwentySixASTdsDtls advancedTwentySixASTdsDtls;

    @JsonProperty("summary")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTds15g15hPartA1", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTds15g15hPartA1Summary> advancedTwentySixASTds15g15hPartA1Summaries;

    @JsonProperty("deductorWise")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTds15g15hPartA1", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTds15g15hPartA1DeductorWise> advancedTwentySixASTds15g15hPartA1DeductorWises;
}

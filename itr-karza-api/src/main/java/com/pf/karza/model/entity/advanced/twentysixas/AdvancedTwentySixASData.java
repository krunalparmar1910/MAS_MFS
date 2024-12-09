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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advanced_twenty_six_as_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASData extends BaseId {
    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "status_of_26_as_data")
    private String statusOf26asData;

    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_id")
    @JsonBackReference
    private AdvancedTwentySixAS advancedTwentySixAS;

    @JsonProperty("data")
    @JsonManagedReference
    @OneToOne(mappedBy = "advancedTwentySixASData", orphanRemoval = true, cascade = CascadeType.ALL)
    private AdvancedTwentySixASDataEntry advancedTwentySixASDataEntry;
}

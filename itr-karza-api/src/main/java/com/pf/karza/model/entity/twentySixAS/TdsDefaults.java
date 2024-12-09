package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "26as_data_tds_defaults", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsDefaults extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "twenty_six_as_data_id")
    private TwentySixASData twentySixASData;

    @JsonManagedReference
    @OneToMany(mappedBy = "tdsDefaults", cascade = CascadeType.ALL)
    private List<TdsDefaultsDeductorWise> deductorWise;

    @JsonManagedReference
    @OneToMany(mappedBy = "tdsDefaults", cascade = CascadeType.ALL)
    private List<TdsDefaultsSummary> summary;

}

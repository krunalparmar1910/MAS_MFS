package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "26as_data_tds_details_tds15g15h", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tds15g15h extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tds_details_id")
    private TdsDetails tdsDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "tds15g15h", cascade = CascadeType.ALL)
    @JsonProperty("summary")
    private List<Tds15g15hSummary> tds15g15hSummaryList;

    @JsonManagedReference
    @OneToMany(mappedBy = "tds15g15h", cascade = CascadeType.ALL)
    @JsonProperty("deductorWise")
    private List<Tds15g15hDeductorWise> tds15g15hDeductorWiseList;

    // Include other fields and relationships here as needed
}

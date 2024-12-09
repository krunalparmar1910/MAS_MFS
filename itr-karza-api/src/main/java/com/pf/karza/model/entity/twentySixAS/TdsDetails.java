package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "26as_data_tds_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsDetails extends BaseId {

    @OneToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "26as_data_id")
    private TwentySixASData twentySixASData;

    @JsonManagedReference
    @OneToMany(mappedBy = "tdsDetails", cascade = CascadeType.ALL)
    private List<Tds> tds;

    @JsonManagedReference
    @OneToMany(mappedBy = "tdsDetails", cascade = CascadeType.ALL)
    private List<Tds15g15h> tds15g15h;

    // TODO not present in documentation yet but available in response
    @JsonManagedReference
    @OneToMany(mappedBy = "tdsDetails", cascade = CascadeType.ALL)
    private List<Tds194b194r194s> tds194b194r194s;
}

package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_director_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDetails extends BaseId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "filling_data_id")
    @JsonBackReference
    private FillingData fillingData;


    @Column(name = "din")
    private String din;

    @Column(name = "pan")
    private String pan;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "type_of_entity")
    private String typeOfEntity;

    @Column(name = "shares_listed_or_unlisted")
    private String sharesListedOrUnlisted;
}

package com.pf.karza.model.entity;

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
@Table(name = "outstanding_demand", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutstandingDemand extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "rectification_rights")
    private String rectificationRights;

    @Column(name = "date_of_service")
    private String dateOfService;

    @Column(name = "din")
    private String din;

    @Column(name = "date_of_demand_raised")
    private String dateOfDemandRaised;

    @Column(name = "section_code")
    private String sectionCode;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "outstanding_demand_amount")
    private String outstandingDemandAmount;

    @Column(name = "mode_of_service")
    private String modeOfService;

    @Column(name = "uploaded_by")
    private String uploadedBy;
}
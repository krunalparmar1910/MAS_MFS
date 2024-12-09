package com.pf.karza.model.entity.advanced.outstandingdemand;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "advanced_outstanding_demand_entry", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedOutstandingDemandEntry extends BaseId {
    @Column(name = "assessment_year")
    private Integer assessmentYear;

    @Column(name = "section_code")
    private String sectionCode;

    @Column(name = "din")
    private String din;

    @Column(name = "date_of_demand_raised")
    private LocalDate dateOfDemandRaised;

    @Column(name = "date_of_service")
    private LocalDate dateOfService;

    @Column(name = "mode_of_service")
    private String modeOfService;

    @Column(name = "outstanding_demand_amount")
    private BigDecimal outstandingDemandAmount;

    @Column(name = "original_outstanding_demand_amount")
    private BigDecimal originalOutstandingDemandAmount;

    @Column(name = "accrued_interest")
    private BigDecimal accruedInterest;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "rectification_rights")
    private String rectificationRights;

    @Column(name = "response_type")
    private String responseType;

    @Column(name = "response_reason")
    private String responseReason;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "ao_response_reason")
    private String aoResponseReason;

    @Column(name = "ao_response_date")
    private LocalDate aoResponseDate;

    @Column(name = "current_status_date")
    private LocalDate currentStatusDate;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "interest_us")
    private String interestUs;

    @Column(name = "pan")
    private String pan;

    @Column(name = "pan_name")
    private String panName;

    @Column(name = "tracking_ref_no")
    private String trackingRefNo;

    @Column(name = "interest_start_date")
    private LocalDate interestStartDate;

    @Column(name = "rate_of_interest")
    private BigDecimal rateOfInterest;

    @Column(name = "final_interest")
    private BigDecimal finalInterest;

    @Column(name = "interest_section_code")
    private String interestSectionCode;

    @Column(name = "response_submitted")
    private String responseSubmitted;

    @Column(name = "major_head")
    private String majorHead;

    @Column(name = "interest_cal_date")
    private LocalDate interestCalDate;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "demand_intimation_details")
    private String demandIntimationDetails;

    @Column(name = "schedule_type")
    private String scheduleType;

    @Column(name = "time_period")
    private Long timePeriod;

    @Column(name = "date_of_delivery_by_email")
    private LocalDate dateOfDeliveryByEmail;

    @Column(name = "date_of_dispatch_by_post")
    private LocalDate dateOfDispatchByPost;

    @Column(name = "date_of_delivery_by_post")
    private LocalDate dateOfDeliveryByPost;

    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_outstanding_demand_id")
    private AdvancedOutstandingDemand advancedOutstandingDemand;
}

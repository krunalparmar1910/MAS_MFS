package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "application_data", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicationData extends BaseID {

    @Column(name = "user_name")
    private String user;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "dtt_trail_id")
    private Step dTTrail;
    private String start;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Step milestone;
    private Long amount;
    private Long businessUnitId;
    private String inputValReasonCodes;
    private String returnMessage;
    private String purpose;
    private Long environmentTypeId;
    private Boolean cibilBureauFlag;
    private String gstStateCode;
    private Long solutionSetId;
    private String environmentType;
    private String scoreType;
    private Long applicationId;
    private Boolean formattedReport;
    private String memberCode;
    private String password;


    private String callerApplicationId;
    private String mfiBranchReferenceNo;
    private String mfiCenterReferenceNo;
    private Long mfiLoanPurpose;
    private Long mfiEnquiryAmount;
    private boolean mfiBureauFlag;
    private boolean idVerificationFlag;
    private boolean dstuNtcFlag;
    private String consumerConsentForUidaiAuthentication;
    private String ntcProductType;
    private String mfiDocRequest;
    private Boolean caastUefVersionEmail;
    private String version;
}

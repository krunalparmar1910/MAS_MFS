package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "applicant_dscb", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Applicant extends BaseID {
    private String dateOfBirth;
    private String applicantFirstName;
    private String applicantLastName;
    private String applicantMiddleName;
    private String emailAddress;
    private String gender;
    private String applicantType;
    private Long nodeIdentifier;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_dscb_id")
    private List<ApplicantTelephone> telephoneList;

    private String dsAadharEkycOtp;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_dscb_id")
    private List<ApplicantAddressDSCB> applicantAddressDSCBList;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_dscb_id")
    private List<IdentifierApplicant> identifierApplicantList;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ds_cibil_bureau_data_id")
    private DsCibilBureauData dsCibilBureauData;
}

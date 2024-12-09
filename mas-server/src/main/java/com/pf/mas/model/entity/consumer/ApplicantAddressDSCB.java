package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applicant_address_dscb", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicantAddressDSCB extends BaseID {
    private Long residenceType;
    private String stateCode;
    private String city;
    private String pinCode;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressType;


    private String accountNumber;
    @Column(name = "member_other_id4_type")
    private String memberOtherId4Type;
    private String memberOtherId4;

    @Column(name = "member_other_id3_type")
    private String memberOtherId3Type;
    private String memberOtherId3;

    @Column(name = "member_other_id2_type")
    private String memberOtherId2Type;
    private String memberOtherId2;

    @Column(name = "member_other_id1_type")
    private String memberOtherId1Type;
    private String memberOtherId1;
    private String nomineeRelation;
    private String nomineeName;
    private String memberRelationType4;
    private String memberRelationName4;
    private String memberRelationType3;
    private String memberRelationName3;
    private String memberRelationType2;
    private String memberRelationName2;
    private String memberRelationType1;
    private String memberRelationName1;
    private String keyPersonName;
    private String keyPersonRelation;
    private String companyName;
    private String emailAddress;
    private String gender;
    private String dateOfBirth;
    private String applicantLastName;
    private String applicantMiddleName;
    private String applicantFirstName;
    private String applicantType;
    private String nodeIdentifier;
    
}

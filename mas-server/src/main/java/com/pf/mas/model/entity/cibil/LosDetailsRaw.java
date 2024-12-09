package com.pf.mas.model.entity.cibil;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseUUID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "los_details_raw", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LosDetailsRaw extends BaseUUID {

    private String name;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfBirth;
    private String gender;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "los_details_raw_id")
    private List<LosIdentificationRaw> losIdentificationRawList;
    @ElementCollection
    @CollectionTable(name = "los_details_raw_telephone_number", schema = Constants.CIBIL_DB_SCHEMA, joinColumns = @JoinColumn(name = "los_details_raw_id", nullable=false))
    @Column(name = "telephone_number")
    private List<Long> telephoneNumbers;
    @ElementCollection
    @CollectionTable(name = "los_details_raw_email", schema = Constants.CIBIL_DB_SCHEMA, joinColumns = @JoinColumn(name = "los_details_raw_id", nullable=false))
    @Column(name = "email")
    private List<String> emails;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "los_details_raw_id")
    private List<LosAddressInfoRaw> losAddressInfoRawList;
    private String requestId;
}

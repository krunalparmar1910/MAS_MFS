package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credit_report_inquiry", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditReportInquiry extends BaseID {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_report_inquiry_id")
    private List<NameCRI> nameCRIList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_report_inquiry_id")
    private List<AddressCRI> addressCRIList;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "header_cri_id")
    private HeaderCRI headerCRI;

    @ElementCollection
    @CollectionTable(name = "credit_report_inquiry_telephones", schema = Constants.CIBIL_DB_SCHEMA, joinColumns = @JoinColumn(name = "credit_report_inquiry_id", nullable=false))
    @Column(name = "telephone")
    private List<String> telephones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_report_inquiry_id")
    private List<IdentificationCRI> identificationCRIList;
}

package com.pf.karza.model.entity.itrfilled;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "itr_filled", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItrFilled extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "pan")
    private String pan;

    @Column(name = "status")
    private String status;

    @Column(name = "filling_date")
    private String fillingDate;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "annual_year")
    private String annualYear;

    @Column(name = "filing_type")
    private String filingType;

    @Column(name = "itr_form")
    private String itrForm;

    @Lob
    @Column(name = "other_files_download_link")
    private String otherFilesDownloadLink;

    @OneToMany(mappedBy = "itrFilled", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("activity")
    @JsonManagedReference
    private List<Activity> activityList;
}
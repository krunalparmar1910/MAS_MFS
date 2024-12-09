package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import com.pf.karza.model.entity.ubo.FilingHistoryDownloadsStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_history", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FillingHistory extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "pan")
    private String pan;

    @Column(name = "assessment_year")
    @JsonProperty("A.Y.")
    private String assessmentYear;

    @Column(name = "form")
    private String form;

    @Column(name = "filing_type")
    private String filingType;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private String date;

    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "itr_form")
    private String itrForm;

    @Lob
    @Column(name = "other_files_download_link")
    private String otherFilesDownloadLink;

    @Column(name = "activity_status")
    private String activityStatus;

    @Column(name = "activity_date")
    private String activityDate;

    @Lob
    @Column(name = "activity_download_link")
    private String activityDownloadLink;

    @OneToOne(mappedBy = "fillingHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private FilingHistoryDownloadsStatus downloadsStatus;
}

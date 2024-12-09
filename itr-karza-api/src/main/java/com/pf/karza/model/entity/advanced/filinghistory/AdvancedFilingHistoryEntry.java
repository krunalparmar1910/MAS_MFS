package com.pf.karza.model.entity.advanced.filinghistory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "advanced_filing_history_entry", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedFilingHistoryEntry extends BaseId {
    @Column(name = "ack_no")
    private String ackNo;

    @Column(name = "filing_type")
    private String filingType;

    @Column(name = "itr_form")
    private String itrForm;

    @Column(name = "pan")
    private String pan;

    @Column(name = "status")
    private String status;

    @Column(name = "status_date")
    private LocalDate statusDate;

    @Column(name = "filed_by")
    private String filedBy;

    @Column(name = "filing_section")
    private String filingSection;

    @JsonProperty("fillingDate")
    @Column(name = "filing_date")
    private LocalDate filingDate;

    @Column(name = "assmnt_yr")
    private String assmntYr;

    @Lob
    @Column(name = "itr_forms_xml_download_link")
    private String itrFormsXmlDownloadLink;

    @Lob
    @Column(name = "oth_files_download_link")
    private String othFilesDownloadLink;

    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_filing_history_id")
    private AdvancedFilingHistory advancedFilingHistory;

    @JsonProperty("activity")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedFilingHistoryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdvancedFilingHistoryEntryActivity> advancedFilingHistoryEntryActivities;

    @JsonProperty("downloadLinks")
    private void setDownloadLinks(Map<String, String> downloadLinksMap) {
        if (downloadLinksMap != null) {
            itrFormsXmlDownloadLink = downloadLinksMap.get("itrFormsXml");
            othFilesDownloadLink = downloadLinksMap.get("othFiles");
        }
    }
}

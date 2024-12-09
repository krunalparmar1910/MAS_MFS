package com.pf.karza.model.entity.advanced.filinghistory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.time.LocalDate;

@Entity
@Table(name = "advanced_filing_history_entry_activity", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedFilingHistoryEntryActivity extends BaseId {
    @Column(name = "status")
    private String status;

    @JsonProperty("date")
    @Column(name = "activity_date")
    private LocalDate activityDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_filing_history_entry_id")
    @JsonBackReference
    private AdvancedFilingHistoryEntry advancedFilingHistoryEntry;
}

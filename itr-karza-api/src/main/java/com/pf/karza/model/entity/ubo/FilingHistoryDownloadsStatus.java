package com.pf.karza.model.entity.ubo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.FillingHistory;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "filling_history_downloads_status", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilingHistoryDownloadsStatus extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "filling_history_id")
    @JsonBackReference
    private FillingHistory fillingHistory;

    @Lob
    @Column(name = "download_link")
    private String downloadLink;
}
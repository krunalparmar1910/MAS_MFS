package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import com.pf.karza.model.entity.itrfilled.Activity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "itr_filled_activity_downloads_status", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadsStatus extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "activity_id")
    @JsonBackReference
    private Activity activity;

    @Lob
    @Column(name = "download_link")
    private String downloadLink;
}
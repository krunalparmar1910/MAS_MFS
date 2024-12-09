package com.pf.karza.model.entity.itrfilled;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.DownloadsStatus;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itr_filled_activity", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "itr_filled_id")
    @JsonBackReference
    private ItrFilled itrFilled;

    private String status;

    private String date;

    @OneToOne(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private DownloadsStatus downloadsStatus;

}
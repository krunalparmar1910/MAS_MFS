package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "combined_response", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CombinedResponse extends BaseID {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_data_id")
    private ApplicationData applicationData;
    private String decision;
    private String applicationId;
}

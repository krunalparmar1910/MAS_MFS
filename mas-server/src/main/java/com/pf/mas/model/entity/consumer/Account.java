package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseID {
    private Long length;
    private String segmentTag;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_summary_segment_id")
    private AccountSummarySegment accountSummarySegment;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_non_summary_segment_id")
    private AccountNonSummarySegment accountNonSummarySegment;
    private String comment;
    private boolean duplicate;
    private boolean addInTotal;

}

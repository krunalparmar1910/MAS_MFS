package com.pf.karza.model.entity.fillingData;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "filling_data_share_holding_pattern_opening_balance", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningBalance extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "share_holding_pattern_id")
    private ShareHoldingPattern shareHoldingPattern;

    private String noShares;

    private String costAcq;
}

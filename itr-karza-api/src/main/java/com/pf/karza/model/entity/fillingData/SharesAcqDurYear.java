package com.pf.karza.model.entity.fillingData;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_share_holding_pattern_shares_acq_dur_year", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharesAcqDurYear extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "share_holding_pattern_id")
    private ShareHoldingPattern shareHoldingPattern;

    @Column(name = "no_shares")
    private String noShares;

    @Column(name = "date_of_purch")
    private String dateOfPurch;

    @Column(name = "face_val_per_sh")
    private String faceValPerSh;

    @Column(name = "issue_price_per_sh")
    private String issuePricePerSh;

    @Column(name = "purch_price_per_sh")
    private String purchPricePerSh;
}

package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filling_data_share_holding_pattern", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareHoldingPattern extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "filling_data_id")
    private FillingData fillingData;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "pan")
    private String pan;

    @Column(name = "company_typ")
    private String companyTyp;

    @Column(name = "shareholder_name")
    private String shareholderName;

    @Column(name = "share_percentage")
    private String sharePercentage;

    @OneToOne(mappedBy = "shareHoldingPattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private OpeningBalance openingBalance;

    @OneToOne(mappedBy = "shareHoldingPattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private SharesAcqDurYear sharesAcqDurYear;

    @OneToOne(mappedBy = "shareHoldingPattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private SharesTransfDurYear sharesTransfDurYear;

    @OneToOne(mappedBy = "shareHoldingPattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClosingBalance closingBalance;

}

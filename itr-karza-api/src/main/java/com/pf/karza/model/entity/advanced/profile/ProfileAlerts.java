package com.pf.karza.model.entity.advanced.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile_alerts", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileAlerts extends BaseId {
    @Column(name = "under_liquidation")
    private String underLiquidation;

    @OneToOne
    @JoinColumn(nullable = false, name = "profile_id")
    @JsonBackReference
    private Profile profile;
}

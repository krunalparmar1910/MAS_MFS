package com.pf.karza.model.entity.advanced.twentysixas;

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
@Table(name = "advanced_twenty_six_as_profile", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASProfile extends BaseId {
    @Column(name = "pan")
    private String pan;

    @Column(name = "status")
    private String status;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_id")
    @JsonBackReference
    private AdvancedTwentySixAS advancedTwentySixAS;
}

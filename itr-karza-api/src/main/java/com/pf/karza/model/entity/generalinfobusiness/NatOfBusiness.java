package com.pf.karza.model.entity.generalinfobusiness;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_info_nat_of_business", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NatOfBusiness extends BaseId {
    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false, name = "general_info_id")
    private GeneralInfo generalInfo;

    @Column(name = "code")
    private String code;

    @Column(name = "trade_name_1")
    private String tradeName1;

    @Column(name = "title")
    private String title;
}
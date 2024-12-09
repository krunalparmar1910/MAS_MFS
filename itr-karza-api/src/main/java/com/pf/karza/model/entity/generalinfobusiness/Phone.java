package com.pf.karza.model.entity.generalinfobusiness;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_info_phone", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Phone extends BaseId {
    @JsonBackReference
    @OneToOne
    @JoinColumn(nullable = false, name = "general_info_id")
    private GeneralInfo generalInfo;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "std_code")
    private String stdCode;
}

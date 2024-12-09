package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "advanced_twenty_six_as_tds_194b_194r_194s", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTds194b194r194s extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_dtls_id")
    @JsonBackReference
    private AdvancedTwentySixASTdsDtls advancedTwentySixASTdsDtls;
}

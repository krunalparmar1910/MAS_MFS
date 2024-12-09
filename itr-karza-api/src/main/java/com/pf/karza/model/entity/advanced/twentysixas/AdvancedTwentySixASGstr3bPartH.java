package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.constant.ModelConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "advanced_twenty_six_as_gstr3b_part_h", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASGstr3bPartH extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_data_entry_id")
    @JsonBackReference
    private AdvancedTwentySixASDataEntry advancedTwentySixASDataEntry;

    @Column(name = "sr_no")
    private Integer srNo;

    @Column(name = "gstin")
    private String gstin;

    @Column(name = "arn")
    private String arn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MMM_YYYY_FORMAT)
    @Column(name = "dof")
    private LocalDate dof;

    @Column(name = "ret_prd")
    private String retPrd;

    @Column(name = "txbl_trnovr")
    private BigDecimal txblTrnovr;

    @Column(name = "ttl_trnovr")
    private BigDecimal ttlTrnovr;
}

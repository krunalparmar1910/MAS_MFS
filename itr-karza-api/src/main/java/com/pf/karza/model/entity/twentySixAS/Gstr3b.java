package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "26as_data_gstr3b", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gstr3b extends BaseId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "26as_data_id")
    private TwentySixASData twentySixASData;

    @Column(name = "sr_no")
    private String srNo;

    @Column(name = "ret_prd")
    private String retPrd;

    @Column(name = "dof")
    private String dof;

    @Column(name = "txbl_trnovr")
    private String txblTrnovr;

    @Column(name = "gstin")
    private String gstin;

    @Column(name = "ttl_trnovr")
    private String ttlTrnovr;

    @Column(name = "arn")
    private String arn;

    // Include other fields and relationships here as needed
}

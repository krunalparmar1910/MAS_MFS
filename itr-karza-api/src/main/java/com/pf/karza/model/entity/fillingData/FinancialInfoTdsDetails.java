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
@Table(name = "filling_data_financial_info_tds_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfoTdsDetails extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "financial_info_id")
    @JsonBackReference
    private FinancialInfo financialInfo;

    @Column(name = "tan")
    private String tan;

    @Column(name = "name_of_employer")
    private String nameOfEmployer;

    @Column(name = "salary")
    private String salary;

    @Column(name = "tax_deducted")
    private String taxDeducted;

}

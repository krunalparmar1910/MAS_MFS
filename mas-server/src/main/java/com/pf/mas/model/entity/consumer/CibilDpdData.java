package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cibil_dpd_data", schema = Constants.CIBIL_DB_SCHEMA)
public class CibilDpdData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private String reportId;

    @Column(name = "dpd0_all_accounts_ind")
    private Long dpd0AllAccountsInd;

    @Column(name = "dpd1to30_all_accounts_ind")
    private Long dpd1to30AllAccountsInd;

    @Column(name = "dpd31to90_all_accounts_ind")
    private Long dpd31to90AllAccountsInd;

    @Column(name = "dpd_greater_than90_all_accounts_ind")
    private Long dpdGreaterThan90AllAccountsInd;

    @Column(name = "dpd0_live_accounts_ind")
    private Long dpd0LiveAccountsInd;

    @Column(name = "dpd1to30_live_accounts_ind")
    private Long dpd1to30LiveAccountsInd;

    @Column(name = "dpd31to90_live_accounts_ind")
    private Long dpd31to90LiveAccountsInd;

    @Column(name = "dpd_greater_than90_live_accounts_ind")
    private Long dpdGreaterThan90LiveAccountsInd;

    @Column(name = "dpd0_closed_accounts_ind")
    private Long dpd0ClosedAccountsInd;

    @Column(name = "dpd1to30_closed_accounts_ind")
    private Long dpd1to30ClosedAccountsInd;

    @Column(name = "dpd31to90_closed_accounts_ind")
    private Long dpd31to90ClosedAccountsInd;

    @Column(name = "dpd_greater_than90_closed_accounts_ind")
    private Long dpdGreaterThan90ClosedAccountsInd;

    @Column(name = "dpd0_all_accounts_joint")
    private Long dpd0AllAccountsJoint;

    @Column(name = "dpd1to30_all_accounts_joint")
    private Long dpd1to30AllAccountsJoint;

    @Column(name = "dpd31to90_all_accounts_joint")
    private Long dpd31to90AllAccountsJoint;

    @Column(name = "dpd_greater_than90_all_accounts_joint")
    private Long dpdGreaterThan90AllAccountsJoint;

    @Column(name = "dpd0_live_accounts_joint")
    private Long dpd0LiveAccountsJoint;

    @Column(name = "dpd1to30_live_accounts_joint")
    private Long dpd1to30LiveAccountsJoint;

    @Column(name = "dpd31to90_live_accounts_joint")
    private Long dpd31to90LiveAccountsJoint;

    @Column(name = "dpd_greater_than90_live_accounts_joint")
    private Long dpdGreaterThan90LiveAccountsJoint;

    @Column(name = "dpd0_closed_accounts_joint")
    private Long dpd0ClosedAccountsJoint;

    @Column(name = "dpd1to30_closed_accounts_joint")
    private Long dpd1to30ClosedAccountsJoint;

    @Column(name = "dpd31to90_closed_accounts_joint")
    private Long dpd31to90ClosedAccountsJoint;

    @Column(name = "dpd_greater_than90_closed_accounts_joint")
    private Long dpdGreaterThan90ClosedAccountsJoint;

    @Column(name = "dpd0_all_accounts_guarantor")
    private Long dpd0AllAccountsGuarantor;

    @Column(name = "dpd1to30_all_accounts_guarantor")
    private Long dpd1to30AllAccountsGuarantor;

    @Column(name = "dpd31to90_all_accounts_guarantor")
    private Long dpd31to90AllAccountsGuarantor;

    @Column(name = "dpd_greater_than90_all_accounts_guarantor")
    private Long dpdGreaterThan90AllAccountsGuarantor;

    @Column(name = "dpd0_live_accounts_guarantor")
    private Long dpd0LiveAccountsGuarantor;

    @Column(name = "dpd1to30_live_accounts_guarantor")
    private Long dpd1to30LiveAccountsGuarantor;

    @Column(name = "dpd31to90_live_accounts_guarantor")
    private Long dpd31to90LiveAccountsGuarantor;

    @Column(name = "dpd_greater_than90_live_accounts_guarantor")
    private Long dpdGreaterThan90LiveAccountsGuarantor;

    @Column(name = "dpd0_closed_accounts_guarantor")
    private Long dpd0ClosedAccountsGuarantor;

    @Column(name = "dpd1to30_closed_accounts_guarantor")
    private Long dpd1to30ClosedAccountsGuarantor;

    @Column(name = "dpd31to90_closed_accounts_guarantor")
    private Long dpd31to90ClosedAccountsGuarantor;

    @Column(name = "dpd_greater_than90_closed_accounts_guarantor")
    private Long dpdGreaterThan90ClosedAccountsGuarantor;
}

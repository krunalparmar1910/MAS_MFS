package com.pf.karza.model.entity;

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
@Table(name = "audit_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditInfo extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "audit_report_section")
    private String auditReportSection;

    @Column(name = "audit_report_act")
    private String auditReportAct;

    @Column(name = "audit_accountant_flg")
    private String auditAccountantFlg;

    @Column(name = "audited_section")
    private String auditedSection;

    @Column(name = "liable_sec44AAflg")
    private String liableSec44AAflg;

    @Column(name = "auditor_name")
    private String auditorName;

    @Column(name = "aud_frm_pan")
    private String audFrmPAN;

    @Column(name = "audit_date")
    private String auditDate;

    @Column(name = "aud_frm_name")
    private String audFrmName;

    @Column(name = "audit_report_furnish_date")
    private String auditReportFurnishDate;

    @Column(name = "auditor_mem_no")
    private String auditorMemNo;

    @Column(name = "liable_sec44ABflg")
    private String liableSec44ABflg;

    @Column(name = "liable_sec92Eflg")
    private String liableSec92Eflg;

    @Column(name = "aud_frm_reg_no")
    private String audFrmRegNo;
}
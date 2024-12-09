package com.pf.mas.model.entity;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "client_order_report_details", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class ClientOrderReportDetails implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "report_file_name")
    private String reportFileName;

    @Column(name = "report_company_name")
    @EqualsAndHashCode.Include
    private String reportCompanyName;

    @Column(name = "report_details_string")
    private String reportDetailsString;

    @Column(name = "report_pan")
    private String reportPan;

    @Column(name = "report_gstn")
    private String reportGstn;

    @Column(name = "period_covered_from")
    private LocalDate periodCoveredFrom;

    @Column(name = "period_covered_to")
    private LocalDate periodCoveredTo;

    @OneToOne
    @JoinColumn(name = "client_order_ref_id")
    private ClientOrder clientOrder;
}

package com.pf.mas.model.entity;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseAuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "client_order", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class ClientOrder extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "client_order_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private String clientOrderId;

    @Column(name = "entity_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private String entityId;

    @Column(name = "report_status")
    private String reportStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "ip_address")
    private String ipAddress;

    @OneToOne(mappedBy = "clientOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClientOrderReportDetails clientOrderReportDetails;

    public enum ClientOrderReportStatus {
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        ERROR("Error");

        private final String status;

        ClientOrderReportStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    }
}

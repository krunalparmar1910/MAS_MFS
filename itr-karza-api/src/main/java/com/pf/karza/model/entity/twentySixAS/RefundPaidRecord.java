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
@Table(name = "26as_data_refund_paid_refund_paid_record", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundPaidRecord extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "refund_paid_id")
    private RefundPaid refundPaid;

    @Column(name = "nat_refnd")
    private String natRefnd;

    @Column(name = "date_of_pay")
    private String dateOfPay;

    @Column(name = "refnd_issd")
    private String refndIssd;

    @Column(name = "interest")
    private String interest;

    @Column(name = "year")
    private String year;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "amt_refnd")
    private String amtRefnd;

    @Column(name = "mode")
    private String mode;
}

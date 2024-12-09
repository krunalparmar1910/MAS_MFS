package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "26as_data_refund_paid", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundPaid extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "twenty_six_as_data_id")
    private TwentySixASData twentySixASData;

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

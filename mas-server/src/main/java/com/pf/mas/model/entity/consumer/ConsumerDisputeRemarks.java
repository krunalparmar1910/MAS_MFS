package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consumer_dispute_remarks", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ConsumerDisputeRemarks extends BaseID {
	private String length;
	private String segmentTag;
	private String dateOfEntry;
	private String consumerDisputeRemarks1;
	private String consumerDisputeRemarks2;
	private String consumerDisputeRemarks3;
	private String consumerDisputeRemarks4;
	private String consumerDisputeRemarks5;
	private String consumerDisputeRemarks6;
}
package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cheque_dishonoured_due_to_insufficient_funds", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ChequeDishounouredDuetoInsufficientFunds extends BaseID {
	private String message;
	@Column(name = "cd_3_month_count")
	private String cd3monthCount;
	@Column(name = "cd_4to6_month_count")
	private String cd4to6monthCount;
	@Column(name = "cd_7to9_month_count")
	private String cd7to9monthCount;
	@Column(name = "cd_10to12_month_count")
	private String cd10to12monthCount;
}
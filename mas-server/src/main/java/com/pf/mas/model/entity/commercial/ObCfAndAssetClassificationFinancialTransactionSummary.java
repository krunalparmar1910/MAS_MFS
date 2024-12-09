package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ob_cf_and_asset_classification_financial_transaction_summary", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ObCfAndAssetClassificationFinancialTransactionSummary extends BaseID {
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "non_funded_id")
	private ObCfAndAssetClassificationTransactionTypeDetails nonFunded;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "working_capital_id")
	private ObCfAndAssetClassificationTransactionTypeDetails workingCapital;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "term_loan_id")
	private ObCfAndAssetClassificationTransactionTypeDetails termLoan;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "forex_id")
	private ObCfAndAssetClassificationTransactionTypeDetails forex;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "total_id")
	private ObCfAndAssetClassificationTransactionTypeDetails total;
}
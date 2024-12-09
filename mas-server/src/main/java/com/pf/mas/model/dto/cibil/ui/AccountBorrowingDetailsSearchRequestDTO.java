package com.pf.mas.model.dto.cibil.ui;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.DoubleRange;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccountBorrowingDetailsSearchRequestDTO {
    private String typeOfLoan;
    private Double sanctionAmountLowerRange;
    private Double sanctionAmountUpperRange;
    private Double outStandingAmountLowerRange;
    private Double outStandingAmountUpperRange;
    private Date sanctionedDateLowerRange;
    private Date sanctionedDateUpperRange;
    private String status;
    private String BankNbfc;
    private String ownerShip;
    private Double overdueLowerRange;
    private Double overdueUpperRange;
    private Double tenureLowerRange;
    private Double tenureUpperRange;
    private Date reportedDateLowerRange;
    private Date reportedDateUpperRange;
    private Double emiLowerRange;
    private Double emiUpperRange;
    private Double interestRateLowerRange;
    private Double interestRateUpperRange;
    private Long last12MonthsDpdLowerRange;
    private Long last12MonthsDpdUpperRange;
    private Long overallDpdLowerRange;
    private Long overallDpdUpperRange;
}

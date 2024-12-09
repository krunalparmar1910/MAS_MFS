package com.pf.perfios.model.entity;

import java.util.HashMap;
import java.util.Map;

public enum IndicatorSubType {
//ALL FCUs
    //PFI
    SuspiciousBankEStatements("suspiciousBankEStatements"),
    AccountHolderNameVerification("AccountHolderNameVerification"),

    IrregularInterestDebitXns("irregularInterestDebitXns"),
    SalaryAmountNotVaryingXns("salaryAmountNotVaryingXns"),
    AmountBalanceMismatchXns("amountBalanceMismatchXns"),
    CashDepositOnHolidayXns("cashDepositOnHolidayXns"),
    SuspiciousRTGSXns("suspiciousRTGSXns"),
    SuspiciousATMXns("suspiciousATMXns"),
    NegativeEODBalanceXns("negativeEODBalanceXns"),
    ChqDepositOnHolidayXns("chqDepositOnHolidayXns"),
    CreditInterestNotReflecting("creditInterestNotReflecting"),
    ChqWithdrawalOnHolidayXns("chqWithdrawalOnHolidayXns"),
    ImpsAboveThresholdXns("impsAboveThresholdXns"),
    MajorityRoundFigureXns("majorityRoundFigureXns"),
    UpiAboveThresholdXns("upiAboveThresholdXns"),
    CashWithdrawalOnHolidayXns("cashWithdrawalOnHolidayXns"),


    //BTI
    LowAccountVintage("lowAccountVintage"),
    MoreCashXns("moreCashXns"),
    EqualCreditDebitXns("equalCreditDebitXns"),
    TaxPaymentXns("taxPaymentXns"),
    ChequeNumberInSeriesXns("chequeNumberInSeriesXns"),
    CashWithdrawalAfterCreditXns("cashWithdrawalAfterCreditXns"),
    ConsecutiveEMIChequeBounces("consecutiveEMIChequeBounces"),
    IrregularEmiEcsXns("irregularEmiEcsXns"),
    OnlyAtmDebitsInAnyOfLastThreeMonths("onlyAtmDebitsInAnyOfLastThreeMonths"),
    ATMWithChqNoXns("aTMWithChqNoXns"),
    HugeBalances("hugeBalances"),
    LowXns("lowXns"),
    NoXnMonths("noXnMonths"),
    CashWithdrawalAfterCashDepositXns("cashWithdrawalAfterCashDepositXns"),
    InterestCreditXns("interestCreditXns"),
    DebitEODBalanceXns("debitEODBalanceXns"),
    SalaryWithSameNarrationXns("salaryWithSameNarrationXns"),
    SalaryThroughIMPSOrMPAYOrCHQ("salaryThroughIMPSOrMPAYOrCHQ"),
    SalaryThroughIMPSOrUPI("salaryThroughIMPSOrUPI"),
    SalaryOnHolidayXns("salaryOnHolidayXns"),
    SuspiciousSalaryDebitXns("SuspiciousSalaryDebitXns"),
    MoreCashDepositXns("moreCashDepositXns"),
    NeftImpsRtgsAgainstSalaryXns("neftImpsRtgsAgainstSalaryXns"),
    BigDebitAfterSalaryXns("bigDebitAfterSalaryXns"),
    IrregularSalaryCreditXns("irregularSalaryCreditXns"),
    HighCashDepositXns("highCashDepositXns"),
    DebitTransactionToEmployer("debitTransactionToEmployer"),
    IrregularCreditTransactions("irregularCreditTransactions"),
    NeftRtgsGreaterThanAMB("neftRtgsGreaterThanAMB"),
    CashDepositGreaterThanAMB("cashDepositGreaterThanAMB"),
    PartiesInCreditsAndDebits("partiesInCreditsAndDebits"),
    PartiesInCreditsAndDebitsWhenCreditGreaterThanAMB("partiesInCreditsAndDebitsWhenCreditGreaterThanAMB"),
    IrregularTransferToPartiesXns("irregularTransferToPartiesXns"),
    DiscontinuityInCredits("discontinuityInCredits"),
    RotationOfMoney("rotationOfMoney"),
    NeftImpsWithChqNoXns("neftImpsWithChqNoXns"),
    DebitInterestNotReflecting("debitInterestNotReflecting"),
    CreditEODBalanceXns("creditEODBalanceXns"),
    UnusualODCCActivity("unusualODCCActivity"),
    NoPosXnMonths("noPosXnMonths"),
    RoundedSalaryTransactionXns("roundedSalaryTransactionXns"),
    SuspiciousGamingXns("suspiciousGamingXns");

    private static final Map<String, IndicatorSubType> KEY_MAP = new HashMap<>();

    static {
        for (IndicatorSubType subtype : IndicatorSubType.values()) {
            KEY_MAP.put(subtype.key, subtype);
        }
    }


    private final String key;

    // private enum constructor
    IndicatorSubType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static IndicatorSubType fromKey(String key) {
        IndicatorSubType subtype = KEY_MAP.get(key);
        if (subtype == null) {
            throw new IllegalArgumentException("No enum constant with key: " + key);
        }
        return subtype;
    }
}
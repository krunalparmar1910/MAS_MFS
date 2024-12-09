package com.pf.perfios.utils;

import java.util.Set;

import static com.pf.perfios.model.entity.IndicatorSubType.ATMWithChqNoXns;
import static com.pf.perfios.model.entity.IndicatorSubType.AccountHolderNameVerification;
import static com.pf.perfios.model.entity.IndicatorSubType.AmountBalanceMismatchXns;
import static com.pf.perfios.model.entity.IndicatorSubType.BigDebitAfterSalaryXns;
import static com.pf.perfios.model.entity.IndicatorSubType.CashDepositGreaterThanAMB;
import static com.pf.perfios.model.entity.IndicatorSubType.CashDepositOnHolidayXns;
import static com.pf.perfios.model.entity.IndicatorSubType.CashWithdrawalAfterCashDepositXns;
import static com.pf.perfios.model.entity.IndicatorSubType.CashWithdrawalAfterCreditXns;
import static com.pf.perfios.model.entity.IndicatorSubType.CashWithdrawalOnHolidayXns;
import static com.pf.perfios.model.entity.IndicatorSubType.ChequeNumberInSeriesXns;
import static com.pf.perfios.model.entity.IndicatorSubType.ChqDepositOnHolidayXns;
import static com.pf.perfios.model.entity.IndicatorSubType.ChqWithdrawalOnHolidayXns;
import static com.pf.perfios.model.entity.IndicatorSubType.ConsecutiveEMIChequeBounces;
import static com.pf.perfios.model.entity.IndicatorSubType.CreditEODBalanceXns;
import static com.pf.perfios.model.entity.IndicatorSubType.CreditInterestNotReflecting;
import static com.pf.perfios.model.entity.IndicatorSubType.DebitEODBalanceXns;
import static com.pf.perfios.model.entity.IndicatorSubType.DebitInterestNotReflecting;
import static com.pf.perfios.model.entity.IndicatorSubType.DebitTransactionToEmployer;
import static com.pf.perfios.model.entity.IndicatorSubType.DiscontinuityInCredits;
import static com.pf.perfios.model.entity.IndicatorSubType.EqualCreditDebitXns;
import static com.pf.perfios.model.entity.IndicatorSubType.HighCashDepositXns;
import static com.pf.perfios.model.entity.IndicatorSubType.HugeBalances;
import static com.pf.perfios.model.entity.IndicatorSubType.ImpsAboveThresholdXns;
import static com.pf.perfios.model.entity.IndicatorSubType.InterestCreditXns;
import static com.pf.perfios.model.entity.IndicatorSubType.IrregularCreditTransactions;
import static com.pf.perfios.model.entity.IndicatorSubType.IrregularEmiEcsXns;
import static com.pf.perfios.model.entity.IndicatorSubType.IrregularInterestDebitXns;
import static com.pf.perfios.model.entity.IndicatorSubType.IrregularSalaryCreditXns;
import static com.pf.perfios.model.entity.IndicatorSubType.IrregularTransferToPartiesXns;
import static com.pf.perfios.model.entity.IndicatorSubType.LowAccountVintage;
import static com.pf.perfios.model.entity.IndicatorSubType.LowXns;
import static com.pf.perfios.model.entity.IndicatorSubType.MajorityRoundFigureXns;
import static com.pf.perfios.model.entity.IndicatorSubType.MoreCashDepositXns;
import static com.pf.perfios.model.entity.IndicatorSubType.MoreCashXns;
import static com.pf.perfios.model.entity.IndicatorSubType.NeftImpsRtgsAgainstSalaryXns;
import static com.pf.perfios.model.entity.IndicatorSubType.NeftImpsWithChqNoXns;
import static com.pf.perfios.model.entity.IndicatorSubType.NeftRtgsGreaterThanAMB;
import static com.pf.perfios.model.entity.IndicatorSubType.NegativeEODBalanceXns;
import static com.pf.perfios.model.entity.IndicatorSubType.NoPosXnMonths;
import static com.pf.perfios.model.entity.IndicatorSubType.NoXnMonths;
import static com.pf.perfios.model.entity.IndicatorSubType.OnlyAtmDebitsInAnyOfLastThreeMonths;
import static com.pf.perfios.model.entity.IndicatorSubType.PartiesInCreditsAndDebits;
import static com.pf.perfios.model.entity.IndicatorSubType.PartiesInCreditsAndDebitsWhenCreditGreaterThanAMB;
import static com.pf.perfios.model.entity.IndicatorSubType.RotationOfMoney;
import static com.pf.perfios.model.entity.IndicatorSubType.RoundedSalaryTransactionXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SalaryAmountNotVaryingXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SalaryOnHolidayXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SalaryThroughIMPSOrMPAYOrCHQ;
import static com.pf.perfios.model.entity.IndicatorSubType.SalaryThroughIMPSOrUPI;
import static com.pf.perfios.model.entity.IndicatorSubType.SalaryWithSameNarrationXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SuspiciousATMXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SuspiciousBankEStatements;
import static com.pf.perfios.model.entity.IndicatorSubType.SuspiciousGamingXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SuspiciousRTGSXns;
import static com.pf.perfios.model.entity.IndicatorSubType.SuspiciousSalaryDebitXns;
import static com.pf.perfios.model.entity.IndicatorSubType.TaxPaymentXns;
import static com.pf.perfios.model.entity.IndicatorSubType.UnusualODCCActivity;
import static com.pf.perfios.model.entity.IndicatorSubType.UpiAboveThresholdXns;

public final class FcuConst {

    public static final Set<String> INDICATOR_TYPE_OBJECT = Set.of(
            //PFI
            SuspiciousBankEStatements.getKey(), AccountHolderNameVerification.getKey(), CreditInterestNotReflecting.getKey(),
            //BTI
            LowAccountVintage.getKey(), MoreCashXns.getKey(), EqualCreditDebitXns.getKey(), ConsecutiveEMIChequeBounces.getKey(),
            OnlyAtmDebitsInAnyOfLastThreeMonths.getKey(), HugeBalances.getKey(), DebitTransactionToEmployer.getKey(),
            IrregularCreditTransactions.getKey(), DiscontinuityInCredits.getKey(), RotationOfMoney.getKey(),
            DebitInterestNotReflecting.getKey(), UnusualODCCActivity.getKey()
    );
    public static final Set<String> INDICATOR_TYPE_LIST = Set.of(
            //BTI
            LowXns.getKey(), NoXnMonths.getKey(), PartiesInCreditsAndDebits.getKey(), NoPosXnMonths.getKey()

    );
    public static final Set<String> INDICATOR_TYPE_GROUP_LIST = Set.of(
            //PFI
            IrregularInterestDebitXns.getKey(), SalaryAmountNotVaryingXns.getKey(), AmountBalanceMismatchXns.getKey(), CashDepositOnHolidayXns.getKey(),
            SuspiciousRTGSXns.getKey(), SuspiciousATMXns.getKey(), NegativeEODBalanceXns.getKey(), ChqDepositOnHolidayXns.getKey(),
            ChqWithdrawalOnHolidayXns.getKey(), ImpsAboveThresholdXns.getKey(), MajorityRoundFigureXns.getKey(),
            UpiAboveThresholdXns.getKey(), CashWithdrawalOnHolidayXns.getKey(),
            //BTI
            TaxPaymentXns.getKey(), ChequeNumberInSeriesXns.getKey(), CashWithdrawalAfterCreditXns.getKey(), IrregularEmiEcsXns.getKey(),
            ATMWithChqNoXns.getKey(), CashWithdrawalAfterCashDepositXns.getKey(), InterestCreditXns.getKey(),
            DebitEODBalanceXns.getKey(), SalaryWithSameNarrationXns.getKey(), SalaryThroughIMPSOrMPAYOrCHQ.getKey(), SalaryThroughIMPSOrUPI.getKey(),
            SalaryOnHolidayXns.getKey(), SuspiciousSalaryDebitXns.getKey(), MoreCashDepositXns.getKey(), NeftImpsRtgsAgainstSalaryXns.getKey(),
            BigDebitAfterSalaryXns.getKey(), IrregularSalaryCreditXns.getKey(), HighCashDepositXns.getKey(), NeftRtgsGreaterThanAMB.getKey(),
            CashDepositGreaterThanAMB.getKey(), PartiesInCreditsAndDebitsWhenCreditGreaterThanAMB.getKey(), IrregularTransferToPartiesXns.getKey(),
            NeftImpsWithChqNoXns.getKey(), CreditEODBalanceXns.getKey(), RoundedSalaryTransactionXns.getKey(), SuspiciousGamingXns.getKey()
    );


    private FcuConst() {
        //do nothing
    }
}

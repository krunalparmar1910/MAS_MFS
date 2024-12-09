package com.pf.perfios.service;

import com.pf.perfios.exception.MasEntityNotFoundException;
import com.pf.perfios.exception.MasPerfiosException;
import com.pf.perfios.model.dto.*;
import com.pf.perfios.model.entity.*;
import com.pf.perfios.repository.*;
import com.pf.perfios.utils.FcuConst;
import com.pf.perfios.utils.PerfiosUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveReportService {
    private static final Set<String> I_W_RETURN_KEYWORDS = Set.of(
            "Insufficient Funds", "Exceed Arrangement", "Exceeds Arrangement", "Exceed Arrangements", "Exceeds Arrangements", "I/W Return");
    private static final String PENAL_CHARGES = "Penal Charges";
    private final AccountSummaryRepository accountSummaryRepository;
    private final CustomerInfoRepository customerInfoRepository;
    private final StatementsConsideredRepository statementsConsideredRepository;
    private final MonthwiseDetailsRepository monthwiseDetailsRepository;
    private final TopFiveFundsRepository topFiveFundsRepository;
    private final TransactionsRepository transactionsRepository;
    private final EodBalancesRepository eodBalancesRepository;
    private final FcuIndicatorsRepository fcuIndicatorsRepository;
    private final FcuIndicatorsDetailsRepository fcuIndicatorsDetailsRepository;
    private final FcuIndicatorsDescriptionRepository fcuIndicatorsDescriptionRepository;
    private final CombinedMonthlyDetailsRepository combinedMonthlyDetailsRepository;
    private final StatementAccountsRepository statementAccountsRepository;
    private final MonthwiseCustomFieldsRepository monthwiseCustomFieldsRepository;
    private final AccountSummaryCustomFieldsRepository accountSummaryCustomFieldsRepository;
    private final DerivedTransactionsRepository derivedTransactionsRepository;
    private final MasterRuleService masterRuleService;
    private final MasRequestsRepository masRequestsRepository;
    private final TransactionsCustomFieldRepository transactionsCustomFieldRepository;
    private final CustomTotalFieldsRepository customTotalFieldsRepository;

    public static Specification<Transactions> withOptionalFilters(TransactionReqDTO request, List<AccountSummary> accountSummaryList) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Start with always true predicate

            if(DebitCredit.CREDIT == request.getDebitOrCredit()) {
                predicate = criteriaBuilder.and(predicate,
                         criteriaBuilder.ge(root.get("amount"), BigDecimal.ZERO)
                );
            } else if(DebitCredit.DEBIT == request.getDebitOrCredit()){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lt(root.get("amount"), BigDecimal.ZERO)
                );
            } //else if both then no condition

            predicate = criteriaBuilder.and(predicate, root.get("accountSummary").in(accountSummaryList));


            if (request.getFromDate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("date"), request.getFromDate()));
            }

            if (request.getToDate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getToDate()));
            }

            // debit
            Predicate debitFromPredicate;
            Predicate debitToPredicate;
            Predicate debitPredicate;

            if (request.getFromDebit() != null ) {
                debitFromPredicate = criteriaBuilder.le(root.get("amount"), -request.getFromDebit());
            } else {
                debitFromPredicate = criteriaBuilder.conjunction();
            }

            if (request.getToDebit() != null) {
                debitToPredicate =  criteriaBuilder.ge(root.get("amount"), -request.getToDebit());
            } else {
                debitToPredicate = criteriaBuilder.conjunction();
            }

            debitPredicate = criteriaBuilder.and(debitFromPredicate, debitToPredicate);

            // credit
            Predicate creditFromPredicate;
            Predicate creditToPredicate;
            Predicate creditPredicate;

            if (request.getFromCredit() != null ) {
                creditFromPredicate = criteriaBuilder.ge(root.get("amount"), request.getFromCredit());
            } else {
                creditFromPredicate = criteriaBuilder.conjunction();
            }

            if (request.getToCredit() != null) {
                creditToPredicate =  criteriaBuilder.le(root.get("amount"), request.getToCredit());
            } else {
                creditToPredicate = criteriaBuilder.conjunction();
            }

            creditPredicate = criteriaBuilder.and(creditFromPredicate, creditToPredicate);

            if (DebitCredit.CREDIT == request.getDebitOrCredit()) {
                predicate = criteriaBuilder.and(predicate, creditPredicate);
            } else if (DebitCredit.DEBIT == request.getDebitOrCredit()) {
                predicate = criteriaBuilder.and(predicate, debitPredicate);
            } else {
                if (request.getFromCredit() != null && request.getFromDebit() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(creditPredicate, debitPredicate));
                } else if (request.getFromCredit() != null) {
                    predicate = criteriaBuilder.and(predicate, creditPredicate);
                } else if (request.getFromDebit() != null) {
                    predicate = criteriaBuilder.and(predicate, debitPredicate);
                }
            }

            // balance
            Predicate balanceFromPredicate;
            Predicate balanceToPredicate;
            Predicate balancePredicate;

            if (request.getFromBalance() != null) {
                balanceFromPredicate = criteriaBuilder.ge(root.get("balance"), request.getFromBalance());
            } else {
                balanceFromPredicate = criteriaBuilder.conjunction();
            }

            if (request.getToBalance() != null) {
                balanceToPredicate = criteriaBuilder.ge(root.get("balance"), request.getToBalance());
            } else {
                balanceToPredicate = criteriaBuilder.conjunction();
            }

            balancePredicate = criteriaBuilder.and(balanceFromPredicate, balanceToPredicate);
            predicate = criteriaBuilder.and(predicate, balancePredicate);

            if (request.getFromChqNo() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("chequeNo"), request.getFromChqNo()));
            }

            if (request.getToChqNo() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("chequeNo"), request.getToChqNo()));
            }

            if (StringUtils.isNotEmpty(request.getSearchText())) {
                predicate = criteriaBuilder.and(predicate, getSearchCriteriaPredicates(request.getSearchText(), criteriaBuilder, root));
            }

            if (StringUtils.isNotEmpty(request.getDescription())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + request.getDescription() + "%"));
            }

            if (StringUtils.isNotEmpty(request.getCategory())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("category"), "%" + request.getCategory() + "%"));
            }

            if (StringUtils.isNotEmpty(request.getComment())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("transactionsCustomField").get("comment"), "%" + request.getComment() + "%"));
            }

            return predicate;
        };
    }

    private static Predicate getSearchCriteriaPredicates(String searchText, CriteriaBuilder criteriaBuilder, Root<Transactions> root) {
        return criteriaBuilder.or(criteriaBuilder.like(root.get("description"), "%" + searchText + "%"),
                criteriaBuilder.like(root.get("category"), "%" + searchText + "%"));
    }

    @Transactional
    public void saveJSON(JSONObject data, MasRequests masRequests) {

        JSONObject customerData = data.optJSONObject("customerInfo");

        if(customerData == null){
            throw new MasPerfiosException("customerInfo object is null in json report unable to save report with masfinancial id: "+masRequests.getMasFinancialId());
        }

        String perfiosTransactionId = customerData.optString("perfiosTransactionId");
        CustomerInfo existingCustomer = customerInfoRepository.findByPerfiosTransactionId(perfiosTransactionId);
        if(existingCustomer != null){
            log.error("Duplicate request found to save in the database with perfiosTransactionId: "+perfiosTransactionId);
            return;
        }

        CustomerInfo customerInfo =
                CustomerInfo.builder()
                        .bankName(customerData.optString("bank"))
                        .address(customerData.optString("address"))
                        .name(customerData.optString("name"))
                        .mobileNumber(customerData.optString("mobile"))
                        .landline(customerData.optString("landline"))
                        .customerTransactionId(customerData.optString("customerTransactionId"))
                        .pan(customerData.optString("pan"))
                        .email(customerData.optString("email"))
                        .perfiosTransactionId(perfiosTransactionId)
                        .masFinancialId(masRequests.getMasFinancialId())
                        .customerTransactionId(customerData.optString("customerTransactionId"))
                        .instId(customerData.optLong("instId"))
                        .status(Status.Pending)
                        .build();

        customerInfoRepository.save(customerInfo);

        //statementDetails

        JSONArray statementDetails = data.optJSONArray("statementdetails");

        if(statementDetails == null){
            throw new MasPerfiosException("customerInfo object is null in json report unable to save report with masfinancial id: "+masRequests.getMasFinancialId());
        }

        for (int index = 0; index < statementDetails.length(); index++) {
            JSONObject statementDetail = statementDetails.optJSONObject(index);
            JSONObject customerDetail = statementDetail.optJSONObject("customerInfo");
            JSONArray statementAccounts = statementDetail.optJSONArray("statementAccounts");
            if(customerDetail != null && statementAccounts != null) {
                StatementsConsidered statementsConsidered =
                        StatementsConsidered.builder()
                                .customerInfo(customerInfo)
                                .fileName(statementDetail.optString("fileName"))
                                .statementStatus(statementDetail.optString("statementStatus"))
                                .nameInStatement(customerDetail.optString("name"))
                                .address(customerDetail.optString("address"))
                                .landline(customerDetail.optString("landline"))
                                .mobile(customerDetail.optString("mobile"))
                                .email(customerDetail.optString("email"))
                                .panNo(customerDetail.optString("pan"))
                                .institution(customerDetail.optString("bank"))
                                .build();

                statementsConsideredRepository.save(statementsConsidered);

                saveStatementAccounts(statementsConsidered, statementAccounts);
            }

        }

        saveAccountSummary(customerInfo, data);

        saveTransactions(customerInfo, data);

        saveCombinedMonthlyDetails(customerInfo, data);

        customerInfo.setStatus(Status.Completed);
        customerInfoRepository.save(customerInfo);

        masRequests.setCustomerInfo(customerInfo);
        masRequestsRepository.save(masRequests);

    }

    private void saveStatementAccounts(StatementsConsidered statementsConsidered, JSONArray statementAccounts) {

        for (int index = 0; index < statementAccounts.length(); index++) {
            JSONObject account = statementAccounts.getJSONObject(index);
            StatementAccounts statementAccount =
                    StatementAccounts.builder()
                            .statementsConsidered(statementsConsidered)
                            .accountNumber(account.optString("accountNo"))
                            .accountType(account.optString("accountType"))
                            .startTransactionDate(PerfiosUtils.parseLocalDate(account.optString("xnsStartDate")))
                            .endTransactionDate(PerfiosUtils.parseLocalDate(account.optString("xnsEndDate")))
                            .build();

            statementAccountsRepository.save(statementAccount);
        }
    }

    private void saveCombinedMonthlyDetails(CustomerInfo customerInfo, JSONObject data) {
        JSONArray monthlyDetails = data.optJSONArray("combinedMonthlyDetails");

        if(monthlyDetails != null) {
            for (int index = 0; index < monthlyDetails.length(); index++) {
                JSONObject monthlyDetail = monthlyDetails.getJSONObject(index);
                CombinedMonthlyDetails combinedMonthlyDetails =
                        CombinedMonthlyDetails.builder()
                                .customerInfo(customerInfo)
                                .monthName(monthlyDetail.optString("monthName"))
                                .startDate(PerfiosUtils.parseLocalDate(monthlyDetail.optString("startDate")))
                                .build();

                combinedMonthlyDetailsRepository.save(combinedMonthlyDetails);
            }
        }
    }

    private void saveTransactions(CustomerInfo customerInfo, JSONObject data) {

        JSONArray accountXns = data.optJSONArray("accountXns");

        if(accountXns != null) {
            for (int index = 0; index < accountXns.length(); index++) {
                JSONObject accountXn = accountXns.getJSONObject(index);
                AccountSummary accountSummary = accountSummaryRepository.findByCustomerInfoAndAccountNumber(customerInfo, accountXn.getString("accountNo"));

                saveTransaction(accountSummary, accountXn);
            }
        }

    }

    private void saveTransaction(AccountSummary accountSummary, JSONObject accountXn) {
        JSONArray xns = accountXn.optJSONArray("xns");
        if(xns != null) {
            for (int index = 0; index < xns.length(); index++) {
                JSONObject xn = xns.getJSONObject(index);

                Transactions transactions =
                        Transactions.builder()
                                .accountSummary(accountSummary)
                                .date(PerfiosUtils.parseLocalDate(xn.optString("date")))
                                .chequeNo(PerfiosUtils.parseLong(xn.optString("chqNo")))
                                .description(xn.optString("narration"))
                                .amount(xn.optBigDecimal("amount", BigDecimal.ZERO))
                                .category(xn.optString("category"))
                                .balance(xn.optBigDecimal("balance", BigDecimal.ZERO))
                                .build();

                transactionsRepository.save(transactions);
            }
        }
    }

    private void saveAccountSummary(CustomerInfo customerInfo, JSONObject data) {
        JSONArray accountAnalysisList = data.getJSONArray("accountAnalysis");

        for (int index = 0; index < accountAnalysisList.length(); index++) {
            JSONObject accountAnalysis = accountAnalysisList.optJSONObject(index);
            JSONObject summaryInfo = accountAnalysis.getJSONObject("summaryInfo");
            AccountSummary accountSummary =
                    AccountSummary.builder()
                            .customerInfo(customerInfo)
                            .accountNumber(accountAnalysis.optString("accountNo"))
                            .accountType(accountAnalysis.optString("accountType"))
                            .institutionName(summaryInfo.optString("instName"))
                            .fullMonthCount(summaryInfo.optInt("fullMonthCount", 0))
                            .build();

            accountSummaryRepository.save(accountSummary);

            //monthly details

            JSONObject total = summaryInfo.optJSONObject("total");
            JSONObject average = summaryInfo.optJSONObject("average");
            saveMonthlyDetail(total, accountSummary, DetailType.TOTAL);
            saveMonthlyDetail(average, accountSummary, DetailType.AVG);

            saveMonthlyDetails(accountAnalysis, accountSummary);

            saveFcuIndicators(accountAnalysis, accountSummary);

            saveEodBalances(accountAnalysis, accountSummary);

            saveTopFiveFunds(accountAnalysis, accountSummary);

            saveDerivedTransactions(accountAnalysis, accountSummary);

        }


    }

    private void saveDerivedTransactions(JSONObject accountAnalysis, AccountSummary accountSummary) {
        JSONArray bouncedOrPenalXns = accountAnalysis.optJSONArray("bouncedOrPenalXns", new JSONArray());
        JSONArray reversalXns = accountAnalysis.optJSONArray("reversalXns", new JSONArray());

        if(bouncedOrPenalXns != null){
            for(int i = 0; i< bouncedOrPenalXns.length(); i++){
                saveDerivedTransaction(bouncedOrPenalXns.getJSONObject(i), accountSummary, TransactionType.BOUNCED_OR_PENAL);
            }
        }

        if(reversalXns != null){
            for(int i = 0; i< reversalXns.length(); i++){
                saveDerivedTransaction(reversalXns.getJSONObject(i), accountSummary, TransactionType.REVERSAL);
            }
        }

    }

    private void saveDerivedTransaction(JSONObject transaction, AccountSummary accountSummary, TransactionType transactionType) {
        DerivedTransactions derivedTransactions =
                DerivedTransactions
                        .builder()
                        .accountSummary(accountSummary)
                        .date(PerfiosUtils.parseLocalDate(transaction.optString("date")))
                        .chequeNo(PerfiosUtils.parseLong(transaction.optString("chqNo")))
                        .description(transaction.optString("narration"))
                        .amount(transaction.optBigDecimal("amount", BigDecimal.ZERO))
                        .category(transaction.optString("category"))
                        .balance(transaction.optBigDecimal("balance", BigDecimal.ZERO))
                        .type(transactionType)
                        .build();

        derivedTransactionsRepository.save(derivedTransactions);

    }

    private void saveTopFiveFunds(JSONObject accountAnalysis, AccountSummary accountSummary) {
        JSONArray topFiveFundsReceived = accountAnalysis.optJSONArray("top5FundsReceived", new JSONArray());
        JSONArray topFiveFundsTransferred = accountAnalysis.optJSONArray("top5FundsTransferred", new JSONArray());

        if(topFiveFundsReceived != null) {
            saveTopFiveFundsByType(accountSummary, topFiveFundsReceived, FundType.Received);
        }
        if(topFiveFundsTransferred != null) {
            saveTopFiveFundsByType(accountSummary, topFiveFundsTransferred, FundType.Remittances);
        }
    }

    private void saveTopFiveFundsByType(AccountSummary accountSummary, JSONArray topFiveFundsList, FundType fundType) {
        for (int index = 0; index < topFiveFundsList.length(); index++) {
            JSONObject topFiveFundsData = topFiveFundsList.optJSONObject(index);
            TopFiveFunds topFiveFunds =
                    TopFiveFunds.builder()
                            .accountSummary(accountSummary)
                            .monthYear(PerfiosUtils.parseMonthToLocalDate(topFiveFundsData.getString("month")))
                            .description(topFiveFundsData.getString("category"))
                            .amount(topFiveFundsData.getBigDecimal("amount"))
                            .type(fundType)
                            .build();

            topFiveFundsRepository.save(topFiveFunds);
        }
    }

    private void saveEodBalances(JSONObject accountAnalysis, AccountSummary accountSummary) {
        JSONArray eodBalancesData = accountAnalysis.optJSONArray("eODBalances");

        if(eodBalancesData != null) {
            for (int index = 0; index < eodBalancesData.length(); index++) {
                JSONObject balanceData = eodBalancesData.optJSONObject(index);
                if(balanceData != null) {
                    EodBalances eodBalances =
                            EodBalances.builder()
                                    .accountSummary(accountSummary)
                                    .date(PerfiosUtils.parseLocalDate(balanceData.optString("date")))
                                    .eodBalance(balanceData.optBigDecimal("balance", BigDecimal.ZERO))
                                    .build();

                    eodBalancesRepository.save(eodBalances);
                }
            }
        }
    }

    private void saveFcuIndicators(JSONObject accountAnalysis, AccountSummary accountSummary) {
        JSONObject fcuAnalysis = accountAnalysis.optJSONObject("fCUAnalysis");

        if(fcuAnalysis != null) {
            JSONObject possibleFraudIndicators = fcuAnalysis.optJSONObject("possibleFraudIndicators");
            JSONObject behaviouralTransactionalIndicators = fcuAnalysis.optJSONObject("behaviouralTransactionalIndicators");

            //saving fcu score
            String fcuScore = fcuAnalysis.optString("fcuScore", null);
            accountSummary.setFcuScore(fcuScore);
            accountSummaryRepository.save(accountSummary);

            if(possibleFraudIndicators != null) {
                saveIndicator(possibleFraudIndicators, accountSummary);
            }
            if(behaviouralTransactionalIndicators != null) {
                saveIndicator(behaviouralTransactionalIndicators, accountSummary);
            }
        }
    }

    private void saveIndicator(JSONObject indicator, AccountSummary accountSummary) {

        for (String key : indicator.keySet()) {

            if (FcuConst.INDICATOR_TYPE_OBJECT.contains(key)) {
                saveFCU(accountSummary, getFCUDesc(IndicatorSubType.fromKey(key)), getStatus(indicator, key), getCount(indicator, key));
            } else {
                if (FcuConst.INDICATOR_TYPE_LIST.contains(key) || FcuConst.INDICATOR_TYPE_GROUP_LIST.contains(key)) {
                    saveListFCU(accountSummary, getFCUDesc(IndicatorSubType.fromKey(key)), indicator, key);
                } else {
                    log.warn("Unknown FCU indicator found with key: " + key + " for perfiosTransactionId: " + accountSummary.getCustomerInfo().getPerfiosTransactionId());
                    return;
                }
            }
        }
    }

    private void saveListFCU(AccountSummary accountSummary, FcuIndicatorsDescription fcuIndicatorDesc, JSONObject indicator, String key) {

        FcuIndicators fcuIndicator =
                FcuIndicators.builder()
                        .accountSummary(accountSummary)
                        .fcuIndicatorsDescription(fcuIndicatorDesc)
                        .build();

        fcuIndicatorsRepository.save(fcuIndicator);


        saveListFCU(indicator, fcuIndicator, key);
    }

    private void saveListFCU(JSONObject indicator, FcuIndicators fcuIndicator, String key) {
        JSONArray fcuList = indicator.optJSONArray(key, new JSONArray());

        for (int index = 0; index < fcuList.length(); index++) {
            JSONObject fcu = fcuList.optJSONObject(index);
            saveFCUDetail(fcuIndicator, fcu);
        }
    }

    private void saveFCUDetail(FcuIndicators fcuIndicator, JSONObject fcu) {
        FcuIndicatorsDetails fcuIndicatorsDetails =
                FcuIndicatorsDetails.builder()
                        .fcuIndicators(fcuIndicator)
                        .groupNo(fcu.optInt("group"))
                        .date(PerfiosUtils.parseLocalDate(fcu.optString("date")))
                        .chqNo(fcu.optString("chqNo"))
                        .narration(fcu.optString("narration"))
                        .amount(fcu.optBigDecimal("amount", null))
                        .category(fcu.optString("category"))
                        .balance(fcu.optBigDecimal("balance", null))
                        .month(fcu.optString("month"))
                        .mode(fcu.optString("mode"))
                        .partyName(fcu.optString("partyName"))
                        .build();

        fcuIndicatorsDetailsRepository.save(fcuIndicatorsDetails);
    }

    private String getStatus(JSONObject possibleFraudIndicators, String key) {
        JSONObject value = possibleFraudIndicators.optJSONObject(key, new JSONObject());
        return value.optString("status", null);
    }

    private Long getCount(JSONObject possibleFraudIndicators, String key) {
        JSONObject value = possibleFraudIndicators.optJSONObject(key, new JSONObject());
        if(value.opt("count") != null) {
            return value.getLong("count");
        } else if(value.opt("countOfStatements") != null) {
            return value.getLong("countOfStatements");
        } else {
            return null;
        }
    }

    private void saveFCU(AccountSummary accountSummary, FcuIndicatorsDescription fcuIndicatorReference, String status, Long count) {
        FcuIndicators fcuIndicator =
                FcuIndicators.builder()
                        .accountSummary(accountSummary)
                        .fcuIndicatorsDescription(fcuIndicatorReference)
                        .status(status)
                        .count(count)
                        .build();

        fcuIndicatorsRepository.save(fcuIndicator);
    }

    private FcuIndicatorsDescription getFCUDesc(IndicatorSubType fcuSubType) {
        return fcuIndicatorsDescriptionRepository.findByIndicatorSubType(fcuSubType);
    }

    private void saveMonthlyDetails(JSONObject accountAnalysis, AccountSummary accountSummary) {

        JSONArray monthlyDetails = accountAnalysis.getJSONArray("monthlyDetails");

        for (int index = 0; index < monthlyDetails.length(); index++) {
            JSONObject monthlyDetail = monthlyDetails.optJSONObject(index);
            saveMonthlyDetail(monthlyDetail, accountSummary, DetailType.NONE);
        }

    }

    private void saveMonthlyDetail(JSONObject monthlyDetail, AccountSummary accountSummary, DetailType type) {
        if (monthlyDetail == null) {
            return;
        }
        MonthwiseDetails monthwiseDetails =
                MonthwiseDetails.builder()
                        .accountSummary(accountSummary)
                        .atmWithdrawals(monthlyDetail.optBigDecimal("atmWithdrawals", BigDecimal.ZERO))
                        .avgEodBalance(monthlyDetail.optBigDecimal("balAvg", BigDecimal.ZERO))
                        .maxEodBalance(monthlyDetail.optBigDecimal("balMax", BigDecimal.ZERO))
                        .minEodBalance(monthlyDetail.optBigDecimal("balMin", BigDecimal.ZERO))
                        .lastBalance(monthlyDetail.optBigDecimal("balLast", BigDecimal.ZERO))
                        .totalNoOfCashDeposits(monthlyDetail.optLong("cashDeposits"))
                        .totalNoOfCashWithdrawals(monthlyDetail.optLong("cashWithdrawals"))
                        .totalNoOfChequeDeposits(monthlyDetail.optLong("chqDeposits"))
                        .totalNoOfChequeIssues(monthlyDetail.optLong("chqIssues"))
                        .totalNoOfCreditTransactions(monthlyDetail.optLong("credits"))
                        .totalNoOfDebitTransactions(monthlyDetail.optLong("debits"))
                        .totalNoOfInwardChequeBounces(monthlyDetail.optLong("inwBounces"))
                        .month(PerfiosUtils.parseMonthToLocalDate(monthlyDetail.optString("monthName", "")))
                        .startDate(PerfiosUtils.parseLocalDate(monthlyDetail.optString("startDate", "")))
                        .totalNoOfOutwardChequeBounces(monthlyDetail.optLong("outwBounces"))
                        .totalNoOfSalaries(monthlyDetail.optLong("salaries"))
                        .totalAmountOfCashDeposits(monthlyDetail.optBigDecimal("totalCashDeposit", BigDecimal.ZERO))
                        .totalAmountOfCashWithdrawals(monthlyDetail.optBigDecimal("totalCashWithdrawal", BigDecimal.ZERO))
                        .totalAmountOfChequeDeposits(monthlyDetail.optBigDecimal("totalChqDeposit", BigDecimal.ZERO))
                        .totalAmountOfChequeIssues(monthlyDetail.optBigDecimal("totalChqIssue", BigDecimal.ZERO))
                        .totalAmountOfCreditTransactions(monthlyDetail.optBigDecimal("totalCredit", BigDecimal.ZERO))
                        .totalAmountOfDebitTransactions(monthlyDetail.optBigDecimal("totalDebit", BigDecimal.ZERO))
                        .totalAmountOfSalaries(monthlyDetail.optBigDecimal("totalSalary", BigDecimal.ZERO))
                        .transactionsCount(monthlyDetail.optLong("xnsCount"))
                        .type(type)
                        .build();

        monthwiseDetailsRepository.save(monthwiseDetails);
    }

    public MonthlyDetailsDTO monthlyDetails(String masFinancialId,String uniqueFirmId, MonthlyDetailsReqDTO request) throws MasEntityNotFoundException {
        MonthlyDetailsDTO monthlyDetailsDTO = MonthlyDetailsDTO.builder()
                .masFinancialId(masFinancialId)
                .build();
        monthlyDetailsDTO.setBankDetailList(getBankDetailList(masFinancialId,uniqueFirmId, request));
        monthlyDetailsDTO.setCustomEditableFieldDTO(getCustomTotalDetails(masFinancialId));
        return monthlyDetailsDTO;
    }

    private CustomEditableFieldDTO getCustomTotalDetails(String masFinancialId) {
        Optional<CustomTotalFields> totalFields = customTotalFieldsRepository.findByMasFinId(masFinancialId);

        if (!totalFields.isPresent()) {
            return CustomEditableFieldDTO.builder()
                    .totalCreditInterBank(0)
                    .totalCreditInterFirm(0)
                    .totalDebitInterBank(0)
                    .totalDebitInterFirm(0)
                    .totalCreditNonBusinessTransaction(0)
                    .totalDebitNonBusinessTransaction(0)
                    .customCreditLoanReceipts(0)
                    .customDebitLoanReceipts(0)
                    .masFinId(masFinancialId) // or any fallback value
                    .build();
        }
        CustomTotalFields customTotalFields = totalFields.get();

        return CustomEditableFieldDTO.builder()
                .totalCreditInterBank( customTotalFields.getTotalCreditInterBank() )
                .totalCreditInterFirm( customTotalFields.getTotalCreditInterFirm())
                .totalDebitInterBank(customTotalFields.getTotalDebitInterBank())
                .totalDebitInterFirm(customTotalFields.getTotalDebitInterFirm())
                .totalCreditNonBusinessTransaction( customTotalFields.getTotalCreditNonBusinessTransaction())
                .totalDebitNonBusinessTransaction(customTotalFields.getTotalDebitNonBusinessTransaction())
                .customCreditLoanReceipts(customTotalFields.getCustomCreditLoanReceipts())
                .customDebitLoanReceipts( customTotalFields.getCustomDebitLoanReceipts())
                .masFinId(customTotalFields.getMasFinId())
                .build();
    }


    private List<BankDetailDTO> getBankDetailList(String masFinancialId,String uniqueFirmId, MonthlyDetailsReqDTO request) throws MasEntityNotFoundException {
        List<CustomerInfo> flatList = List.of();
        if(uniqueFirmId.equals("0")){
            flatList= customerInfoRepository.findByMasFinancialId(masFinancialId);
        }else {
            List<MasRequests> masRequests=masRequestsRepository.findByUniqueFirmId(uniqueFirmId);
            flatList = masRequests.stream() .map(a -> customerInfoRepository.findByMasFinancialId(a.getMasFinancialId()))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(flatList)) {
            throw new MasEntityNotFoundException("No customers found this given masFinancialId");
        }
        boolean allAccounts = CollectionUtils.isEmpty(request.getCustomerTxnIdList());
        List<BankDetailDTO> bankDetailDTOList = new ArrayList<>();

        for (CustomerInfo customerInfo : flatList) {
            if (allAccounts || request.getCustomerTxnIdList().contains(customerInfo.getCustomerTransactionId())) {
                //adding account to response
                bankDetailDTOList.addAll(getBankDetailDTO(customerInfo));
            }
        }
        return bankDetailDTOList;
    }

    private List<BankDetailDTO> getBankDetailDTO(CustomerInfo customerInfo) {
        List<AccountSummary> accountSummaries = customerInfo.getAccountSummaries();
        List<BankDetailDTO> bankDetailDTOList = new ArrayList<>();

        for (AccountSummary accountSummary : accountSummaries) {
            AccountSummaryCustomFields accountSummaryCustomFields = accountSummary.getAccountSummaryCustomFields();
            BigDecimal customCreditLoanReceipts = null;
            BigDecimal customDebitLoanReceipts = null;
            if (accountSummaryCustomFields != null) {
                customCreditLoanReceipts = accountSummaryCustomFields.getCustomCreditLoanReceipts();
                customDebitLoanReceipts = accountSummaryCustomFields.getCustomDebitLoanReceipts();
            }
            BankDetailDTO bankDetailDTO = BankDetailDTO.builder()
                    .accountSummaryUuid(accountSummary.getUuid().toString())
                    .customerTransactionId(customerInfo.getCustomerTransactionId())
                    .perfiosTransactionId(customerInfo.getPerfiosTransactionId())
                    .customerDetails(CustomerDTO.from(customerInfo))
                    .bankName(accountSummary.getInstitutionName())
                    .accountNumber(accountSummary.getAccountNumber())
                    .accountType(accountSummary.getAccountType())
                    .customCreditLoanReceipts(customCreditLoanReceipts)
                    .customDebitLoanReceipts(customDebitLoanReceipts)
                    .build();
            List<MonthlyReportDTO> monthlyReportDTOList = getMonthlyReportDTOList(accountSummary);

            monthlyReportDTOList.forEach(dto ->
            {
                LocalDate startDate = dto.getMonthYear();
                LocalDate endDate = startDate.plusMonths(1);
                setBouncedPenalty(accountSummary, dto, startDate, endDate);
                masterRuleService.calculateAndSetOtherTransactions(accountSummary, dto, startDate, endDate);
            });
            if (!monthlyReportDTOList.isEmpty()) {
                bankDetailDTO.setYearMonthFrom(monthlyReportDTOList.get(0).getMonthYear());
                bankDetailDTO.setYearMonthTo(monthlyReportDTOList.get(monthlyReportDTOList.size() - 1).getMonthYear());
            }
            bankDetailDTO.setMonthlyReportList(monthlyReportDTOList);

            Optional<MasRequests> masRequests = masRequestsRepository.findByMasFinancialIdAndCustomerTransactionId(
                    customerInfo.getMasFinancialId(), customerInfo.getCustomerTransactionId());
            BigDecimal creditLimit = masRequests.map(MasRequests::getCreditLimit).orElse(BigDecimal.ZERO);
            List<EodBalances> eodBalances = eodBalancesRepository.findByAccountSummary(accountSummary);
            bankDetailDTO.setAverageBankingBalance(getAverageBankingBalance(eodBalances, creditLimit));
            bankDetailDTO.setMedianBankingBalance(getMedianBankingBalanace(eodBalances, creditLimit));

            bankDetailDTOList.add(bankDetailDTO);
        }
        return bankDetailDTOList;
    }

    private List<MonthlyReportDTO> getMonthlyReportDTOList(AccountSummary accountSummary) {
        List<MonthwiseDetails> monthwiseDetailsList = monthwiseDetailsRepository.findByAccountSummaryAndType(accountSummary, DetailType.NONE);
        List<Transactions> transactionsList = transactionsRepository.findByAccountSummary(accountSummary);

        List<MonthlyReportDTO> monthlyReportDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(transactionsList)) {
            Map<Integer, List<Transactions>> transactionsMap = transactionsList.stream()
                    .collect(Collectors.groupingBy(tx -> tx.getDate().getMonthValue(), HashMap::new, Collectors.toCollection(ArrayList::new)));
            for (MonthwiseDetails monthwiseDetails : monthwiseDetailsList) {
                List<Transactions> monthlyTransactions = transactionsMap.get(monthwiseDetails.getMonth().getMonthValue());
                long inwardChequeBounceCount = 0L;
                // check i/w return for check bounces for each transaction
                for (Transactions transaction : CollectionUtils.emptyIfNull(monthlyTransactions)) {
                    if (isInwardCheckBounceTransaction(transaction)) {
                        ++inwardChequeBounceCount;
                    }
                }
                monthlyReportDTOList.add(MonthlyReportDTO.toDTO(monthwiseDetails, inwardChequeBounceCount));
            }
        }
        return monthlyReportDTOList;
    }

    private boolean isInwardCheckBounceTransaction(Transactions transaction) {
        // check in description, category or user added comment
        return transaction != null && (I_W_RETURN_KEYWORDS.stream().anyMatch(word -> StringUtils.containsIgnoreCase(transaction.getDescription(), word))
                || I_W_RETURN_KEYWORDS.stream().anyMatch(word -> StringUtils.containsIgnoreCase(transaction.getComment(), word))
                || (transaction.getTransactionsCustomField() != null
                && I_W_RETURN_KEYWORDS.stream().anyMatch(word -> StringUtils.containsIgnoreCase(transaction.getTransactionsCustomField().getComment(), word))));
    }

    private void setBouncedPenalty(AccountSummary accountSummary, MonthlyReportDTO dto, LocalDate startDate, LocalDate endDate) {
        List<DerivedTransactions> derivedTransactions = derivedTransactionsRepository
                .findByAccountSummaryAndDateGreaterThanEqualAndDateLessThanAndCategory(accountSummary, startDate, endDate, PENAL_CHARGES);
        dto.setPenaltyCharges(BigDecimal.valueOf(derivedTransactions.stream().map(dt -> Math.abs(dt.getAmount().doubleValue())).reduce(0.0, Double::sum)));
    }

    private BigDecimal getAverageBankingBalance(List<EodBalances> eodBalances, BigDecimal creditLimit) {
        BigDecimal averageBankingBalance = null;
        if (CollectionUtils.isNotEmpty(eodBalances)) {
            averageBankingBalance = eodBalances.stream()
                    .map(balance -> balance.getEodBalance().add(creditLimit))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(eodBalances.size()), RoundingMode.HALF_EVEN);
        }
        return averageBankingBalance;
    }

    private BigDecimal getMedianBankingBalanace(List<EodBalances> eodBalances, BigDecimal creditLimit) {
        BigDecimal medianBankingBalance = null;
        if (CollectionUtils.isNotEmpty(eodBalances)) {
            List<BigDecimal> sortedEodBalanceValues = eodBalances.stream()
                    .map(balance -> creditLimit.add(balance.getEodBalance()))
                    .sorted()
                    .toList();
            int size = sortedEodBalanceValues.size();
            if (size % 2 == 0) {
                medianBankingBalance = sortedEodBalanceValues.get(size / 2)
                        .add(sortedEodBalanceValues.get((size / 2) - 1))
                        .divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
            } else {
                medianBankingBalance = sortedEodBalanceValues.get(size / 2);
            }
        }
        return medianBankingBalance;
    }

    public PageResponseDTO<TransactionDTO> getTransactions(String masFinancialId, TransactionReqDTO request, Pageable pageable) throws MasEntityNotFoundException {
        List<AccountSummary> accountSummaryList = getAccountSummaryList(masFinancialId, request);

        final Pageable updatedRequest;
        if (pageable.getSort().getOrderFor("comment") != null) {
            updatedRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(pageable.getSort().getOrderFor("comment").getDirection(), "transactionsCustomField.comment"));
        } else {
            updatedRequest = pageable;
        }
        Page<Transactions> transactions = transactionsRepository.findAll(withOptionalFilters(request, accountSummaryList), updatedRequest);
        List<TransactionDTO> transactionDTOList = transactions.stream().map(TransactionDTO::fromTransaction).toList();

        masterRuleService.applyMasterRule(transactionDTOList);

        return PageResponseDTO.<TransactionDTO>builder()
                .elements(transactionDTOList)
                .totalElements(transactions.getTotalElements())
                .totalPages(transactions.getTotalPages())
                .build();
    }

    private List<AccountSummary> getAccountSummaryList(String masFinancialId, TransactionReqDTO request) throws MasEntityNotFoundException {
        List<CustomerInfo> customerInfoList;
        List<AccountSummary> accountSummaryList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getAccountList())) {
            //find transactions with specific customerTransactionId and accountNumber
            List<String> customerIdList = request.getAccountList().stream().map(AccountsDTO::getCustomerTransactionId).toList();
            customerInfoList = customerInfoRepository.findByCustomerTransactionIdIn(customerIdList);

            if (CollectionUtils.isEmpty(customerInfoList)) {
                throw new MasEntityNotFoundException("Unable to find customer data with requested masFinancialId and customerTransactionId");
            }

            for (AccountsDTO accountsDTO : request.getAccountList()) {
                if (CollectionUtils.isNotEmpty(accountsDTO.getAccountNumberList())) {
                    accountSummaryList.addAll(accountSummaryRepository
                            .findByCustomerInfo_CustomerTransactionIdAndAccountNumberIn(accountsDTO.getCustomerTransactionId(), accountsDTO.getAccountNumberList()));
                } else {
                    accountSummaryList.addAll(accountSummaryRepository
                            .findByCustomerInfo_CustomerTransactionId(accountsDTO.getCustomerTransactionId()));
                }
            }
        } else {
            //find all transaction with this masFinancialId
            customerInfoList = customerInfoRepository.findByMasFinancialId(masFinancialId);
            if (CollectionUtils.isEmpty(customerInfoList)) {
                throw new MasEntityNotFoundException("Unable to find customer data with requested masFinancialId");
            }
            accountSummaryList = accountSummaryRepository.findByCustomerInfoIn(customerInfoList);
        }

        if (CollectionUtils.isEmpty(accountSummaryList)) {
            throw new MasEntityNotFoundException("No Account details found with this customerId");
        }
        return accountSummaryList;
    }

    public List<BankResDTO> bankList(String transactionId) {
        CustomerInfo customerInfo = customerInfoRepository.findByPerfiosTransactionId(transactionId);
        List<AccountSummary> accountSummaryList = accountSummaryRepository.findByCustomerInfo(customerInfo);

        return accountSummaryList.stream().map(acc ->
                BankResDTO.builder()
                        .accountNo(acc.getAccountNumber())
                        .bankName(acc.getInstitutionName())
                        .build())
                        .toList();

    }

    public void updateCustomFields(CustomFieldReqDTO request) {

        AccountSummaryCustomFields accountSummaryCustomFields = accountSummaryCustomFieldsRepository.findByAccountSummary_Uuid(UUID.fromString(request.getAccountSummaryUuid())).orElse(null);
        if (accountSummaryCustomFields == null) {
            accountSummaryCustomFields = new AccountSummaryCustomFields();
            accountSummaryCustomFields.setAccountSummary(accountSummaryRepository.findByUuid(UUID.fromString(request.getAccountSummaryUuid())).orElse(null));
        }
        if (request.getCustomCreditLoanReceipts() != null) {
            accountSummaryCustomFields.setCustomCreditLoanReceipts(request.getCustomCreditLoanReceipts());
        }
        if (request.getCustomDebitLoanReceipts() != null) {
            accountSummaryCustomFields.setCustomDebitLoanReceipts(request.getCustomDebitLoanReceipts());
        }
        accountSummaryCustomFieldsRepository.save(accountSummaryCustomFields);

        if (!CollectionUtils.isEmpty(request.getMonthlyReportFieldList())) {
            for (CustomFieldMonthlyReportReqDTO customField : request.getMonthlyReportFieldList()) {
                MonthwiseCustomFields monthwiseCustomFields = monthwiseCustomFieldsRepository.findByMonthwiseDetail_Uuid(UUID.fromString(customField.getMonthwiseDetailUuid())).orElse(null);
                if (monthwiseCustomFields == null) {
                    monthwiseCustomFields = new MonthwiseCustomFields();
                    monthwiseCustomFields.setMonthwiseDetail(monthwiseDetailsRepository.findByUuid(UUID.fromString(customField.getMonthwiseDetailUuid())).orElse(null));
                }
                monthwiseCustomFields.setCustomCcUtilizationPercentage(customField.getCustomCcUtilizationPercentage());
                monthwiseCustomFieldsRepository.save(monthwiseCustomFields);
            }
        }
     updateCustomTotalFields(request.getCustomEditableFieldDTO());
    }

    private void updateCustomTotalFields(CustomEditableFieldDTO customEditableFieldDTO) {
        CustomTotalFields customTotalFields=CustomTotalFields.builder().totalCreditInterBank(customEditableFieldDTO.getTotalCreditInterBank()).totalCreditInterFirm(customEditableFieldDTO.getTotalCreditInterFirm())
                .totalDebitInterBank(customEditableFieldDTO.getTotalDebitInterBank()).totalDebitInterFirm(customEditableFieldDTO.getTotalDebitInterFirm())
                .totalCreditNonBusinessTransaction(customEditableFieldDTO.getTotalCreditNonBusinessTransaction()).totalDebitNonBusinessTransaction(customEditableFieldDTO.getTotalDebitNonBusinessTransaction())
                .customCreditLoanReceipts(customEditableFieldDTO.getCustomCreditLoanReceipts()).customDebitLoanReceipts(customEditableFieldDTO.getCustomDebitLoanReceipts())
                .masFinId(customEditableFieldDTO.getMasFinId()).build();
        Optional<CustomTotalFields> customTotalFields1=customTotalFieldsRepository.findByMasFinId(customTotalFields.getMasFinId());
        customTotalFields1.ifPresent(totalFields -> customTotalFields.setId(totalFields.getId()));

        customTotalFieldsRepository.save(customTotalFields);

    }

    public TransactionTotalDTO getTransactionsTotal(String masFinancialId, TransactionReqDTO request) throws MasEntityNotFoundException {

        List<AccountSummary> accountSummaryList = getAccountSummaryList(masFinancialId, request);
        List<Transactions.Amount> amountList = transactionsRepository.findBy(withOptionalFilters(request, accountSummaryList), q-> q.as(Transactions.Amount.class).all());

        double totalCredit = amountList.stream().filter(amt -> amt.getAmount().compareTo(BigDecimal.ZERO) >= 0).mapToDouble(val -> val.getAmount().doubleValue()).sum();
        double totalDebit = amountList.stream().filter(amt -> amt.getAmount().compareTo(BigDecimal.ZERO) < 0).mapToDouble(val -> val.getAmount().abs().doubleValue()).sum();

        LocalDate endDate = null;
        LocalDate startDate = null;

        for(AccountSummary accountSummary: accountSummaryList) {
            CustomerInfo customerInfo = accountSummary.getCustomerInfo();
            Optional<StatementsConsidered> statementsConsidered = customerInfo.getStatementsConsidered().stream().filter(stmt -> stmt.getStatementAccountList().stream().anyMatch(acc -> acc.getAccountNumber().equals(accountSummary.getAccountNumber()))).findFirst();

            if(statementsConsidered.isPresent()){
                for(StatementAccounts account : statementsConsidered.get().getStatementAccountList()){
                    if (account.getAccountNumber().equals(accountSummary.getAccountNumber())) {
                        startDate = getEarliestDate(startDate, account.getStartTransactionDate());
                        endDate = getLatestDate(endDate, account.getEndTransactionDate());
                    }
                }
            }
        }

        return TransactionTotalDTO.builder()
                .totalCredit(totalCredit)
                .totalDebit(totalDebit)
                .startTransactionDate(startDate)
                .endTransactionDate(endDate)
                .build();
    }

    private static LocalDate getEarliestDate(LocalDate startDate, LocalDate transactionDate) {
        if(startDate == null){
            return transactionDate;
        } else if(startDate.compareTo(transactionDate) > 0){
            return transactionDate;
        } else {
            return startDate;
        }
    }

    private static LocalDate getLatestDate(LocalDate endDate, LocalDate transactionDate) {
        if(endDate == null){
            return transactionDate;
        } else if(endDate.compareTo(transactionDate) < 0){
            return transactionDate;
        } else {
            return endDate;
        }
    }

    public void saveFailedDataSaveIntoDb(JSONObject jsonData, String transactionId, String masFinancialId, String errorMessage) {
        MasRequests masRequests = masRequestsRepository.findByPerfiosTransactionIdAndMasFinancialId(transactionId, masFinancialId);

        masRequests.setErrorMessage(errorMessage);
        masRequests.setErrorJsonString(jsonData.toString());
        masRequests.setStatus(MasReqStatus.FAILED_SAVING_REPORT);

        masRequestsRepository.save(masRequests);
    }

    @Transactional(rollbackOn = MasEntityNotFoundException.class)
    public void updateTransactionCustomFields(List<TransactionCustomFieldReqDTO> request) throws MasEntityNotFoundException {
        for (TransactionCustomFieldReqDTO req : request) {
            updateTransactionCustomField(req);
        }
    }

    private void updateTransactionCustomField(TransactionCustomFieldReqDTO request) throws MasEntityNotFoundException {
        TransactionsCustomField customField = transactionsCustomFieldRepository.findByTransactions_Uuid(UUID.fromString(request.getTransactionUuid())).orElse(null);
        if (customField == null) {
            customField = new TransactionsCustomField();
            Optional<Transactions> transactions = transactionsRepository.findByUuid(UUID.fromString(request.getTransactionUuid()));
            if (transactions.isEmpty()) {
                throw new MasEntityNotFoundException("No transaction found with given id");
            } else {
                customField.setTransactions(transactions.get());
            }
        }
        customField.setComment(StringUtils.isBlank(request.getComment()) ? null : request.getComment());
        transactionsCustomFieldRepository.save(customField);
    }
}

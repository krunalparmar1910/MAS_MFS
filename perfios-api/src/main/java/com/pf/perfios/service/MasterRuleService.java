package com.pf.perfios.service;

import com.pf.perfios.exception.MasBadRequestException;
import com.pf.perfios.exception.MasEntityNotFoundException;
import com.pf.perfios.model.dto.MasterIdentifiersDTO;
import com.pf.perfios.model.dto.MasterIdentifiersUpdateDTO;
import com.pf.perfios.model.dto.MasterRulesDTO;
import com.pf.perfios.model.dto.MasterRulesUpdateDTO;
import com.pf.perfios.model.dto.MonthlyReportDTO;
import com.pf.perfios.model.dto.RuleQueryContext;
import com.pf.perfios.model.dto.TransactionDTO;
import com.pf.perfios.model.dto.TransactionFlagDTO;
import com.pf.perfios.model.entity.AccountSummary;
import com.pf.perfios.model.entity.DebitCredit;
import com.pf.perfios.model.entity.IdentifierType;
import com.pf.perfios.model.entity.MasterIdentifiers;
import com.pf.perfios.model.entity.MasterRule;
import com.pf.perfios.model.entity.Transactions;
import com.pf.perfios.repository.MasterIdentifiersRepository;
import com.pf.perfios.repository.MasterRuleRepository;
import com.pf.perfios.repository.TransactionsRepository;
import com.pf.perfios.utils.DbConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterRuleService {

    private final MasterIdentifiersRepository masterIdentifiersRepository;

    private final MasterRuleRepository masterRuleRepository;

    private final TransactionsRepository transactionsRepository;

    public List<MasterIdentifiersDTO> getMasterIdentifiers() {

        List<MasterIdentifiers> masterIdentifiersList = masterIdentifiersRepository.findAll();

        return masterIdentifiersList.stream().map(MasterIdentifiersDTO::from).toList();

    }

    public MasterIdentifiersDTO updateMasterIdentifier(MasterIdentifiersUpdateDTO request) throws MasEntityNotFoundException, MasBadRequestException {
        MasterIdentifiers masterIdentifiers;
        if(request.getId() == -1){
            boolean exists = masterIdentifiersRepository.existsByIdentifierNameAndIdentifierType(request.getIdentifierName(), request.getIdentifierType());
            if(exists){
                throw new MasBadRequestException("Identifier with name "+request.getIdentifierName()+" already exists");
            }
            masterIdentifiers = new MasterIdentifiers();
            masterIdentifiers.setDeletable(true);
        } else {
            masterIdentifiers = masterIdentifiersRepository.findById(request.getId()).orElseThrow(() -> new MasEntityNotFoundException("No data found with given Id"));
        }

        masterIdentifiers.setIdentifierType(request.getIdentifierType());

        //if identifier is deletable then only allow editing the identifier name
        if(masterIdentifiers.isDeletable()) {
            masterIdentifiers.setIdentifierName(request.getIdentifierName());
        }
        masterIdentifiers.setIdentificationValue(request.getIdentificationValue());
        masterIdentifiers.setDebitOrCredit(request.getDebitCredit());

        masterIdentifiersRepository.save(masterIdentifiers);

        return MasterIdentifiersDTO.from(masterIdentifiers);
    }

    public void applyMasterRule(List<TransactionDTO> transactionDTOList) {
        List<MasterRule> masterRuleList = masterRuleRepository.findAll();
        List<MasterIdentifiers> categoryIdentifierList = masterIdentifiersRepository.findByIdentifierType(IdentifierType.CATEGORY);
        List<MasterIdentifiers> transactionIdentifierList = masterIdentifiersRepository.findByIdentifierType(IdentifierType.TRANSACTION);
        List<MasterIdentifiers> partiesIdentifierList = masterIdentifiersRepository.findByIdentifierType(IdentifierType.PARTIES_OR_MERCHANT);

        transactionDTOList.forEach(
                transactionDTO -> {
                    MasterIdentifiers categoryIdentifier =
                            getMatchingIdentifierByPriority(categoryIdentifierList,transactionDTO);

                    MasterIdentifiers transactionIdentifier =
                            getMatchingIdentifierByPriority(transactionIdentifierList,transactionDTO);

                    MasterIdentifiers partiesIdentifier =
                            getMatchingIdentifierByPriority(partiesIdentifierList,transactionDTO);

                    MasterRule masterRule = findMatchingRule(transactionDTO, masterRuleList);

                    transactionDTO.setIdentifiedCategory(getIdentifierName(categoryIdentifier));
                    transactionDTO.setParties(getIdentifierName(partiesIdentifier));
                    transactionDTO.setTransactionType(getIdentifierName(transactionIdentifier));

                    transactionDTO.setTransactionFlag(masterRule != null ? masterRule.getTransactionFlag() : "");

        });
    }

    private String getIdentifierName(MasterIdentifiers identifier) {
        return identifier != null ? identifier.getIdentifierName() : "";
    }

    private MasterIdentifiers getMatchingIdentifierByPriority(List<MasterIdentifiers> identifierList, TransactionDTO transactionDTO) {
        //first trying with comment then with category and description
        MasterIdentifiers match = getMatchingIdentifier(identifierList, transactionDTO, true);
        if(match == null){
            match = getMatchingIdentifier(identifierList, transactionDTO, false);
        }
        return match;
    }

    private MasterIdentifiers getMatchingIdentifier(List<MasterIdentifiers> categoryIdentifierList, TransactionDTO transactionDTO, boolean withComment) {
        for(MasterIdentifiers identifier : categoryIdentifierList){
            if(isMasterIdentifierMatching(identifier, transactionDTO, withComment)){
                return identifier;
            }
        }
        return null;
    }

    private MasterRule findMatchingRule(TransactionDTO transactionDTO, List<MasterRule> masterRuleList) {
        //first trying by matching comment with identification value

        for(MasterRule masterRule : masterRuleList){
            boolean isMatched = isRuleMatching(transactionDTO, masterRule, true);
            if (isMatched) {
                return masterRule;
            }
        }

        for(MasterRule masterRule : masterRuleList){
            boolean isMatched = isRuleMatching(transactionDTO, masterRule, false);
            if (isMatched) {
                return masterRule;
            }
        }

        //no matching rule found
        return null;

    }

    public boolean isRuleMatching(TransactionDTO transactionDTO, MasterRule masterRule, boolean withComment) {
        //do not check for partial rule which are not yet completely updated
        if(!masterRule.isCompleted()){
            return false;
        }
        if(DebitCredit.DEBIT.equals(masterRule.getDebitOrCredit()) && transactionDTO.getAmount().doubleValue() >= 0){
            return false;
        }else if(DebitCredit.CREDIT.equals(masterRule.getDebitOrCredit()) && transactionDTO.getAmount().doubleValue() < 0){
            return false;
        }

        //if identification value is empty or null then return that it's matching the identification value
        boolean identificationValueMatch =
                Strings.isEmpty(masterRule.getIdentificationValue())
                        || isIdentificationValueMatching(masterRule.getIdentificationValue(), transactionDTO, withComment);


        boolean partiesMatch = getMatchingIdentifier(masterRule.getPartiesMerchantList(), transactionDTO, withComment) != null;
        boolean transactionTypeMatch = getMatchingIdentifier(masterRule.getTransactionTypeList(), transactionDTO, withComment) != null;
        boolean categoryMatch = getMatchingIdentifier(masterRule.getCategoryList(), transactionDTO, withComment) != null;

        String ruleQuery = masterRule.getRuleQuery();

        RuleQueryContext data =
                RuleQueryContext.builder()
                        .transactionType(transactionTypeMatch)
                        .category(categoryMatch)
                        .parties(partiesMatch)
                        .identificationValue(identificationValueMatch)
                        .build();
        return evaluateRule(ruleQuery, data);
    }

    public static boolean evaluateRule(String ruleQuery, RuleQueryContext data) {
        EvaluationContext context = new StandardEvaluationContext(data);
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(ruleQuery);
        return Boolean.TRUE.equals(expression.getValue(context, Boolean.class));
    }

    private boolean isIdentificationValueMatching(String identificationValue, TransactionDTO transactionDTO, boolean withComment) {
        String[] values = identificationValue.split(",");
        for (String value : values) {
            String trimmed = value.trim();
            if (withComment) {
                if (!Strings.isEmpty(transactionDTO.getComment()) && transactionDTO.getComment().toLowerCase().contains(trimmed.toLowerCase())) {
                    return true;
                }
            } else {
                if (transactionDTO.getCategory().toLowerCase().contains(trimmed.toLowerCase())
                        || transactionDTO.getDescription().toLowerCase().contains(trimmed.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMasterIdentifierMatching(MasterIdentifiers identifier, TransactionDTO transactionDTO, boolean withComment) {
        if(identifier.getDebitOrCredit().equals(DebitCredit.DEBIT) && transactionDTO.getAmount().doubleValue() >= 0){
            return false;
        }else if(identifier.getDebitOrCredit().equals(DebitCredit.CREDIT) && transactionDTO.getAmount().doubleValue() < 0){
            return false;
        }

        return isIdentificationValueMatching(identifier.getIdentificationValue(), transactionDTO, withComment);
    }

    public List<MasterRulesDTO> getMasterRules() {

        List<MasterRule> masterIdentifiersList = masterRuleRepository.findAll();

        return masterIdentifiersList.stream().map(MasterRulesDTO::from).toList();
    }

    public MasterRulesDTO updateMasterRule(MasterRulesUpdateDTO request) throws MasEntityNotFoundException, MasBadRequestException {
        MasterRule masterRule;
        masterRule = masterRuleRepository.findById(request.getId()).orElseThrow(()-> new MasEntityNotFoundException("No data found with given Id"));

        validateRuleQuery(request.getRuleQuery());

        List<MasterIdentifiers> transactionTypeList = masterIdentifiersRepository.findByIdIn(request.getTransactionTypeIdList());
        if(CollectionUtils.isEmpty(transactionTypeList)){
            throw new MasBadRequestException("Invalid Transaction Type Id");
        }
        masterRule.setTransactionTypeList(transactionTypeList);

        List<MasterIdentifiers> categoryList = masterIdentifiersRepository.findByIdIn(request.getCategoryIdList());
        if(CollectionUtils.isEmpty(categoryList)){
            throw new MasBadRequestException("Invalid Category Id");
        }
        masterRule.setCategoryList(categoryList);

        List<MasterIdentifiers> partiesMerchantList = masterIdentifiersRepository.findByIdIn(request.getPartiesMerchantIdList());
        if(CollectionUtils.isEmpty(partiesMerchantList)){
            throw new MasBadRequestException("Invalid Parties/Merchant Id");
        }
        masterRule.setPartiesMerchantList(partiesMerchantList);

        //not updating transaction flag value in this method
        masterRule.setIdentificationValue(request.getIdentificationValue());
        masterRule.setDebitOrCredit(request.getDebitCredit());
        masterRule.setRuleQuery(request.getRuleQuery());
        masterRule.setCompleted(true);

        checkForUniqueMasterRule(masterRule);

        masterRuleRepository.save(masterRule);

        return MasterRulesDTO.from(masterRule);
    }

    private static void validateRuleQuery(String ruleQuery) throws MasBadRequestException {
        try {
            //supplying dummy values to expression evaluator to check if its valid expression
            RuleQueryContext data =
                    RuleQueryContext.builder()
                            .transactionType(false)
                            .category(false)
                            .parties(false)
                            .identificationValue(false)
                            .build();
            evaluateRule(ruleQuery, data);
        } catch (ParseException | EvaluationException pe){
            log.error("Error while parsing ruleQuery, {} ", pe.getMessage());
            throw new MasBadRequestException("Unable to parse rule query");
        }
    }

    private void checkForUniqueMasterRule(MasterRule masterRule) throws MasBadRequestException {
        List<MasterRule> masterRules = masterRuleRepository.findAll();
        for (MasterRule savedMasterRule : masterRules) {
            if (!StringUtils.equals(savedMasterRule.getTransactionFlag(), masterRule.getTransactionFlag())
                    && ListUtils.isEqualList(savedMasterRule.getCategoryList(), masterRule.getCategoryList())
                    && ListUtils.isEqualList(savedMasterRule.getPartiesMerchantList(), masterRule.getPartiesMerchantList())
                    && ListUtils.isEqualList(savedMasterRule.getTransactionTypeList(), masterRule.getTransactionTypeList())
                    && StringUtils.equals(savedMasterRule.getIdentificationValue(), masterRule.getIdentificationValue())
                    && savedMasterRule.getDebitOrCredit() == masterRule.getDebitOrCredit()
                    && StringUtils.equals(savedMasterRule.getRuleQuery(), masterRule.getRuleQuery())) {
                throw new MasBadRequestException("Rule with given criteria already exists for Transaction Flag \"" + savedMasterRule.getTransactionFlag() + "\"");
            }
        }
    }

    private void deleteMasterIdentifier(MasterIdentifiers identifiers) throws MasBadRequestException {
        if(identifiers.isDeletable()) {
            masterIdentifiersRepository.delete(identifiers);
        } else {
            throw new MasBadRequestException("Identifier is not deletable");
        }
    }

    public void deleteMasterRule(Long id) throws MasEntityNotFoundException, MasBadRequestException {
        MasterRule masterRule = masterRuleRepository.findById(id).orElseThrow(() -> new MasEntityNotFoundException("No rule found with given Id"));
        if(masterRule.isDeletable()) {
            masterRuleRepository.deleteById(id);
        } else {
            throw new MasBadRequestException("Transaction flag is not deletable");
        }
    }

    public void deleteTransactionTypeIdentifier(Long id) throws MasBadRequestException {

        List<MasterRule> masterRules = masterRuleRepository.findByTransactionTypeList_Id(id);

        if(!CollectionUtils.isEmpty(masterRules)){
            throw new MasBadRequestException("Rule already exist with this transaction type, please delete rule");
        }

        Optional<MasterIdentifiers> masterIdentifier = masterIdentifiersRepository.findByIdAndIdentifierType(id, IdentifierType.TRANSACTION);

        if(masterIdentifier.isPresent()){
            deleteMasterIdentifier(masterIdentifier.get());
        }
    }

    public void deleteCategoryIdentifier(Long id) throws MasBadRequestException {
        List<MasterRule> masterRules = masterRuleRepository.findByCategoryList_Id(id);
        if(!CollectionUtils.isEmpty(masterRules)){
            throw new MasBadRequestException("Rule already exist with this Category, please delete rule");
        }
        Optional<MasterIdentifiers> masterIdentifier = masterIdentifiersRepository.findByIdAndIdentifierType(id, IdentifierType.CATEGORY);

        if(masterIdentifier.isPresent()){
            deleteMasterIdentifier(masterIdentifier.get());
        }
    }

    public void deletePartiesIdentifier(Long id) throws MasBadRequestException {
        List<MasterRule> masterRules = masterRuleRepository.findByPartiesMerchantList_Id(id);

        if(!CollectionUtils.isEmpty(masterRules)){
            throw new MasBadRequestException("Rule already exist with this parties or merchant, please delete rule");
        }

        Optional<MasterIdentifiers> masterIdentifier = masterIdentifiersRepository.findByIdAndIdentifierType(id, IdentifierType.PARTIES_OR_MERCHANT);

        if(masterIdentifier.isPresent()){
            deleteMasterIdentifier(masterIdentifier.get());
        }
    }

    public TransactionFlagDTO addMasterRuleTransactionFlag(TransactionFlagDTO request) throws MasBadRequestException {

        Optional<MasterRule> existing  = masterRuleRepository.findByTransactionFlag(request.getTransactionFlag());
        if(existing.isPresent()){
            throw new MasBadRequestException("Rule with transaction flag "+request.getTransactionFlag()+" already exists");
        }
        MasterRule rule = new MasterRule();
        rule.setTransactionFlag(request.getTransactionFlag());
        rule.setDeletable(true);
        rule.setCompleted(false);
        masterRuleRepository.save(rule);

        return TransactionFlagDTO.builder().id(rule.getId()).transactionFlag(request.getTransactionFlag()).build();
    }

    public void calculateAndSetOtherTransactions(AccountSummary accountSummary, MonthlyReportDTO monthlyReport, LocalDate startDate, LocalDate endDate) {
        List<Transactions> allTransactions = transactionsRepository.findByAccountSummaryAndDateGreaterThanEqualAndDateLessThan(accountSummary, startDate, endDate);

        BigDecimal totalCreditInterBank = BigDecimal.ZERO;
        BigDecimal totalCreditInterFirm = BigDecimal.ZERO;
        BigDecimal totalCreditNonBusinessTransaction = BigDecimal.ZERO;
        BigDecimal totalDebitInterBank = BigDecimal.ZERO;
        BigDecimal totalDebitInterFirm = BigDecimal.ZERO;
        BigDecimal totalDebitNonBusinessTransaction = BigDecimal.ZERO;
        BigDecimal totalLoanDisbursements = BigDecimal.ZERO;
        BigDecimal totalEmiTracing = BigDecimal.ZERO;
        long totalNoOfIwReturn = 0L;

        MasterRule creditInterBankRule = masterRuleRepository.findByTransactionFlag(DbConst.CREDIT_INTER_BANK_TRANSACTION_FLAG).orElse(null);
        MasterRule creditInterFirmRule = masterRuleRepository.findByTransactionFlag(DbConst.CREDIT_INTER_FIRM_TRANSACTION_FLAG).orElse(null);
        MasterRule creditNonBusinessTxnRule = masterRuleRepository.findByTransactionFlag(DbConst.CREDIT_NON_BUSINESS_TRANSACTION_FLAG).orElse(null);

        MasterRule debitInterBankRule = masterRuleRepository.findByTransactionFlag(DbConst.DEBIT_INTER_BANK_TRANSACTION_FLAG).orElse(null);
        MasterRule debitInterFirmRule = masterRuleRepository.findByTransactionFlag(DbConst.DEBIT_INTER_FIRM_TRANSACTION_FLAG).orElse(null);
        MasterRule debitNonBusinessTxnRule = masterRuleRepository.findByTransactionFlag(DbConst.DEBIT_NON_BUSINESS_TRANSACTION_FLAG).orElse(null);
        MasterIdentifiers loanDisbursementIdentifier = masterIdentifiersRepository.findByIdentifierNameAndIdentifierType(DbConst.LOAN_DISBURSEMENT_TRANSACTION_TYPE, IdentifierType.TRANSACTION).orElse(null);
        MasterIdentifiers emiTracingIdentifier = masterIdentifiersRepository.findByIdentifierNameAndIdentifierType(DbConst.EMI_TRACING_TRANSACTION_TYPE, IdentifierType.TRANSACTION).orElse(null);

        for (Transactions transaction : allTransactions) {

            TransactionDTO transactionDTO = TransactionDTO.fromTransaction(transaction);

            if (loanDisbursementIdentifier != null && (isMasterIdentifierMatching(loanDisbursementIdentifier, transactionDTO, true)
                    || isMasterIdentifierMatching(loanDisbursementIdentifier, transactionDTO, false))) {
                totalLoanDisbursements = totalLoanDisbursements.add(transactionDTO.getAmount().abs());

            } else if (emiTracingIdentifier != null && (isMasterIdentifierMatching(emiTracingIdentifier, transactionDTO, true)
                    || isMasterIdentifierMatching(emiTracingIdentifier, transactionDTO, false))) {
                totalEmiTracing = totalEmiTracing.add(transactionDTO.getAmount().abs());
            }

            if(transactionDTO.isCredit()) {
                if(isRuleApplicable(transactionDTO, creditInterBankRule)) {
                    totalCreditInterBank = totalCreditInterBank.add(transactionDTO.getAmount());
                    continue;
                }

                if(isRuleApplicable(transactionDTO, creditInterFirmRule)){
                    totalCreditInterFirm = totalCreditInterFirm.add(transactionDTO.getAmount());
                    continue;
                }

                if(isRuleApplicable(transactionDTO, creditNonBusinessTxnRule)) {
                    totalCreditNonBusinessTransaction = totalCreditNonBusinessTransaction.add(transactionDTO.getAmount());
                }

            } else {
                if (isIdentificationValueMatching(DbConst.IW_RETURN_IDENTIFIER, transactionDTO, true)
                        || isIdentificationValueMatching(DbConst.IW_RETURN_IDENTIFIER, transactionDTO, false)) {
                    totalNoOfIwReturn++;
                }

                if(isRuleApplicable(transactionDTO, debitInterBankRule)) {
                    totalDebitInterBank = totalDebitInterBank.add(transactionDTO.getAmount().abs());
                    continue;
                }

                if(isRuleApplicable(transactionDTO, debitInterFirmRule)){
                    totalDebitInterFirm = totalDebitInterFirm.add(transactionDTO.getAmount().abs());
                    continue;
                }

                if(isRuleApplicable(transactionDTO, debitNonBusinessTxnRule)) {
                    totalDebitNonBusinessTransaction = totalDebitNonBusinessTransaction.add(transactionDTO.getAmount().abs());
                }
            }
        }

        monthlyReport.setCreditInterBank(totalCreditInterBank);
        monthlyReport.setCreditInterFirm(totalCreditInterFirm);
        monthlyReport.setCreditNonBusinessTransaction(totalCreditNonBusinessTransaction);
        monthlyReport.setDebitInterBank(totalDebitInterBank);
        monthlyReport.setDebitInterFirm(totalDebitInterFirm);
        monthlyReport.setDebitNonBusinessTransaction(totalDebitNonBusinessTransaction);
        monthlyReport.setTotalLoanDisbursements(totalLoanDisbursements);
        monthlyReport.setTotalEmiTracing(totalEmiTracing);
        monthlyReport.setInwardReturn(totalNoOfIwReturn);

    }


    private boolean isRuleApplicable(TransactionDTO transactionDTO, MasterRule masterRule) {
        if(masterRule == null || !masterRule.isCompleted()){
            return false;
        }
        if(isRuleMatching(transactionDTO, masterRule, true)){
            return true;
        } else {
            return isRuleMatching(transactionDTO, masterRule, false);
        }
    }
}

package com.pf.mas.service.cibilservice;

import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.mapper.CibilConsumerMapper;
import com.pf.mas.model.dto.cibil.consumer.AuccountSummerySegmentDTO;
import com.pf.mas.model.dto.cibil.consumer.DcResponseDTO;
import com.pf.mas.model.dto.cibil.consumer.EnvelopeDTO;
import com.pf.mas.model.dto.cibil.consumer.NameSegmentDTO;
import com.pf.mas.model.dto.cibil.enums.AssetClassificationStatus;
import com.pf.mas.model.dto.cibil.enums.CcFlag;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsDTO;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsSearchRequestDTO;
import com.pf.mas.model.dto.cibil.ui.AccountInquiriesHistoricalDTO;
import com.pf.mas.model.dto.cibil.ui.AddressInfoDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerAccountSummaryDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerProfileSummaryDTO;
import com.pf.mas.model.dto.cibil.ui.DataSaveDTO;
import com.pf.mas.model.dto.cibil.ui.IdentificationDTO;
import com.pf.mas.model.dto.cibil.ui.LosDetailsDTO;
import com.pf.mas.model.entity.cibil.*;
import com.pf.mas.model.entity.consumer.*;
import com.pf.mas.repository.cibil.*;
import com.pf.mas.repository.emiMaster.EMIMasterRepository;
import com.pf.mas.utils.cibil.MappingRegistry;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CibilConsumerServiceImpl implements CibilConsumerService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private EnvelopeResponseRepository envelopeResponseRepository;

    @Autowired
    private CibilConsumerMapper cibilConsumerMapper;

    @Autowired
    private NonCibilAccountRepository nonCibilAccountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LosDetailsRepository losDetailsRepository;

    @Autowired
    private RawDataRepository rawDataRepository;

    @Autowired
    private EMIMasterRepository emiMasterRepository;

    @Autowired
    private CibilDpdDataRepository cibilDpdDataRepository;

    static Map<String,Map<String ,Object>> allIndividual=new HashMap<>();
    static Map<String,Map<String ,Object>> allJoint=new HashMap<>();
    static Map<String,Map<String ,Object>> allGuarantor=new HashMap<>();

    @Override
    @Transactional
    public EnvelopeResponse addConsumerDetail(String requestId, EnvelopeDTO envelopeDTO, LosDetailsDTO losDetailsDTO, String ipAddress, String createdBy) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][addConsumerDetail] Could not save consumer data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id not found.");
        }
        if (envelopeResponseRepository.findByRequestId(requestId).isPresent()) {
            log.error(String.format("[CibilConsumerServiceImpl][addConsumerDetail] Could not save consumer data. Data against the request id %s already present.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s already present.", requestId));
        }

        LosDetails losDetails = cibilConsumerMapper.toLosDetails(losDetailsDTO);
        if (losDetails != null) {
            log.info("[CibilConsumerServiceImpl][addConsumerDetail] Initiating los Details data save.");
            losDetails.setRequestId(requestId);
            losDetails.setArchived(false);
            losDetailsRepository.save(losDetails);
            log.info("[CibilConsumerServiceImpl][addConsumerDetail] Los details data saved successfully.");
        }
        if (envelopeDTO != null && envelopeDTO.getBodyDTO() != null && envelopeDTO.getBodyDTO().getExecuteXMLStringResponseDTO() != null && envelopeDTO.getBodyDTO().getExecuteXMLStringResponseDTO().getExecuteXMLStringResultDTO() != null) {
            log.info("[CibilConsumerServiceImpl][addConsumerDetail]Initiating consumer data save.");
            DcResponseDTO dcResponseDTO = envelopeDTO.getBodyDTO().getExecuteXMLStringResponseDTO().getExecuteXMLStringResultDTO().getDcResponseDTO();
            EnvelopeResponse envelopeResponse = cibilConsumerMapper.toEnvelopeResponse(dcResponseDTO);
            envelopeResponse.setRequestId(requestId);
            envelopeResponse.setIpAddress(ipAddress);
            envelopeResponse.setCreatedBy(createdBy);
            EnvelopeResponse savedEnvelopeResponse = envelopeResponseRepository.save(envelopeResponse);
            saveDpdData(savedEnvelopeResponse,requestId);
            log.info("[CibilConsumerServiceImpl][addConsumerDetail] Consumer data saved successfully.");
            return savedEnvelopeResponse;
        }
        log.error("[CibilConsumerServiceImpl][addConsumerDetail] Error while saving consumer data.");
        throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Xml data found to be empty.");

    }

    @Override
    public ConsumerProfileSummaryDTO getConsumerProfileSummary(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not save consumer data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request Id not found.");
        }
        EnvelopeResponse envelopeResponse = searchConsumerDetails(requestId, accountBorrowingDetailsSearchRequestDTO);

        if (envelopeResponse.getCombinedResponse() == null || envelopeResponse.getCombinedResponse().getApplicant() == null || envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData() == null || envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse() == null) {
            log.error(String.format("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not fetch consumer summary. Data against the request id %s is not populated.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s is not populated.", requestId));
        }
        ConsumerProfileSummaryDTO consumerProfileSummaryDTO = new ConsumerProfileSummaryDTO();

        CreditReport creditReport = envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse().getCreditReport();
        CreditReport secondaryCreditReport = envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse().getSecondaryCreditReport();
        Applicant applicant = envelopeResponse.getCombinedResponse().getApplicant();

        if (creditReport == null) {
            log.error(String.format("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not fetch consumer summary. Data against the request id %s is not populated.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s is not populated.", requestId));
        }

        Date cibilReportProcessedDate = null;
        if (creditReport.getCreditReportHeaderList().get(0) != null) {
            cibilReportProcessedDate = creditReport.getCreditReportHeaderList().get(0).getDateProcessed();
        }

        if (!creditReport.getNameSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setName(StringUtils.joinWith(StringUtils.SPACE,
                    applicant.getApplicantFirstName(), applicant.getApplicantMiddleName(), applicant.getApplicantLastName()));
            consumerProfileSummaryDTO.setDateOfBirth(creditReport.getNameSegmentList().get(0).getDateOfBirth());
            consumerProfileSummaryDTO.setGender(creditReport.getNameSegmentList().get(0).getGender());
            consumerProfileSummaryDTO.setAge(calculateAge(creditReport.getNameSegmentList().get(0).getDateOfBirth()));
        }
        if (!creditReport.getScoreSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setScore(creditReport.getScoreSegmentList().get(0).getScore());
        }
        if (!creditReport.getCreditReportHeaderList().isEmpty()) {
            consumerProfileSummaryDTO.setControlNumber(creditReport.getCreditReportHeaderList().get(0).getEnquiryControlNumber());
        }
        if (!creditReport.getIdSegmentList().isEmpty()) {
            List<IdentificationDTO> identificationDTOList = new ArrayList<>();
            for (IdSegment eachIdSegment : creditReport.getIdSegmentList()) {
                IdentificationDTO newIdentificationDTO = new IdentificationDTO();
                newIdentificationDTO.setIdType(eachIdSegment.getIdType());
                newIdentificationDTO.setIdNumber(eachIdSegment.getIdNumber());
                identificationDTOList.add(newIdentificationDTO);
            }
            consumerProfileSummaryDTO.setIdentification(identificationDTOList);
        }
        if (!creditReport.getTelephoneSegmentList().isEmpty()) {
            List<String> telephoneList = new ArrayList<>();
            for (TelephoneSegment eachTelephoneSegment : creditReport.getTelephoneSegmentList()) {
                telephoneList.add(eachTelephoneSegment.getTelephoneNumber());
            }
            consumerProfileSummaryDTO.setTelephoneNumbers(telephoneList);
        }
        if (!creditReport.getEmailContactSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setEmails(creditReport.getEmailContactSegmentList().stream().map(EmailContactSegment::getEmailId).collect(Collectors.toList()));
        }
        if (!creditReport.getCreditReportAddressList().isEmpty()) {
            List<AddressInfoDTO> addressInfoDTOList = getAddressInfoDTOList(creditReport);
            consumerProfileSummaryDTO.setAddresses(addressInfoDTOList);
        }
        // For Additional Details in Borrowing Details...
        if (secondaryCreditReport != null && !secondaryCreditReport.getCreditReportAddressList().isEmpty()) {
            List<AddressInfoDTO> secondaryAddressInfoDTOList = getSecondaryAddressInfoDTOList(secondaryCreditReport);
            consumerProfileSummaryDTO.setSecondaryAddresses(secondaryAddressInfoDTOList);
        }
        if (secondaryCreditReport != null && !secondaryCreditReport.getTelephoneSegmentList().isEmpty()) {
            List<String> telephoneList = new ArrayList<>();
            for (TelephoneSegment eachTelephoneSegment : secondaryCreditReport.getTelephoneSegmentList()) {
                telephoneList.add(eachTelephoneSegment.getTelephoneNumber());
            }
            consumerProfileSummaryDTO.setSecondaryTelephoneNumbers(telephoneList);
        }
        if (secondaryCreditReport != null && !secondaryCreditReport.getIdSegmentList().isEmpty()) {
            List<IdentificationDTO> identificationDTOList = new ArrayList<>();
            for (IdSegment eachIdSegment : creditReport.getIdSegmentList()) {
                IdentificationDTO newIdentificationDTO = new IdentificationDTO();
                newIdentificationDTO.setIdType(eachIdSegment.getIdType());
                newIdentificationDTO.setIdNumber(eachIdSegment.getIdNumber());
                identificationDTOList.add(newIdentificationDTO);
            }
            consumerProfileSummaryDTO.setSecondaryIdentification(identificationDTOList);
        }
        if (secondaryCreditReport != null && !secondaryCreditReport.getNameSegmentList().isEmpty()) {
            List<NameSegmentDTO> nameSegmentDTOList = getNameSegmentDTOList(secondaryCreditReport);
            consumerProfileSummaryDTO.setSecondaryNameSegmentList(nameSegmentDTOList);
        }

        ConsumerAccountSummaryDTO consumerAccountSummaryDTO = new ConsumerAccountSummaryDTO();
        AccountInquiriesHistoricalDTO accountInquiriesHistoricalDTO = new AccountInquiriesHistoricalDTO();

        if (!creditReport.getAccountList().isEmpty()) {
            long liveAccounts = 0, closedAccounts = 0;
            BigDecimal sanctionedAmount = BigDecimal.ZERO, overdueAmount = BigDecimal.ZERO, pos = BigDecimal.ZERO;
            consumerAccountSummaryDTO.setTotalLoansOpened((long) creditReport.getAccountList().size());

            setLoanInfoData(creditReport.getAccountList(),consumerAccountSummaryDTO);
            for (Account eachAccount : creditReport.getAccountList()) {
                updateDPDCountForAccounts(consumerAccountSummaryDTO, eachAccount);

                if (!eachAccount.getAccountNonSummarySegment().isLiveAccount()) {
                    closedAccounts += 1;
                } else {
                    liveAccounts += 1;
                    sanctionedAmount = sanctionedAmount.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount() == null ? 0 : eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount()));
                    overdueAmount = overdueAmount.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getAmountOverdue() == null ? 0 : eachAccount.getAccountNonSummarySegment().getAmountOverdue()));
                    pos = pos.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getCurrentBalance() == null ? 0 : eachAccount.getAccountNonSummarySegment().getCurrentBalance()));
                }
                Date dateOpened = eachAccount.getAccountNonSummarySegment().getDateOpenedOrDisbursed();
                calculateAccountsOpenedTime(cibilReportProcessedDate, dateOpened, accountInquiriesHistoricalDTO);
            }
            consumerAccountSummaryDTO.setIndividual(allIndividual);
            consumerAccountSummaryDTO.setJoint(allJoint);
            consumerAccountSummaryDTO.setGuarantor(allGuarantor);
            consumerAccountSummaryDTO.setLiveLoans(liveAccounts);
            consumerAccountSummaryDTO.setClosedLoans(closedAccounts);
            consumerAccountSummaryDTO.setSanctionedAmountLiveLoans(sanctionedAmount);
            consumerAccountSummaryDTO.setOverdueAmountLiveLoans(overdueAmount);
            consumerAccountSummaryDTO.setPosLiveLoans(pos);

            List<AccountBorrowingDetailsDTO> accountBorrowingDetailsDTOList = calculateAccountBorrowingDetails(creditReport.getAccountList());
            List<AccountBorrowingDetailsDTO> filteredAccountBorrowingDetailsDTOList = filterAccountBorrowingDetails(accountBorrowingDetailsDTOList, accountBorrowingDetailsSearchRequestDTO);
            consumerProfileSummaryDTO.setAccountBorrowingDetailsDTOList(filteredAccountBorrowingDetailsDTOList);
        }
        consumerProfileSummaryDTO.setAccountSummary(consumerAccountSummaryDTO);


        if (!creditReport.getEnquiryList().isEmpty()) {
            for (Enquiry eachEnquiry : creditReport.getEnquiryList()) {
                Date dateEnquired = eachEnquiry.getDateOfEnquiryFields();
                calculateLastEnquiryTime(cibilReportProcessedDate, dateEnquired, accountInquiriesHistoricalDTO);
            }
        }
        consumerProfileSummaryDTO.setAccountInquiriesHistoricalData(accountInquiriesHistoricalDTO);
        consumerProfileSummaryDTO.setNonCibilAccountList(this.getNonCibilAccount(requestId));
        Optional<LosDetails> losDetails = getLosDetails(requestId);
        losDetails.ifPresent(details -> consumerProfileSummaryDTO.setLosDetailsDTO(cibilConsumerMapper.toLosDetailsDTO(details)));
        return consumerProfileSummaryDTO;
    }

    private List<AccountBorrowingDetailsDTO> filterAccountBorrowingDetails(List<AccountBorrowingDetailsDTO> accountBorrowingDetailsDTOList, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO) {
        return accountBorrowingDetailsDTOList.stream()
                .filter(eachAccountBorrowingDetailsDTO ->
                        (accountBorrowingDetailsSearchRequestDTO.getLast12MonthsDpdLowerRange() == null || eachAccountBorrowingDetailsDTO.getLast12MonthsDPD() >= accountBorrowingDetailsSearchRequestDTO.getLast12MonthsDpdLowerRange()) &&
                                (accountBorrowingDetailsSearchRequestDTO.getLast12MonthsDpdUpperRange() == null || eachAccountBorrowingDetailsDTO.getLast12MonthsDPD() <= accountBorrowingDetailsSearchRequestDTO.getLast12MonthsDpdUpperRange()) &&
                                (accountBorrowingDetailsSearchRequestDTO.getOverallDpdLowerRange() == null || eachAccountBorrowingDetailsDTO.getOverallDPD() >= accountBorrowingDetailsSearchRequestDTO.getOverallDpdLowerRange()) &&
                                (accountBorrowingDetailsSearchRequestDTO.getOverallDpdUpperRange() == null || eachAccountBorrowingDetailsDTO.getOverallDPD() <= accountBorrowingDetailsSearchRequestDTO.getOverallDpdUpperRange())

                )
                .collect(Collectors.toList());
    }

    @Override
    public EnvelopeResponse searchConsumerDetails(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][searchConsumerDetails] Could not save consumer data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request Id not found.");
        }
        QEnvelopeResponse qEnvelopeResponse = QEnvelopeResponse.envelopeResponse;
        QCombinedResponse qCombinedResponse = QCombinedResponse.combinedResponse;
        QApplicant qApplicant = QApplicant.applicant;
        QDsCibilBureauData qDsCibilBureauData = QDsCibilBureauData.dsCibilBureauData;
        QCibilBureauResponse qCibilBureauResponse = QCibilBureauResponse.cibilBureauResponse;
        QCreditReport qCreditReport = QCreditReport.creditReport;
        QAccount qAccount = QAccount.account;
        QAccountNonSummarySegment qAccountNonSummarySegment = QAccountNonSummarySegment.accountNonSummarySegment;

        BooleanBuilder outerBooleanBuilder = new BooleanBuilder();
        outerBooleanBuilder.and(qEnvelopeResponse.requestId.eq(requestId));

        BooleanBuilder innerBooleanBuilder = new BooleanBuilder();
        if (accountBorrowingDetailsSearchRequestDTO.getTypeOfLoan() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.accountType.eq(accountBorrowingDetailsSearchRequestDTO.getTypeOfLoan()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getSanctionAmountLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.highCreditOrSanctionedAmount.goe(accountBorrowingDetailsSearchRequestDTO.getSanctionAmountLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getSanctionAmountUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.highCreditOrSanctionedAmount.loe(accountBorrowingDetailsSearchRequestDTO.getSanctionAmountUpperRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getOutStandingAmountLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.currentBalance.goe(accountBorrowingDetailsSearchRequestDTO.getOutStandingAmountLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getOutStandingAmountUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.currentBalance.loe(accountBorrowingDetailsSearchRequestDTO.getOutStandingAmountUpperRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getSanctionedDateLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.dateOpenedOrDisbursed.goe(accountBorrowingDetailsSearchRequestDTO.getSanctionedDateLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getSanctionedDateUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.dateOpenedOrDisbursed.loe(accountBorrowingDetailsSearchRequestDTO.getSanctionedDateUpperRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getStatus() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.creditFacility.eq(accountBorrowingDetailsSearchRequestDTO.getStatus()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getBankNbfc() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.reportingMemberShortName.eq(accountBorrowingDetailsSearchRequestDTO.getBankNbfc()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getOwnerShip() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.ownershipIndicator.eq(accountBorrowingDetailsSearchRequestDTO.getOwnerShip()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getOverdueLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.amountOverdue.goe(accountBorrowingDetailsSearchRequestDTO.getOverdueLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getOverdueUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.amountOverdue.loe(accountBorrowingDetailsSearchRequestDTO.getOverdueUpperRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getTenureLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.repaymentTenure.goe(accountBorrowingDetailsSearchRequestDTO.getTenureLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getTenureUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.repaymentTenure.loe(accountBorrowingDetailsSearchRequestDTO.getTenureUpperRange()));
        }

        if (accountBorrowingDetailsSearchRequestDTO.getReportedDateLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.dateReportedAndCertified.goe(accountBorrowingDetailsSearchRequestDTO.getReportedDateLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getReportedDateUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.dateReportedAndCertified.loe(accountBorrowingDetailsSearchRequestDTO.getReportedDateUpperRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getEmiLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.emiAmount.goe(accountBorrowingDetailsSearchRequestDTO.getEmiLowerRange()));
        }
        if (accountBorrowingDetailsSearchRequestDTO.getEmiUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.emiAmount.loe(accountBorrowingDetailsSearchRequestDTO.getEmiUpperRange()));
        }

        if (accountBorrowingDetailsSearchRequestDTO.getInterestRateLowerRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.rateOfInterest.goe(accountBorrowingDetailsSearchRequestDTO.getInterestRateLowerRange()));
        }

        if (accountBorrowingDetailsSearchRequestDTO.getInterestRateUpperRange() != null) {
            innerBooleanBuilder.and(qAccountNonSummarySegment.rateOfInterest.loe(accountBorrowingDetailsSearchRequestDTO.getInterestRateUpperRange()));
        }


        JPAQuery<EnvelopeResponse> outerQuery = createQueryForFilter().where(outerBooleanBuilder);
        JPAQuery<EnvelopeResponse> innerQuery = createQueryForFilter().where(outerBooleanBuilder).where(innerBooleanBuilder);


        List<EnvelopeResponse> envelopeResponseList;

        envelopeResponseList = innerQuery.fetch();
        if (envelopeResponseList.isEmpty()) {
            envelopeResponseList = outerQuery.fetch();
            if (envelopeResponseList.size() == 1) {
                envelopeResponseList.get(0).getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse().getCreditReport().setAccountList(new ArrayList<>());
                return envelopeResponseList.get(0);
            }
        } else if (envelopeResponseList.size() == 1) {
            return envelopeResponseList.get(0);
        }


        log.error(String.format("[CibilConsumerServiceImpl][searchConsumerDetails] Could not fetch consumer summary. Data against the request id %s already present.", requestId));
        throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s not found.", requestId));
    }

    public Optional<LosDetails> getLosDetails(String requestId) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][getLosDetails] Could not fetch los details. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id cannot be empty.");
        }
        return losDetailsRepository.findByRequestIdAndArchived(requestId, false);
    }

    @Override
    public List<NonCibilAccount> getNonCibilAccount(String requestId) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][getNonCibilAccount] Could not fetch non-cibil data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id cannot be empty.");
        }
        Optional<EnvelopeResponse> envelopeResponse = envelopeResponseRepository.findByRequestId(requestId);
        if (envelopeResponse.isEmpty()) {
            log.error(String.format("[CibilConsumerServiceImpl][getNonCibilAccount]Could not fetch data. Cibil data against the request id %s not found.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Cibil data against the request id %s not found.", requestId));
        } else {
            return nonCibilAccountRepository.findAllByRequestIdAndArchived(requestId, false);
        }
    }

    private static void updateDPDCountForAccounts(ConsumerAccountSummaryDTO consumerAccountSummaryDTO ,Account account ) {
        List<PaymentHistoryRecord> paymentHistoryRecordList=account.getAccountNonSummarySegment().getPaymentHistoryRecordList();
        boolean isLive=account.getAccountNonSummarySegment().isLiveAccount();
        String ownershipIndicator=account.getAccountNonSummarySegment().getOwnershipIndicator();
        long latestDpd;
        AssetClassificationStatus assetClassificationStatus;
        boolean hasStatus;

        if (!paymentHistoryRecordList.isEmpty()) {
            PaymentHistoryRecord paymentHistoryRecord = getPaymentHistoryRecordWithHighestDpd(paymentHistoryRecordList);
            if(paymentHistoryRecord == null) {
                return;
            }
            if (paymentHistoryRecord.getStatus() != null) {
                hasStatus = true;
                assetClassificationStatus = paymentHistoryRecord.getStatus();
                latestDpd = 0;
                if(paymentHistoryRecord.getDpd() != null) {
                    if(assetClassificationStatus == AssetClassificationStatus.XXX || assetClassificationStatus == AssetClassificationStatus.STD) {
                        if(paymentHistoryRecord.getDpd() > 0L) {
                            hasStatus = false;
                            assetClassificationStatus = null;
                            latestDpd = paymentHistoryRecord.getDpd();
                        }
                    }
                }
            } else {
                hasStatus = false;
                assetClassificationStatus = AssetClassificationStatus.XXX;
                latestDpd = paymentHistoryRecord.getDpd();
            }

            if (hasStatus) {
                if (assetClassificationStatus == AssetClassificationStatus.STD) {
                    consumerAccountSummaryDTO.setStdAllAccounts(consumerAccountSummaryDTO.getStdAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setStdLiveAccounts(consumerAccountSummaryDTO.getStdLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setStdClosedAccounts(consumerAccountSummaryDTO.getStdClosedAccounts() + 1);
                    }
                } else if (assetClassificationStatus == AssetClassificationStatus.SMA) {
                    consumerAccountSummaryDTO.setSmaAllAccounts(consumerAccountSummaryDTO.getSmaAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setSmaLiveAccounts(consumerAccountSummaryDTO.getSmaLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setSmaClosedAccounts(consumerAccountSummaryDTO.getSmaClosedAccounts() + 1);
                    }
                } else if (assetClassificationStatus == AssetClassificationStatus.SUB) {
                    consumerAccountSummaryDTO.setSubAllAccounts(consumerAccountSummaryDTO.getSubAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setSubLiveAccounts(consumerAccountSummaryDTO.getSubLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setSubClosedAccounts(consumerAccountSummaryDTO.getSubClosedAccounts() + 1);
                    }
                } else if (assetClassificationStatus == AssetClassificationStatus.DBT) {
                    consumerAccountSummaryDTO.setDbtAllAccounts(consumerAccountSummaryDTO.getDbtAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setDbtLiveAccounts(consumerAccountSummaryDTO.getDbtLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setDbtClosedAccounts(consumerAccountSummaryDTO.getDbtClosedAccounts() + 1);
                    }
                } else if (assetClassificationStatus == AssetClassificationStatus.LSS) {
                    consumerAccountSummaryDTO.setLssAllAccounts(consumerAccountSummaryDTO.getLssAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setLssLiveAccounts(consumerAccountSummaryDTO.getLssLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setLssClosedAccounts(consumerAccountSummaryDTO.getLssClosedAccounts() + 1);
                    }
                } else if (assetClassificationStatus == AssetClassificationStatus.XXX) {
                    consumerAccountSummaryDTO.setNotReportedAllAccounts(consumerAccountSummaryDTO.getNotReportedAllAccounts() + 1);
                    if (isLive) {
                        consumerAccountSummaryDTO.setNotReportedLiveAccounts(consumerAccountSummaryDTO.getNotReportedLiveAccounts() + 1);
                    } else {
                        consumerAccountSummaryDTO.setNotReportedClosedAccounts(consumerAccountSummaryDTO.getNotReportedClosedAccounts() + 1);
                    }
                }
            } else {
                if(ownershipIndicator.equals("Individual")){
                    if (latestDpd == 0) {
                        consumerAccountSummaryDTO.setDpd0AllAccountsInd(consumerAccountSummaryDTO.getDpd0AllAccountsInd()+1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd0LiveAccountsInd(consumerAccountSummaryDTO.getDpd0LiveAccountsInd() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd0ClosedAccountsInd(consumerAccountSummaryDTO.getDpd0ClosedAccountsInd() + 1);
                        }
                    } else if (latestDpd >= 1 && latestDpd <= 30) {
                        consumerAccountSummaryDTO.setDpd1to30AllAccountsInd(consumerAccountSummaryDTO.getDpd1to30AllAccountsInd() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd1to30LiveAccountsInd(consumerAccountSummaryDTO.getDpd1to30LiveAccountsInd() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd1to30ClosedAccountsInd(consumerAccountSummaryDTO.getDpd1to30ClosedAccountsInd() + 1);
                        }
                    } else if (latestDpd >= 31 && latestDpd <= 90) {
                        consumerAccountSummaryDTO.setDpd31to90AllAccountsInd(consumerAccountSummaryDTO.getDpd31to90AllAccountsInd() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd31to90LiveAccountsInd(consumerAccountSummaryDTO.getDpd31to90LiveAccountsInd() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd31to90ClosedAccountsInd(consumerAccountSummaryDTO.getDpd31to90ClosedAccountsInd() + 1);
                        }
                    } else if (latestDpd > 90) {
                        consumerAccountSummaryDTO.setDpdGreaterThan90AllAccountsInd(consumerAccountSummaryDTO.getDpdGreaterThan90AllAccountsInd() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpdGreaterThan90LiveAccountsInd(consumerAccountSummaryDTO.getDpdGreaterThan90LiveAccountsInd() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpdGreaterThan90ClosedAccountsInd(consumerAccountSummaryDTO.getDpdGreaterThan90ClosedAccountsInd() + 1);
                        }
                    }
                }else if (ownershipIndicator.equals("Joint")){
                    if (latestDpd == 0) {
                        consumerAccountSummaryDTO.setDpd0AllAccountsJoint(consumerAccountSummaryDTO.getDpd0AllAccountsJoint() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd0LiveAccountsJoint(consumerAccountSummaryDTO.getDpd0LiveAccountsJoint() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd0ClosedAccountsJoint(consumerAccountSummaryDTO.getDpd0ClosedAccountsJoint() + 1);
                        }
                    } else if (latestDpd >= 1 && latestDpd <= 30) {
                        consumerAccountSummaryDTO.setDpd1to30AllAccountsJoint(consumerAccountSummaryDTO.getDpd1to30AllAccountsJoint() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd1to30LiveAccountsJoint(consumerAccountSummaryDTO.getDpd1to30LiveAccountsJoint() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd1to30ClosedAccountsJoint(consumerAccountSummaryDTO.getDpd1to30ClosedAccountsJoint() + 1);
                        }
                    } else if (latestDpd >= 31 && latestDpd <= 90) {
                        consumerAccountSummaryDTO.setDpd31to90AllAccountsJoint(consumerAccountSummaryDTO.getDpd31to90AllAccountsJoint() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd31to90LiveAccountsJoint(consumerAccountSummaryDTO.getDpd31to90LiveAccountsJoint() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd31to90ClosedAccountsJoint(consumerAccountSummaryDTO.getDpd31to90ClosedAccountsJoint() + 1);
                        }
                    } else if (latestDpd > 90) {
                        consumerAccountSummaryDTO.setDpdGreaterThan90AllAccountsJoint(consumerAccountSummaryDTO.getDpdGreaterThan90AllAccountsJoint() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpdGreaterThan90LiveAccountsJoint(consumerAccountSummaryDTO.getDpdGreaterThan90LiveAccountsJoint() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpdGreaterThan90ClosedAccountsJoint(consumerAccountSummaryDTO.getDpdGreaterThan90ClosedAccountsJoint() + 1);
                        }
                    }
                }else if (ownershipIndicator.equals("Guarantor")){
                    if (latestDpd == 0) {
                        consumerAccountSummaryDTO.setDpd0AllAccountsGuarantor(consumerAccountSummaryDTO.getDpd0AllAccountsGuarantor() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd0LiveAccountsGuarantor(consumerAccountSummaryDTO.getDpd0LiveAccountsGuarantor() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd0ClosedAccountsGuarantor(consumerAccountSummaryDTO.getDpd0ClosedAccountsGuarantor() + 1);
                        }
                    } else if (latestDpd >= 1 && latestDpd <= 30) {
                        consumerAccountSummaryDTO.setDpd1to30AllAccountsGuarantor(consumerAccountSummaryDTO.getDpd1to30AllAccountsGuarantor() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd1to30LiveAccountsGuarantor(consumerAccountSummaryDTO.getDpd1to30LiveAccountsGuarantor() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd1to30ClosedAccountsGuarantor(consumerAccountSummaryDTO.getDpd1to30ClosedAccountsGuarantor() + 1);
                        }
                    } else if (latestDpd >= 31 && latestDpd <= 90) {
                        consumerAccountSummaryDTO.setDpd31to90AllAccountsGuarantor(consumerAccountSummaryDTO.getDpd31to90AllAccountsGuarantor() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpd31to90LiveAccountsGuarantor(consumerAccountSummaryDTO.getDpd31to90LiveAccountsGuarantor() + 1);
                        } else {
                            consumerAccountSummaryDTO.setDpd31to90ClosedAccountsGuarantor(consumerAccountSummaryDTO.getDpd31to90ClosedAccountsGuarantor() + 1);
                        }
                    } else if (latestDpd > 90) {
                        consumerAccountSummaryDTO.setDpdGreaterThan90AllAccountsGuarantor(consumerAccountSummaryDTO.getDpdGreaterThan90AllAccountsGuarantor() + 1);
                        if (isLive) {
                            consumerAccountSummaryDTO.setDpdGreaterThan90LiveAccountsGuarantor(consumerAccountSummaryDTO.getDpdGreaterThan90LiveAccountsGuarantor() + 1);

                        } else {
                            consumerAccountSummaryDTO.setDpdGreaterThan90ClosedAccountsGuarantor(consumerAccountSummaryDTO.getDpdGreaterThan90ClosedAccountsGuarantor() + 1);
                        }
                    }

                }
           }
        }
    }

    private static PaymentHistoryRecord getPaymentHistoryRecordWithHighestDpd(List<PaymentHistoryRecord> paymentHistoryRecordList) {
        PaymentHistoryRecord currentHighestPaymentHistoryRecord = null;
        for (PaymentHistoryRecord eachPaymentHistoryRecord: paymentHistoryRecordList) {
            if(eachPaymentHistoryRecord.getStatus() != null) {
                if(currentHighestPaymentHistoryRecord == null ) {
                    currentHighestPaymentHistoryRecord = new PaymentHistoryRecord();
                    setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                }
                else if(eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.SMA || eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.SUB || eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.DBT || eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.LSS ) {
                    updatePaymentHistoryBasedOnPriority(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                } else if (eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.XXX || eachPaymentHistoryRecord.getStatus() == AssetClassificationStatus.STD ){
                    if(currentHighestPaymentHistoryRecord.getStatus() == null && (currentHighestPaymentHistoryRecord.getDpd() == null || (currentHighestPaymentHistoryRecord.getDpd() == 0L))) {
                        setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                    }
                }
            } else if(eachPaymentHistoryRecord.getDpd() != null) {
                if(currentHighestPaymentHistoryRecord == null) {
                    currentHighestPaymentHistoryRecord = new PaymentHistoryRecord();
                    setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                }
                else if((currentHighestPaymentHistoryRecord.getStatus() == null || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.XXX || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.STD) && (currentHighestPaymentHistoryRecord.getDpd() == null)) {
                    setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                }
                else if((currentHighestPaymentHistoryRecord.getStatus() == null || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.XXX || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.STD) && (currentHighestPaymentHistoryRecord.getDpd() <= eachPaymentHistoryRecord.getDpd())) {
                    setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, eachPaymentHistoryRecord);
                }
            }
        }
        if(currentHighestPaymentHistoryRecord != null && currentHighestPaymentHistoryRecord.getDpd() != null && currentHighestPaymentHistoryRecord.getDpd() > 0L) {
            if(currentHighestPaymentHistoryRecord.getStatus() != null && (currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.XXX || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.STD)) {
                currentHighestPaymentHistoryRecord.setStatus(null);
            }
        }
        if(currentHighestPaymentHistoryRecord != null && currentHighestPaymentHistoryRecord.getStatus() != null && !(currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.XXX || currentHighestPaymentHistoryRecord.getStatus() == AssetClassificationStatus.STD)) {
            currentHighestPaymentHistoryRecord.setDpd(null);
        }
        return currentHighestPaymentHistoryRecord;
    }

    public static void updatePaymentHistoryBasedOnPriority(PaymentHistoryRecord currentHighestPaymentHistoryRecord, PaymentHistoryRecord paymentHistoryRecord) {
        int currentPriority = MappingRegistry.CIBIL_STATUS_PRIORITY_MAP.getOrDefault(currentHighestPaymentHistoryRecord.getStatus(), -2);
        int newPriority = MappingRegistry.CIBIL_STATUS_PRIORITY_MAP.getOrDefault(paymentHistoryRecord.getStatus(), -2);

        if (newPriority > currentPriority) {
            setPaymentHistoryRecord(currentHighestPaymentHistoryRecord, paymentHistoryRecord);
        }
    }

    private static void setPaymentHistoryRecord(PaymentHistoryRecord currentHighestPaymentHistoryRecord, PaymentHistoryRecord paymentHistoryRecord) {
        currentHighestPaymentHistoryRecord.setDate(paymentHistoryRecord.getDate());
        currentHighestPaymentHistoryRecord.setDpd(paymentHistoryRecord.getDpd());
        currentHighestPaymentHistoryRecord.setStatus(paymentHistoryRecord.getStatus());
    }


    private JPAQuery<EnvelopeResponse> createQueryForFilter() {

        QEnvelopeResponse qEnvelopeResponse = QEnvelopeResponse.envelopeResponse;
        QCombinedResponse qCombinedResponse = QCombinedResponse.combinedResponse;
        QApplicant qApplicant = QApplicant.applicant;
        QDsCibilBureauData qDsCibilBureauData = QDsCibilBureauData.dsCibilBureauData;
        QCibilBureauResponse qCibilBureauResponse = QCibilBureauResponse.cibilBureauResponse;
        QCreditReport qCreditReport = QCreditReport.creditReport;
        QAccount qAccount = QAccount.account;
        QAccountNonSummarySegment qAccountNonSummarySegment = QAccountNonSummarySegment.accountNonSummarySegment;

        return jpaQueryFactory.selectFrom(qEnvelopeResponse)
                .leftJoin(qEnvelopeResponse.combinedResponse, qCombinedResponse).fetchJoin()
                .leftJoin(qCombinedResponse.applicant, qApplicant).fetchJoin()
                .leftJoin(qApplicant.dsCibilBureauData, qDsCibilBureauData).fetchJoin()
                .leftJoin(qDsCibilBureauData.cibilBureauResponse, qCibilBureauResponse).fetchJoin()
                .leftJoin(qCibilBureauResponse.creditReport, qCreditReport).fetchJoin()
                .leftJoin(qCreditReport.accountList, qAccount).fetchJoin()
                .leftJoin(qAccount.accountNonSummarySegment, qAccountNonSummarySegment).fetchJoin();

    }

    @NotNull
    private static List<AddressInfoDTO> getAddressInfoDTOList(CreditReport creditReport) {
        List<AddressInfoDTO> addressInfoDTOList = new ArrayList<>();
        for (CreditReportAddress eachCreditReportAddress : creditReport.getCreditReportAddressList()) {
            AddressInfoDTO addressInfoDTO = new AddressInfoDTO();
            addressInfoDTO.setAddressLine1(eachCreditReportAddress.getAddressLine1());
            addressInfoDTO.setAddressLine2(eachCreditReportAddress.getAddressLine2());
            addressInfoDTO.setAddressLine3(eachCreditReportAddress.getAddressLine3());
            addressInfoDTO.setAddressLine4(eachCreditReportAddress.getAddressLine4());
            addressInfoDTO.setAddressLine5(eachCreditReportAddress.getAddressLine5());
            addressInfoDTO.setStateCode(MappingRegistry.getStateFromStateCode(eachCreditReportAddress.getStateCode()));
            addressInfoDTO.setPinCode(eachCreditReportAddress.getPinCode());
            addressInfoDTO.setCategory(eachCreditReportAddress.getAddressCategory());
            addressInfoDTO.setDateReported(getFormattedDateValue(eachCreditReportAddress.getDateReported()));
            addressInfoDTOList.add(addressInfoDTO);
        }
        return addressInfoDTOList;
    }
    @NotNull
    private List<AddressInfoDTO> getSecondaryAddressInfoDTOList(CreditReport secondaryCreditReport) {
        List<AddressInfoDTO> addressInfoDTOList = new ArrayList<>();
        for (CreditReportAddress eachCreditReportAddress : secondaryCreditReport.getCreditReportAddressList()) {
            AddressInfoDTO addressInfoDTO = new AddressInfoDTO();
            addressInfoDTO.setAddressLine1(eachCreditReportAddress.getAddressLine1());
            addressInfoDTO.setAddressLine2(eachCreditReportAddress.getAddressLine2());
            addressInfoDTO.setAddressLine3(eachCreditReportAddress.getAddressLine3());
            addressInfoDTO.setAddressLine4(eachCreditReportAddress.getAddressLine4());
            addressInfoDTO.setAddressLine5(eachCreditReportAddress.getAddressLine5());
            addressInfoDTO.setStateCode(MappingRegistry.getStateFromStateCode(eachCreditReportAddress.getStateCode()));
            addressInfoDTO.setPinCode(eachCreditReportAddress.getPinCode());
            addressInfoDTO.setCategory(eachCreditReportAddress.getAddressCategory());
            addressInfoDTO.setDateReported(getFormattedDateValue(eachCreditReportAddress.getDateReported()));
            addressInfoDTOList.add(addressInfoDTO);
        }
        return addressInfoDTOList;
    }

    @NotNull
    private static List<NameSegmentDTO> getNameSegmentDTOList(CreditReport secondaryCreditReport) {
        List<NameSegmentDTO> nameSegmentDTOList = new ArrayList<>();
        for (NameSegment eachNameSegment : secondaryCreditReport.getNameSegmentList()) {
            NameSegmentDTO newNameSegmentDTO = new NameSegmentDTO();
            newNameSegmentDTO.setConsumerName1(eachNameSegment.getConsumerName1());
            newNameSegmentDTO.setConsumerName2(eachNameSegment.getConsumerName2());
            newNameSegmentDTO.setConsumerName3(eachNameSegment.getConsumerName3());
            newNameSegmentDTO.setConsumerName4(eachNameSegment.getConsumerName4());
            newNameSegmentDTO.setConsumerName5(eachNameSegment.getConsumerName5());
            newNameSegmentDTO.setDateOfBirth(String.valueOf(eachNameSegment.getDateOfBirth()));
            newNameSegmentDTO.setGender(eachNameSegment.getGender());
            nameSegmentDTOList.add(newNameSegmentDTO);
        }
        return nameSegmentDTOList;
    }
    private static String getFormattedDateValue(String dateReported) {
        if (StringUtils.isBlank(dateReported) || dateReported.length() != 8) {
            return dateReported;
        }
        return dateReported.substring(0, 2) + "-" + dateReported.substring(2, 4) + "-" + dateReported.substring(4);
    }

    private List<AccountBorrowingDetailsDTO> calculateAccountBorrowingDetails(List<Account> accountList) {
        List<AccountBorrowingDetailsDTO> accountBorrowingDetailsDTOList = new ArrayList<>();
        for (Account eachAccount : accountList) {
            AccountBorrowingDetailsDTO accountBorrowingDetailsDTO = new AccountBorrowingDetailsDTO();
            accountBorrowingDetailsDTO.setTypeOfLoan(eachAccount.getAccountNonSummarySegment().getAccountType());
            accountBorrowingDetailsDTO.setSanctionedAmount(eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount());
            accountBorrowingDetailsDTO.setOutstandingAmount(eachAccount.getAccountNonSummarySegment().getCurrentBalance());
            accountBorrowingDetailsDTO.setCibilStatus(eachAccount.getAccountNonSummarySegment().getCreditFacility());
            accountBorrowingDetailsDTO.setSanctionedDate(eachAccount.getAccountNonSummarySegment().getDateOpenedOrDisbursed());
            accountBorrowingDetailsDTO.setBankNbfc(eachAccount.getAccountNonSummarySegment().getReportingMemberShortName());
            accountBorrowingDetailsDTO.setOwnership(eachAccount.getAccountNonSummarySegment().getOwnershipIndicator());
            accountBorrowingDetailsDTO.setOverdue(eachAccount.getAccountNonSummarySegment().getAmountOverdue());
            accountBorrowingDetailsDTO.setTenure(eachAccount.getAccountNonSummarySegment().getRepaymentTenure());
            this.calculateDpdSummary(eachAccount.getAccountNonSummarySegment().getPaymentHistoryRecordList(), accountBorrowingDetailsDTO);

            accountBorrowingDetailsDTO.setReportedDate(eachAccount.getAccountNonSummarySegment().getDateReportedAndCertified());
            accountBorrowingDetailsDTO.setEmi(eachAccount.getAccountNonSummarySegment().getEmiAmount());
            accountBorrowingDetailsDTO.setInterestRate(eachAccount.getAccountNonSummarySegment().getRateOfInterest());
            accountBorrowingDetailsDTO.setPaymentHistoryRecordList(eachAccount.getAccountNonSummarySegment().getPaymentHistoryRecordList());
            accountBorrowingDetailsDTO.setComment(eachAccount.getComment());
            accountBorrowingDetailsDTO.setDuplicate(eachAccount.isDuplicate());
            accountBorrowingDetailsDTO.setAddInTotal(eachAccount.isAddInTotal());
            accountBorrowingDetailsDTO.setSuitFiledOrWilfulDefault(eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault());
            accountBorrowingDetailsDTO.setCreditFacilityStatus(eachAccount.getAccountNonSummarySegment().getCreditFacility());
            accountBorrowingDetailsDTO.setWrittenOffAmountPrincipal(eachAccount.getAccountNonSummarySegment().getWrittenOffAmountPrincipal());
            AuccountSummerySegmentDTO accountSummerySegment=cibilConsumerMapper.toAccountSummerySegment(eachAccount.getAccountNonSummarySegment());
            accountBorrowingDetailsDTO.setAuccountSummerySegmentDTO(accountSummerySegment);
            accountBorrowingDetailsDTO.setAccountId(eachAccount.getId());

            EMIMaster byTypeOfLoan = emiMasterRepository.findByTypeOfLoan(eachAccount.getAccountNonSummarySegment().getAccountType());
            if (byTypeOfLoan == null) {
                log.warn("EMIMaster not found for Account Type: {}", eachAccount.getAccountNonSummarySegment().getAccountType());
                accountBorrowingDetailsDTO.setEmiSystem(0.0);
            } else {
                accountBorrowingDetailsDTO.setEmiSystem(calculateSystemGeneratedEmi(eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount(), byTypeOfLoan.getInterest(), byTypeOfLoan.getTenure()));
            }
            accountBorrowingDetailsDTOList.add(accountBorrowingDetailsDTO);
        }
        return accountBorrowingDetailsDTOList;
    }
    public static Double calculateSystemGeneratedEmi(Double principal, Integer annualRateOfInterest, Integer tenureInMonths) {
        if (principal == null || annualRateOfInterest == null || tenureInMonths == null || tenureInMonths <= 0) {
            return 0.0;
        }

        // Correctly calculate the monthly interest rate as a BigDecimal
        BigDecimal monthlyRate = BigDecimal.valueOf(annualRateOfInterest / 12.0 / 100.0);

        double pow = Math.pow(1 + monthlyRate.doubleValue(), tenureInMonths);
        return (principal * monthlyRate.doubleValue() * pow)
                / (pow - 1);
    }
    public void calculateDpdSummary(List<PaymentHistoryRecord> paymentHistoryRecordList, AccountBorrowingDetailsDTO accountBorrowingDetailsDTO) {
        long last12MonthsDpd = 0L, overallDpd = 0L, monthCount = 0L;
        Date last12MonthsDpdDate = null, overallDpdDate = null;
        for (PaymentHistoryRecord eachPaymentHistoryRecord : paymentHistoryRecordList) {
            long currentMonthDPD = eachPaymentHistoryRecord.getDpd() == null ? 0 : eachPaymentHistoryRecord.getDpd();
            monthCount += 1;
            if (last12MonthsDpd < currentMonthDPD && monthCount <= 12) {
                last12MonthsDpd = currentMonthDPD;
                last12MonthsDpdDate = eachPaymentHistoryRecord.getDate();
            }
            if (overallDpd < currentMonthDPD) {
                overallDpd = currentMonthDPD;
                overallDpdDate = eachPaymentHistoryRecord.getDate();
            }
        }
        accountBorrowingDetailsDTO.setLast12MonthsDPD(last12MonthsDpd);
        accountBorrowingDetailsDTO.setOverallDPD(overallDpd);
        accountBorrowingDetailsDTO.setDelayInMonthsOverall(overallDpd / 30);
        accountBorrowingDetailsDTO.setDelayInMonthsLast12Months(last12MonthsDpd / 30);
        accountBorrowingDetailsDTO.setOverallDPDDate(overallDpdDate);
        accountBorrowingDetailsDTO.setLast12MonthsDPDDate(last12MonthsDpdDate);
    }

    private void calculateAccountsOpenedTime(Date cibilReportProcessedDate, Date dateOpenedFormated, AccountInquiriesHistoricalDTO accountInquiriesHistoricalDTO) {
        LocalDate currentDate = LocalDate.now();

        if (dateOpenedFormated == null) {
            return;
        }
        LocalDate dateOpened = dateOpenedFormated.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysDifferenceWithCurrentDate = ChronoUnit.DAYS.between(dateOpened, currentDate);

        if (daysDifferenceWithCurrentDate <= 30) {
            accountInquiriesHistoricalDTO.setAccountsOpenedLast30DaysFromCurrentDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast30DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 60) {
            accountInquiriesHistoricalDTO.setAccountsOpenedLast60DaysFromCurrentDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast60DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 120) {
            accountInquiriesHistoricalDTO.setAccountsOpenedLast120DaysFromCurrentDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast120DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 365) {
            accountInquiriesHistoricalDTO.setAccountsOpenedLast365DaysFromCurrentDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast365DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 730) {
            accountInquiriesHistoricalDTO.setAccountsOpenedLast730DaysFromCurrentDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast730DaysFromCurrentDate() + 1);
        }

        if (cibilReportProcessedDate != null) {
            LocalDate cibilReportProcessedDateLocal = cibilReportProcessedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long daysDifferenceWithCibilReportDate = ChronoUnit.DAYS.between(dateOpened, cibilReportProcessedDateLocal);

            if (daysDifferenceWithCibilReportDate <= 30) {
                accountInquiriesHistoricalDTO.setAccountsOpenedLast30DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast30DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 60) {
                accountInquiriesHistoricalDTO.setAccountsOpenedLast60DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast60DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 120) {
                accountInquiriesHistoricalDTO.setAccountsOpenedLast120DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast120DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 365) {
                accountInquiriesHistoricalDTO.setAccountsOpenedLast365DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast365DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 730) {
                accountInquiriesHistoricalDTO.setAccountsOpenedLast730DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getAccountsOpenedLast730DaysFromCibilReportDate() + 1);
            }
        }
    }

    public static long calculateAge(Date dateOfBirth) {
        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    private void calculateLastEnquiryTime(Date cibilReportProcessedDate, Date dateEnquiredLong, AccountInquiriesHistoricalDTO accountInquiriesHistoricalDTO) {
        LocalDate currentDate = LocalDate.now();
        if (dateEnquiredLong == null) {
            return;
        }
        LocalDate dateEnquired = dateEnquiredLong.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long daysDifferenceWithCurrentDate = ChronoUnit.DAYS.between(dateEnquired, currentDate);

        if (daysDifferenceWithCurrentDate <= 30) {
            accountInquiriesHistoricalDTO.setInquiriesLast30DaysFromCurrentDate(accountInquiriesHistoricalDTO.getInquiriesLast30DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 60) {
            accountInquiriesHistoricalDTO.setInquiriesLast60DaysFromCurrentDate(accountInquiriesHistoricalDTO.getInquiriesLast60DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 120) {
            accountInquiriesHistoricalDTO.setInquiriesLast120DaysFromCurrentDate(accountInquiriesHistoricalDTO.getInquiriesLast120DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 365) {
            accountInquiriesHistoricalDTO.setInquiriesLast365DaysFromCurrentDate(accountInquiriesHistoricalDTO.getInquiriesLast365DaysFromCurrentDate() + 1);
        }
        if (daysDifferenceWithCurrentDate <= 730) {
            accountInquiriesHistoricalDTO.setInquiriesLast730DaysFromCurrentDate(accountInquiriesHistoricalDTO.getInquiriesLast730DaysFromCurrentDate() + 1);
        }

        if (cibilReportProcessedDate != null) {
            LocalDate cibilReportProcessedDateLocal = cibilReportProcessedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long daysDifferenceWithCibilReportDate = ChronoUnit.DAYS.between(dateEnquired, cibilReportProcessedDateLocal);

            if (daysDifferenceWithCibilReportDate <= 30) {
                accountInquiriesHistoricalDTO.setInquiriesLast30DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getInquiriesLast30DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 60) {
                accountInquiriesHistoricalDTO.setInquiriesLast60DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getInquiriesLast60DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 120) {
                accountInquiriesHistoricalDTO.setInquiriesLast120DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getInquiriesLast120DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 365) {
                accountInquiriesHistoricalDTO.setInquiriesLast365DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getInquiriesLast365DaysFromCibilReportDate() + 1);
            }
            if (daysDifferenceWithCibilReportDate <= 730) {
                accountInquiriesHistoricalDTO.setInquiriesLast730DaysFromCibilReportDate(accountInquiriesHistoricalDTO.getInquiriesLast730DaysFromCibilReportDate() + 1);
            }
        }
    }

    public NonCibilAccount addNonCibilAccount(NonCibilAccount nonCibilAccount) {
        if (nonCibilAccount.getRequestId() == null) {
            log.error("[CibilConsumerServiceImpl][addNonCibilAccount] Could not save account data. Request id can not be empty");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id can not be empty.");
        }
        Optional<EnvelopeResponse> envelopeResponse = envelopeResponseRepository.findByRequestId(nonCibilAccount.getRequestId());
        if (envelopeResponse.isEmpty()) {
            log.error(String.format("[CibilConsumerServiceImpl][addNonCibilAccount] Could not save account data. Cibil data against the request id %s not found.", nonCibilAccount.getRequestId()));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Cibil data against the request id %s not found.", nonCibilAccount.getRequestId()));
        } else {
            nonCibilAccount.setArchived(false);
            NonCibilAccount savedNonCibilAccount = nonCibilAccountRepository.save(nonCibilAccount);
            log.info("[CibilConsumerServiceImpl][addNonCibilAccount] Account data saved successfully.");
            return savedNonCibilAccount;
        }

    }

    public NonCibilAccount updateNonCibilAccount(NonCibilAccount nonCibilAccount) {
        if (nonCibilAccount.getRequestId() == null || nonCibilAccount.getId() == null) {
            log.error("[CibilConsumerServiceImpl][updateNonCibilAccount] Could not update account data. Request id can not be empty");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id or id can not be empty.");
        }
        Optional<NonCibilAccount> savedNonCibilAccount = nonCibilAccountRepository.findByRequestIdAndIdAndArchived(nonCibilAccount.getRequestId(), nonCibilAccount.getId(), false);
        if (savedNonCibilAccount.isEmpty()) {
            log.error(String.format("[CibilConsumerServiceImpl][updateNonCibilAccount] Could not update account data. Account with request id %s and id %s not found.", nonCibilAccount.getRequestId(), nonCibilAccount.getId()));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Account with request id %s and id %s not found.", nonCibilAccount.getRequestId(), nonCibilAccount.getId()));
        }
        nonCibilAccount.setId(savedNonCibilAccount.get().getId());
        nonCibilAccount.setArchived(false);
        NonCibilAccount updatedNonCibilAccount = nonCibilAccountRepository.save(nonCibilAccount);
        log.info("[CibilConsumerServiceImpl][updateNonCibilAccount] Account data updated successfully.");
        return updatedNonCibilAccount;
    }

    public void deleteNonCibilAccount(UUID id) {
        if (id == null) {
            log.error("[CibilConsumerServiceImpl][deleteNonCibilAccount] Could not delete account data. Request id can not be empty");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request id can not be empty.");
        }
        Optional<NonCibilAccount> nonCibilAccountToDelete = nonCibilAccountRepository.findByIdAndArchived(id, false);
        if (nonCibilAccountToDelete.isEmpty()) {
            log.error(String.format("[CibilConsumerServiceImpl][deleteNonCibilAccount] Could not delete account data. Account with id %s not found.", id));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Account with id %s not found.", id));
        }
        nonCibilAccountToDelete.get().setArchived(true);
        nonCibilAccountRepository.save(nonCibilAccountToDelete.get());
        log.info("[CibilConsumerServiceImpl][deleteNonCibilAccount] Account deleted successfully.");
    }

    @Override
    public AccountBorrowingDetailsDTO updateCibilAccountDetails(AccountBorrowingDetailsDTO accountBorrowingDetailsDTO, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            log.error(String.format("[CibilConsumerServiceImpl][updateCibilAccountDetails] Could not update account details. Account with id %s not found.", id));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Account with id %s not found.", id));
        }
        account.get().setDuplicate(accountBorrowingDetailsDTO.isDuplicate());
        account.get().setComment(accountBorrowingDetailsDTO.getComment());
        account.get().setAddInTotal(accountBorrowingDetailsDTO.isAddInTotal());
        account.get().getAccountNonSummarySegment().setAccountType(accountBorrowingDetailsDTO.getTypeOfLoan());
        account.get().getAccountNonSummarySegment().setRepaymentTenure(accountBorrowingDetailsDTO.getTenure());
        account.get().getAccountNonSummarySegment().setEmiAmount(accountBorrowingDetailsDTO.getEmi());
        account.get().getAccountNonSummarySegment().setRateOfInterest(accountBorrowingDetailsDTO.getInterestRate());
        AccountBorrowingDetailsDTO updatedAccountBorrowingDetailsDTO = cibilConsumerMapper.toAccountBorrowingDetailsDTO(accountRepository.save(account.get()));
        log.info("[CibilConsumerServiceImpl][updateCibilAccountDetails] Account updated successfully");
        return updatedAccountBorrowingDetailsDTO;
    }

    @Override
    public void saveRawData(String requestId, DataSaveDTO dataSaveDTO, String ipAddress, String createdBy) {

        RawData rawData = new RawData();
        if (dataSaveDTO.getCcFlag() != null && dataSaveDTO.getCcFlag().equalsIgnoreCase(CcFlag.CONSUMER.toString())) {
            LosDetailsRaw losDetailsRaw = cibilConsumerMapper.toLosDetailsRaw(dataSaveDTO.getLosDetailsDTO());
            losDetailsRaw.setRequestId(requestId);
            rawData.setLosDetailsRaw(losDetailsRaw);
        }
        rawData.setIpAddress(ipAddress);
        rawData.setCreatedBy(createdBy);
        rawData.setRequestId(requestId);
        rawData.setRawXmlString(dataSaveDTO.getRawXmlString());
        rawData.setCcFlag(dataSaveDTO.getCcFlag());
        rawData.setDateRecorded(LocalDateTime.now());
        rawDataRepository.save(rawData);
        log.info("[CibilConsumerServiceImpl][saveRawData] Raw data saved successfully.");
    }

    @Override
    public ConsumerProfileSummaryDTO getConsumerProfileSummaryView(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO, String searchCriteria,String searchString,String searchFor) {
        if (requestId == null) {
            log.error("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not save consumer data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request Id not found.");
        }
        EnvelopeResponse envelopeResponse = searchConsumerDetails(requestId, accountBorrowingDetailsSearchRequestDTO);

        if (envelopeResponse.getCombinedResponse() == null || envelopeResponse.getCombinedResponse().getApplicant() == null || envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData() == null || envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse() == null) {
            log.error(String.format("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not fetch consumer summary. Data against the request id %s is not populated.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s is not populated.", requestId));
        }
        ConsumerProfileSummaryDTO consumerProfileSummaryDTO = new ConsumerProfileSummaryDTO();

        CreditReport creditReport = envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse().getCreditReport();
        Applicant applicant = envelopeResponse.getCombinedResponse().getApplicant();

        if (creditReport == null) {
            log.error(String.format("[CibilConsumerServiceImpl][getConsumerProfileSummary] Could not fetch consumer summary. Data against the request id %s is not populated.", requestId));
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s is not populated.", requestId));
        }

        Date cibilReportProcessedDate = null;
        if (creditReport.getCreditReportHeaderList().get(0) != null) {
            cibilReportProcessedDate = creditReport.getCreditReportHeaderList().get(0).getDateProcessed();
        }

        if (!creditReport.getNameSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setName(StringUtils.joinWith(StringUtils.SPACE,
                    applicant.getApplicantFirstName(), applicant.getApplicantMiddleName(), applicant.getApplicantLastName()));
            consumerProfileSummaryDTO.setDateOfBirth(creditReport.getNameSegmentList().get(0).getDateOfBirth());
            consumerProfileSummaryDTO.setGender(creditReport.getNameSegmentList().get(0).getGender());
            consumerProfileSummaryDTO.setAge(calculateAge(creditReport.getNameSegmentList().get(0).getDateOfBirth()));
        }
        if (!creditReport.getScoreSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setScore(creditReport.getScoreSegmentList().get(0).getScore());
        }
        if (!creditReport.getCreditReportHeaderList().isEmpty()) {
            consumerProfileSummaryDTO.setControlNumber(creditReport.getCreditReportHeaderList().get(0).getEnquiryControlNumber());
        }
        if (!creditReport.getIdSegmentList().isEmpty()) {
            List<IdentificationDTO> identificationDTOList = new ArrayList<>();
            for (IdSegment eachIdSegment : creditReport.getIdSegmentList()) {
                IdentificationDTO newIdentificationDTO = new IdentificationDTO();
                newIdentificationDTO.setIdType(eachIdSegment.getIdType());
                newIdentificationDTO.setIdNumber(eachIdSegment.getIdNumber());
                identificationDTOList.add(newIdentificationDTO);
            }
            consumerProfileSummaryDTO.setIdentification(identificationDTOList);
        }
        if (!creditReport.getTelephoneSegmentList().isEmpty()) {
            List<String> telephoneList = new ArrayList<>();
            for (TelephoneSegment eachTelephoneSegment : creditReport.getTelephoneSegmentList()) {
                telephoneList.add(eachTelephoneSegment.getTelephoneNumber());
            }
            consumerProfileSummaryDTO.setTelephoneNumbers(telephoneList);
        }
        if (!creditReport.getEmailContactSegmentList().isEmpty()) {
            consumerProfileSummaryDTO.setEmails(creditReport.getEmailContactSegmentList().stream().map(EmailContactSegment::getEmailId).collect(Collectors.toList()));
        }
        if (!creditReport.getCreditReportAddressList().isEmpty()) {
            List<AddressInfoDTO> addressInfoDTOList = getAddressInfoDTOList(creditReport);
            consumerProfileSummaryDTO.setAddresses(addressInfoDTOList);
        }
        List<Account> accountList = List.of();
        if(searchFor.equals("VigilantSummery")) {
            accountList = getAccountsVigilantSummery(creditReport.getAccountList(),searchCriteria,searchString);
        } else if (searchFor.equals("LoanInformation") && !searchCriteria.contains("status") && !searchCriteria.contains("sub") && !searchCriteria.contains("suit")) {
            accountList=getAccountsLoanInfo(creditReport.getAccountList(),searchCriteria,searchString);
        }else if(searchCriteria.contains("status")){
            accountList=getAccountForStatus(creditReport.getAccountList(),searchCriteria,searchString);
        }else {
            accountList=getAccountsForSuitSUBDBT(creditReport.getAccountList(),searchCriteria,searchString);
        }
        ConsumerAccountSummaryDTO consumerAccountSummaryDTO = new ConsumerAccountSummaryDTO();
        AccountInquiriesHistoricalDTO accountInquiriesHistoricalDTO = new AccountInquiriesHistoricalDTO();

        if (!accountList.isEmpty()) {
            long liveAccounts = 0, closedAccounts = 0;
            BigDecimal sanctionedAmount = BigDecimal.ZERO, overdueAmount = BigDecimal.ZERO, pos = BigDecimal.ZERO;
            consumerAccountSummaryDTO.setTotalLoansOpened((long)accountList.size());

            setLoanInfoData(accountList,consumerAccountSummaryDTO);

            for (Account eachAccount : accountList) {
                updateDPDCountForAccounts(consumerAccountSummaryDTO, eachAccount);

                if (!eachAccount.getAccountNonSummarySegment().isLiveAccount()) {
                    closedAccounts += 1;
                } else {
                    liveAccounts += 1;
                    sanctionedAmount = sanctionedAmount.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount() == null ? 0 : eachAccount.getAccountNonSummarySegment().getHighCreditOrSanctionedAmount()));
                    overdueAmount = overdueAmount.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getAmountOverdue() == null ? 0 : eachAccount.getAccountNonSummarySegment().getAmountOverdue()));
                    pos = pos.add(BigDecimal.valueOf(eachAccount.getAccountNonSummarySegment().getCurrentBalance() == null ? 0 : eachAccount.getAccountNonSummarySegment().getCurrentBalance()));
                }
                Date dateOpened = eachAccount.getAccountNonSummarySegment().getDateOpenedOrDisbursed();
                calculateAccountsOpenedTime(cibilReportProcessedDate, dateOpened, accountInquiriesHistoricalDTO);
            }
            List<AuccountSummerySegmentDTO> auccountSummerySegmentDTOS = new ArrayList<>();
            for (Account eAccount:creditReport.getAccountList()){
                auccountSummerySegmentDTOS.add(cibilConsumerMapper.toAccountSummerySegment(eAccount.getAccountNonSummarySegment()));

            }
            consumerAccountSummaryDTO.setIndividual(allIndividual);
            consumerAccountSummaryDTO.setJoint(allJoint);
            consumerAccountSummaryDTO.setGuarantor(allGuarantor);
            consumerAccountSummaryDTO.setLiveLoans(liveAccounts);
            consumerAccountSummaryDTO.setClosedLoans(closedAccounts);
            consumerAccountSummaryDTO.setSanctionedAmountLiveLoans(sanctionedAmount);
            consumerAccountSummaryDTO.setOverdueAmountLiveLoans(overdueAmount);
            consumerAccountSummaryDTO.setPosLiveLoans(pos);
            List<AccountBorrowingDetailsDTO> accountBorrowingDetailsDTOList = calculateAccountBorrowingDetails(accountList);
            List<AccountBorrowingDetailsDTO> filteredAccountBorrowingDetailsDTOList = filterAccountBorrowingDetails(accountBorrowingDetailsDTOList, accountBorrowingDetailsSearchRequestDTO);

            consumerProfileSummaryDTO.setAccountBorrowingDetailsDTOList(filteredAccountBorrowingDetailsDTOList);
            consumerProfileSummaryDTO.setAuccountSummerySegmentDTOS(auccountSummerySegmentDTOS);
        }
        consumerProfileSummaryDTO.setAccountSummary(consumerAccountSummaryDTO);

        if (!creditReport.getEnquiryList().isEmpty()) {
            for (Enquiry eachEnquiry : creditReport.getEnquiryList()) {
                Date dateEnquired = eachEnquiry.getDateOfEnquiryFields();
                calculateLastEnquiryTime(cibilReportProcessedDate, dateEnquired, accountInquiriesHistoricalDTO);
            }
        }
        consumerProfileSummaryDTO.setAccountInquiriesHistoricalData(accountInquiriesHistoricalDTO);
        consumerProfileSummaryDTO.setNonCibilAccountList(this.getNonCibilAccount(requestId));
        Optional<LosDetails> losDetails = getLosDetails(requestId);
        losDetails.ifPresent(details -> consumerProfileSummaryDTO.setLosDetailsDTO(cibilConsumerMapper.toLosDetailsDTO(details)));
        return consumerProfileSummaryDTO;
    }

    private List<Account> getAccountsForSuitSUBDBT(List<Account> accountList, String searchCriteria, String searchString) {
        List<Account> accounts = new ArrayList<>();

        for (Account account : accountList) {
            AccountNonSummarySegment segment = account.getAccountNonSummarySegment();
            if (segment == null) {
                continue;
            }

            boolean isLive = segment.isLiveAccount();
            String ownershipIndicator = segment.getOwnershipIndicator();
            String paymentHistory1d = segment.getPaymentHistory1();
            String paymentHistory2 = segment.getPaymentHistory2() != null ? segment.getPaymentHistory2() : "";
            boolean subDBtFlag = containsAny(paymentHistory1d, "SUB", "DBT") || containsAny(paymentHistory2, "SUB", "DBT");
            boolean suitFlag = "Suit filed".equals(segment.getSuitFiledOrWilfulDefault());

            if (matchesOwnership(ownershipIndicator, "Individual")) {
                evaluateAndAdd(accounts, searchCriteria, searchString, subDBtFlag, suitFlag, isLive, account, "Ind");
            } else if (matchesOwnership(ownershipIndicator, "Joint")) {
                evaluateAndAdd(accounts, searchCriteria, searchString, subDBtFlag, suitFlag, isLive, account, "Joint");
            } else if (matchesOwnership(ownershipIndicator, "Guarantor")) {
                evaluateAndAdd(accounts, searchCriteria, searchString, subDBtFlag, suitFlag, isLive, account, "Guarantor");
            }
        }

        return accounts;
    }

    private void evaluateAndAdd(List<Account> accounts, String searchCriteria, String searchString, boolean subDBtFlag,
                                boolean suitFlag, boolean isLive, Account account, String ownershipSuffix) {
        if (searchCriteria.equals("subOpen" + ownershipSuffix) && searchString.equals("subStandOpen" + ownershipSuffix)) {
            if (subDBtFlag) accounts.add(account);
        } else if (searchCriteria.equals("suitOpen" + ownershipSuffix) && searchString.equals("suitCountOpen" + ownershipSuffix)) {
            if (suitFlag) accounts.add(account);
        } else if (searchCriteria.equals("subLive" + ownershipSuffix) && searchString.equals("subStandLive" + ownershipSuffix) && isLive) {
            if (subDBtFlag) accounts.add(account);
        } else if (searchCriteria.equals("suitLive" + ownershipSuffix) && searchString.equals("suitCountLive" + ownershipSuffix) && isLive) {
            if (suitFlag) accounts.add(account);
        } else if (searchCriteria.equals("subClose" + ownershipSuffix) && searchString.equals("subStandClose" + ownershipSuffix) && !isLive) {
            if (subDBtFlag) accounts.add(account);
        } else if (searchCriteria.equals("suitClose" + ownershipSuffix) && searchString.equals("suitCountClose" + ownershipSuffix) && !isLive) {
            if (suitFlag) accounts.add(account);
        }
    }
    private boolean matchesOwnership(String ownershipIndicator, String expectedOwnership) {
        return expectedOwnership.equals(ownershipIndicator);
    }

    private boolean containsAny(String input, String... keywords) {
        return Arrays.stream(keywords).anyMatch(input::contains);
    }


    private List<Account> getAccountForStatus(List<Account> accountList, String searchCriteria, String searchString) {
        List<Account> accounts = new ArrayList<>();
        String[] keywords = {"Settlement", "Settled", "Restructured Loan"};

        for (Account account : accountList) {
            AccountNonSummarySegment segment = account.getAccountNonSummarySegment();
            if (segment == null) {
                continue;
            }

            boolean isLive = segment.isLiveAccount();
            String ownershipIndicator = segment.getOwnershipIndicator();
            String creditFacility = segment.getCreditFacility();

            boolean writtenOffFlag = "Written-off".equals(creditFacility);
            boolean wilFullFlag = "Wilful Defaulter".equals(creditFacility);
            boolean settleFlag = creditFacility != null &&
                    Arrays.stream(keywords).anyMatch(creditFacility::contains);

            if (matchesCriteria(ownershipIndicator, "Individual", searchCriteria, "statusInd")) {
                evaluateAndAdd(accounts, searchString, wilFullFlag, writtenOffFlag, settleFlag, isLive, account);
            } else if (matchesCriteria(ownershipIndicator, "Joint", searchCriteria, "statusJoint")) {
                evaluateAndAdd(accounts, searchString, wilFullFlag, writtenOffFlag, settleFlag, isLive, account);
            } else if (matchesCriteria(ownershipIndicator, "Guarantor", searchCriteria, "statusGuarantor")) {
                evaluateAndAdd(accounts, searchString, wilFullFlag, writtenOffFlag, settleFlag, isLive, account);
            }
        }

        return accounts;
    }

    private boolean matchesCriteria(String ownershipIndicator, String ownershipType, String searchCriteria, String expectedCriteria) {
        return ownershipType.equals(ownershipIndicator) && expectedCriteria.equals(searchCriteria);
    }

    private void evaluateAndAdd(List<Account> accounts, String searchString, boolean wilFullFlag, boolean writtenOffFlag,
                                boolean settleFlag, boolean isLive, Account account) {
        switch (searchString) {
            case "wilfulOpenInd":
            case "wilfulOpenJoint":
            case "wilfulOpenGuarantor":
                if (wilFullFlag) accounts.add(account);
                break;
            case "writtenOffOpenInd":
            case "writtenOffOpenJoint":
            case "WrittenOffOpenGuarantor":
                if (writtenOffFlag) accounts.add(account);
                break;
            case "settleCountOpenInd":
            case "settleCountOpenJoint":
            case "settleCountOpenguarantor":
                if (settleFlag) accounts.add(account);
                break;
            case "wilfulLiveInd":
            case "wilfulLiveJoint":
            case "wilfulLiveGuarantor":
                if (wilFullFlag && isLive) accounts.add(account);
                break;
            case "writtenOffLiveInd":
            case "WrittenOffLiveJoint":
            case "WrittenOffLiveGuarantor":
                if (writtenOffFlag && isLive) accounts.add(account);
                break;
            case "settleCountLiveInd":
            case "settleCountLiveJoint":
            case "settleCountLiveguarantor":
                if (settleFlag && isLive) accounts.add(account);
                break;
            case "wilfulCloseInd":
            case "wilfulCloseJoint":
            case "wilfulCloseGuarantor":
                if (wilFullFlag && !isLive) accounts.add(account);
                break;
            case "writtenOffCloseInd":
            case "writtenOffCloseJoint":
            case "writtenOffCloseGuarantor":
                if (writtenOffFlag && !isLive) accounts.add(account);
                break;
            case "settleCountCloseInd":
            case "settleCountCloseJoint":
            case "settleCountCloseguarantor":
                if (settleFlag && !isLive) accounts.add(account);
                break;
            default:
                break;
        }
    }

    private List<Account> getAccountsLoanInfo(List<Account> accountList, String searchCriteria, String searchString) {
        List<Account> accounts = new ArrayList<>();
        for (Account account : accountList) {
            AccountNonSummarySegment segment = account.getAccountNonSummarySegment();
            if (segment == null) continue;

            List<PaymentHistoryRecord> paymentHistoryRecordList = segment.getPaymentHistoryRecordList();
            boolean isLive = segment.isLiveAccount();
            String ownershipIndicator = segment.getOwnershipIndicator();
            long latestDpd = getLatestDpd(paymentHistoryRecordList);

            if (latestDpd == -1) continue;

            if ("1to90plus".equals(searchCriteria) && "alloverdue".equals(searchString)) {
                if (latestDpd > 0) { // Exclude accounts with 0 DPD
                    accounts.add(account);
                }
            } else if ("Individual".equals(ownershipIndicator) && "dpdInd".equals(searchCriteria)) {
                handleCriteria(accounts, account, latestDpd, isLive, searchString, "0DPDAll", "0DPDLive", "0DPDClosed",
                        "1to30DPDAll", "1to30DPDLive", "1to30DPDClosed", "31to90DPDAll", "31to90DPDLive", "31to90DPDClosed",
                        "90DPDAll", "90DPDLive", "90DPDClosed", "DPDIndAll", "TotalClosedDPD", "TotalLiveAccountsDPD");
            } else if ("Joint".equals(ownershipIndicator) && "dpdJoint".equals(searchCriteria)) {
                handleCriteria(accounts, account, latestDpd, isLive, searchString, "0DPDAllJoint", "0DPDLiveJoint", "0DPDClosedJoint",
                        "1to30DPDAllJoint", "1to30DPDLiveJoint", "1to30DPDClosedJoint", "31to90DPDAllJoint", "31to90DPDLiveJoint",
                        "31to90DPDClosedJoint", "90DPDAllJoint", "90DPDLiveJoint", "90DPDClosedJoint", "TotalJointAllDPD",
                        "TotalClosedJointDPD", "TotalLiveJointDPD");
            } else if ("Guarantor".equals(ownershipIndicator) && "dpdGuarantor".equals(searchCriteria)) {
                handleCriteria(accounts, account, latestDpd, isLive, searchString, "0DPDAllGuarantor", "0DPDLiveGuarantor", "0DPDClosedGuarantor",
                        "1to30DPDAllGuarantor", "1to30DPDLiveGuarantor", "1to30DPDClosedGuarantor", "31to90DPDAllGuarantor",
                        "31to90DPDLiveGuarantor", "31to90DPDClosedGuarantor", "90DPDAllGuarantor", "90DPDLiveGuarantor",
                        "90DPDClosedGuarantor", "TotalGuarantorDPD", "TotalClosedGuarantorDPD", "TotalLiveGuarantorDPD");
            }
        }
        return accounts;
    }

    private void handleCriteria(List<Account> accounts, Account account, long latestDpd, boolean isLive, String searchString,
                                String zeroAll, String zeroLive, String zeroClosed, String oneToThirtyAll, String oneToThirtyLive,
                                String oneToThirtyClosed, String thirtyOneToNinetyAll, String thirtyOneToNinetyLive,
                                String thirtyOneToNinetyClosed, String greaterThanNinetyAll, String greaterThanNinetyLive,
                                String greaterThanNinetyClosed, String dpdAll, String totalClosed, String totalLive) {
        // Existing range handling logic
        if (latestDpd == 0) {
            if (matchesCriteria(searchString, zeroAll, zeroLive, zeroClosed, isLive)) accounts.add(account);
        } else if (latestDpd >= 1 && latestDpd <= 30) {
            if (matchesCriteria(searchString, oneToThirtyAll, oneToThirtyLive, oneToThirtyClosed, isLive)) accounts.add(account);
        } else if (latestDpd >= 31 && latestDpd <= 90) {
            if (matchesCriteria(searchString, thirtyOneToNinetyAll, thirtyOneToNinetyLive, thirtyOneToNinetyClosed, isLive)) accounts.add(account);
        } else if (latestDpd > 90) {
            if (matchesCriteria(searchString, greaterThanNinetyAll, greaterThanNinetyLive, greaterThanNinetyClosed, isLive)) accounts.add(account);
        }

        if (dpdAll != null && searchString.equals(dpdAll)) {
            accounts.add(account);
        } else if (totalClosed != null && searchString.equals(totalClosed) && !isLive) {
            accounts.add(account);
        } else if (totalLive != null && searchString.equals(totalLive) && isLive) {
            accounts.add(account);
        }
    }

    private boolean matchesCriteria(String searchString, String all, String live, String closed, boolean isLive) {
        return searchString.equals(all) || (isLive && searchString.equals(live)) || (!isLive && searchString.equals(closed));
    }

    private long getLatestDpd(List<PaymentHistoryRecord> paymentHistoryRecordList) {
        if (paymentHistoryRecordList.isEmpty()) return -1;
        PaymentHistoryRecord paymentHistoryRecord = getPaymentHistoryRecordWithHighestDpd(paymentHistoryRecordList);
        if (paymentHistoryRecord == null || paymentHistoryRecord.getDpd() == null) return -1;
        return paymentHistoryRecord.getDpd();
    }

    private List<Account> getAccountsVigilantSummery(List<Account> accountList,String searchCriteria,String searchString) {
        if ("SUB".equals(searchCriteria)) {
            accountList = accountList.stream()
                    .filter(a ->
                            a.getAccountNonSummarySegment() != null &&
                                    a.getAccountNonSummarySegment().getPaymentHistory1() != null &&
                                    a.getAccountNonSummarySegment().getPaymentHistory1().contains("SUB")
                    )
                    .toList();
        } else if ("DBT".equals(searchCriteria)) {
            accountList = accountList.stream()
                    .filter(a ->
                            a.getAccountNonSummarySegment() != null &&
                                    a.getAccountNonSummarySegment().getPaymentHistory1() != null &&
                                    a.getAccountNonSummarySegment().getPaymentHistory1().contains("DBT")
                    )
                    .toList();
        } else if ("status".equals(searchCriteria)) {
            if ("Wilful Defaulter".equals(searchString)) {
                accountList = accountList.stream()
                        .filter(a ->
                                a.getAccountNonSummarySegment() != null &&
                                        a.getAccountNonSummarySegment().getCreditFacility() != null &&
                                        "Wilful Defaulter".equals(a.getAccountNonSummarySegment().getCreditFacility())
                        )
                        .toList();
            } else if ("Witten Off".equals(searchString)) {
                accountList = accountList.stream()
                        .filter(a ->
                                a.getAccountNonSummarySegment() != null &&
                                        a.getAccountNonSummarySegment().getCreditFacility() != null &&
                                        "Written-off".equals(a.getAccountNonSummarySegment().getCreditFacility())
                        )
                        .toList();
            } else if ("Settlement/Settled".equals(searchString)) {
                String[] keywords = {"Settlement", "Settled", "LSS"};
                accountList = accountList.stream()
                        .filter(a ->
                                a.getAccountNonSummarySegment() != null &&
                                        a.getAccountNonSummarySegment().getCreditFacility() != null &&
                                        Arrays.stream(keywords).anyMatch(
                                                keyword -> a.getAccountNonSummarySegment().getCreditFacility().contains(keyword)
                                        )
                        )
                        .toList();
            } else if ("Resturctured Loan".equals(searchString)) {
                accountList =accountList.stream()
                        .filter(a ->
                                a.getAccountNonSummarySegment() != null &&
                                        a.getAccountNonSummarySegment().getCreditFacility() != null &&
                                        "Resturctured Loan".equals(a.getAccountNonSummarySegment().getCreditFacility())
                        )
                        .toList();
            }
        } else if ("Suit".equals(searchCriteria)) {
            accountList = accountList.stream()
                    .filter(a ->
                            a.getAccountNonSummarySegment() != null &&
                                    a.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault() != null &&
                                    "Suit filed".equals(a.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault())
                    )
                    .toList();
        } else if("1to90plus".equals(searchCriteria)){
            accountList =getAccountsLoanInfo(accountList,searchCriteria,searchString);
        }
        return accountList;
    }

    public static void setLoanInfoData(List<Account> accountList,ConsumerAccountSummaryDTO consumerAccountSummaryDTO){
        long subCountOpen = 0, wilfulCountOpen = 0, writtenOffCountOpen = 0, settleCountOpen = 0, suitCountOpen = 0;
        long subCountLive = 0, wilfulCountLive = 0, writtenOffCountLive = 0, settleCountLive = 0, suitCountLive = 0;
        long subCountClose = 0, wilfulCountClose = 0, writtenOffCountClose = 0, settleCountClose = 0, suitCountClose = 0;
        for (Account eachAccount : accountList) {
            String ownershipIndicator=eachAccount.getAccountNonSummarySegment().getOwnershipIndicator();
            if(ownershipIndicator.equals("Individual")) {
                String[] keywords = {"Settlement", "Settled", "Restructured Loan"};
                String[] keyword1 = {"Settlement", "Settled"};

                String paymentHistory1d = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                String paymentHistory2=eachAccount.getAccountNonSummarySegment().getPaymentHistory2()!=null?eachAccount.getAccountNonSummarySegment().getPaymentHistory2():"";
                subCountOpen = (paymentHistory1d.contains("SUB") || paymentHistory1d.contains("DBT")||paymentHistory2.contains("SUB") || paymentHistory2.contains("DBT")) ? subCountOpen + 1 : subCountOpen;
                if (paymentHistory1d.contains("SUB")){
                    consumerAccountSummaryDTO.setSubStd(consumerAccountSummaryDTO.getSubStd()+1);
                } else if (paymentHistory1d.contains("DBT")) {
                    consumerAccountSummaryDTO.setDubDebt(consumerAccountSummaryDTO.getDubDebt()+1);
                }
                String status1 = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                if (status1 != null) {
                    if (status1.equals("Wilful Defaulter")) {
                        wilfulCountOpen += 1;
                    }
                    if (status1.equals("Written-off")) {
                        writtenOffCountOpen += 1;
                    }
                    if (Arrays.stream(keywords).anyMatch(status1::contains)) {
                        settleCountOpen += 1;
                    }
                    if(Arrays.stream(keyword1).anyMatch(status1::contains)){
                        consumerAccountSummaryDTO.setSettled(consumerAccountSummaryDTO.getSettled()+1);
                    }
                    if(status1.equals("Resturctured Loan")){
                        consumerAccountSummaryDTO.setResturctured(consumerAccountSummaryDTO.getResturctured()+1);
                    }
                }
                String suitFile = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                if (suitFile != null) {
                    suitCountOpen = suitFile.equals("Suit filed") ? suitCountOpen += 1 : suitCountOpen;
                }
                if (!eachAccount.getAccountNonSummarySegment().isLiveAccount()) {
                    String paymentHistory1 = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                    subCountClose = (paymentHistory1.contains("SUB") || paymentHistory1.contains("DBT")) ? subCountClose += 1 : subCountClose;
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountClose += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountClose += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountClose += 1;
                        }
                        if(Arrays.stream(keyword1).anyMatch(status1::contains)){
                            consumerAccountSummaryDTO.setSettled(consumerAccountSummaryDTO.getSettled()+1);
                        }
                        if(status1.equals("Resturctured Loan")){
                            consumerAccountSummaryDTO.setResturctured(consumerAccountSummaryDTO.getResturctured()+1);
                        }
                    }
                    String suitFile1 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile1 != null) {
                        suitCountClose = suitFile1.equals("Suit filed") ? suitCountClose += 1 : suitCountClose;
                    }

                } else {
                    subCountLive = (paymentHistory1d.contains("SUB") || paymentHistory1d.contains("DBT")||paymentHistory2.contains("SUB") || paymentHistory2.contains("DBT")) ? subCountLive += 1 : subCountLive;
                    if (paymentHistory1d.contains("SUB")){
                        consumerAccountSummaryDTO.setSubStd(consumerAccountSummaryDTO.getSubStd()+1);
                    } else if (paymentHistory1d.contains("DBT")) {
                        consumerAccountSummaryDTO.setDubDebt(consumerAccountSummaryDTO.getDubDebt()+1);
                    }
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountLive += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountLive += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountLive += 1;
                        }
                        if(Arrays.stream(keyword1).anyMatch(status1::contains)){
                            consumerAccountSummaryDTO.setSettled(consumerAccountSummaryDTO.getSettled()+1);
                        }
                        if(status1.equals("Resturctured Loan")){
                            consumerAccountSummaryDTO.setResturctured(consumerAccountSummaryDTO.getResturctured()+1);
                        }
                    }
                    String suitFile2 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile2 != null) {
                        suitCountLive = suitFile2.equals("Suit filed") ? suitCountLive += 1 : suitCountLive;
                    }
                }
            }
        }
        Map<String, Object> subStandardCount = new HashMap<>();
        subStandardCount.put("subStandOpen", subCountOpen);
        subStandardCount.put("subStandClose", subCountClose);
        subStandardCount.put("subStandLive", subCountLive);

        Map<String, Object> wilfulCount = new HashMap<>();
        wilfulCount.put("wilfulOpen", wilfulCountOpen);
        wilfulCount.put("wilfulClose", wilfulCountClose);
        wilfulCount.put("wilfulLive", wilfulCountLive);

        Map<String, Object> writtenOffCount = new HashMap<>();
        writtenOffCount.put("writtenOffOpen", writtenOffCountOpen);
        writtenOffCount.put("writtenOffClose", writtenOffCountClose);
        writtenOffCount.put("writtenOffLive", writtenOffCountLive);

        Map<String, Object> suitFileCount = new HashMap<>();
        suitFileCount.put("suitCountOpen", suitCountOpen);
        suitFileCount.put("suitCountClose", suitCountClose);
        suitFileCount.put("suitCountLive", suitCountLive);

        Map<String, Object> settleCount = new HashMap<>();
        settleCount.put("settleCountOpen", settleCountOpen);
        settleCount.put("settleCountClose", settleCountClose);
        settleCount.put("settleCountLive", settleCountOpen);

        allIndividual.put("subStandardCount", new HashMap<>(subStandardCount));
        allIndividual.put("wilfulCount", new HashMap<>(wilfulCount));
        allIndividual.put("writtenOffCount", new HashMap<>(writtenOffCount));
        allIndividual.put("suitFileCount", new HashMap<>(suitFileCount));
        allIndividual.put("settleCount", new HashMap<>(settleCount));

        subStandardCount.clear();
        wilfulCount.clear();
        writtenOffCount.clear();
        suitFileCount.clear();
        settleCount.clear();

        subCountOpen = wilfulCountOpen = writtenOffCountOpen = settleCountOpen = suitCountOpen =
                subCountLive = wilfulCountLive = writtenOffCountLive = settleCountLive = suitCountLive =
                        subCountClose = wilfulCountClose = writtenOffCountClose = settleCountClose = suitCountClose =0;
        for (Account eachAccount:accountList){
            if (eachAccount.getAccountNonSummarySegment().getOwnershipIndicator().equals("Joint")) {
                String[] keywords = {"Settlement", "Settled", "Restructured Loan"};
                String paymentHistory1d = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                String paymentHistory2=eachAccount.getAccountNonSummarySegment().getPaymentHistory2()!=null?eachAccount.getAccountNonSummarySegment().getPaymentHistory2():"";
                subCountOpen = (paymentHistory1d.contains("SUB") || paymentHistory1d.contains("DBT")||paymentHistory2.contains("SUB") || paymentHistory2.contains("DBT")) ? subCountOpen + 1 : subCountOpen;
                if (paymentHistory1d.contains("SUB")){
                    consumerAccountSummaryDTO.setSubStd(consumerAccountSummaryDTO.getSubStd()+1);
                } else if (paymentHistory1d.contains("DBT")) {
                    consumerAccountSummaryDTO.setDubDebt(consumerAccountSummaryDTO.getDubDebt()+1);
                }
                String status1 = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                if (status1 != null) {
                    if (status1.equals("Wilful Defaulter")) {
                        wilfulCountOpen += 1;
                    }
                    if (status1.equals("Written-off")) {
                        writtenOffCountOpen += 1;
                    }
                    if (Arrays.stream(keywords).anyMatch(status1::contains)) {
                        settleCountOpen += 1;
                    }
                }
                String suitFile = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                if (suitFile != null) {
                    suitCountOpen = suitFile.equals("Suit filed") ? suitCountOpen += 1 : suitCountOpen;
                }
                if (!eachAccount.getAccountNonSummarySegment().isLiveAccount()) {
                    String paymentHistory1 = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                    subCountClose = (paymentHistory1.contains("SUB") || paymentHistory1.contains("DBT")) ? subCountClose += 1 : subCountClose;
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountClose += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountClose += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountClose += 1;
                        }
                    }
                    String suitFile1 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile1 != null) {
                        suitCountClose = suitFile1.equals("Suit filed") ? suitCountClose += 1 : suitCountClose;
                    }

                } else {
                    String paymentHistory1 = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                    subCountLive = (paymentHistory1.contains("SUB") || paymentHistory1.contains("DBT")) ? subCountLive += 1 : subCountLive;
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountLive += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountLive += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountLive += 1;
                        }
                    }
                    String suitFile2 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile2 != null) {
                        suitCountLive = suitFile2.equals("Suit filed") ? suitCountLive += 1 : suitCountLive;
                    }
                }
            }
        }

        subStandardCount.put("subStandOpen", subCountOpen);
        subStandardCount.put("subStandClose", subCountClose);
        subStandardCount.put("subStandLive", subCountLive);

        wilfulCount.put("wilfulOpen", wilfulCountOpen);
        wilfulCount.put("wilfulClose", wilfulCountClose);
        wilfulCount.put("wilfulLive", wilfulCountLive);

        writtenOffCount.put("writtenOffOpen", writtenOffCountOpen);
        writtenOffCount.put("writtenOffClose", writtenOffCountClose);
        writtenOffCount.put("writtenOffLive", writtenOffCountLive);

        suitFileCount.put("suitCountOpen", suitCountOpen);
        suitFileCount.put("suitCountClose", suitCountClose);
        suitFileCount.put("suitCountLive", suitCountLive);

        settleCount.put("settleCountOpen", settleCountOpen);
        settleCount.put("settleCountClose", settleCountClose);
        settleCount.put("settleCountLive", settleCountOpen);

        allJoint.put("subStandardCount", new HashMap<>(subStandardCount));
        allJoint.put("wilfulCount", new HashMap<>(wilfulCount));
        allJoint.put("writtenOffCount", new HashMap<>(writtenOffCount));
        allJoint.put("suitFileCount", new HashMap<>(suitFileCount));
        allJoint.put("settleCount", new HashMap<>(settleCount));

        subStandardCount.clear();
        wilfulCount.clear();
        writtenOffCount.clear();
        suitFileCount.clear();
        settleCount.clear();

        subCountOpen = wilfulCountOpen = writtenOffCountOpen = settleCountOpen = suitCountOpen =
                subCountLive = wilfulCountLive = writtenOffCountLive = settleCountLive = suitCountLive =
                        subCountClose = wilfulCountClose = writtenOffCountClose = settleCountClose = suitCountClose = 0;
        for (Account eachAccount:accountList){
            if (eachAccount.getAccountNonSummarySegment().getOwnershipIndicator().equals("Guarantor")) {
                String[] keywords = {"Settlement", "Settled", "Restructured Loan"};


                String paymentHistory1d = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                String paymentHistory2=eachAccount.getAccountNonSummarySegment().getPaymentHistory2()!=null?eachAccount.getAccountNonSummarySegment().getPaymentHistory2():"";
                subCountOpen = (paymentHistory1d.contains("SUB") || paymentHistory1d.contains("DBT")||paymentHistory2.contains("SUB") || paymentHistory2.contains("DBT")) ? subCountOpen + 1 : subCountOpen;
                String status1 = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                if (status1 != null) {
                    if (status1.equals("Wilful Defaulter")) {
                        wilfulCountOpen += 1;
                    }
                    if (status1.equals("Written-off")) {
                        writtenOffCountOpen += 1;
                    }
                    if (Arrays.stream(keywords).anyMatch(status1::contains)) {
                        settleCountOpen += 1;
                    }
                }
                String suitFile = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                if (suitFile != null) {
                    suitCountOpen = suitFile.equals("Suit filed") ? suitCountOpen += 1 : suitCountOpen;
                }
                if (!eachAccount.getAccountNonSummarySegment().isLiveAccount()) {
                    String paymentHistory1 = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                    subCountClose = (paymentHistory1.contains("SUB") || paymentHistory1.contains("DBT")) ? subCountClose += 1 : subCountClose;
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountClose += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountClose += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountClose += 1;
                        }
                    }
                    String suitFile1 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile1 != null) {
                        suitCountClose = suitFile1.equals("Suit filed") ? suitCountClose += 1 : suitCountClose;
                    }

                } else {
                    String paymentHistory1 = eachAccount.getAccountNonSummarySegment().getPaymentHistory1();
                    subCountLive = (paymentHistory1.contains("SUB") || paymentHistory1.contains("DBT")) ? subCountLive += 1 : subCountLive;
                    String status = eachAccount.getAccountNonSummarySegment().getCreditFacility();
                    if (status != null) {
                        if (status1.equals("Wilful Defaulter")) {
                            wilfulCountLive += 1;
                        }
                        if (status.equals("Written-off")) {
                            writtenOffCountLive += 1;
                        }
                        if (Arrays.stream(keywords).anyMatch(status::contains)) {
                            settleCountLive += 1;
                        }
                    }
                    String suitFile2 = eachAccount.getAccountNonSummarySegment().getSuitFiledOrWilfulDefault();
                    if (suitFile2 != null) {
                        suitCountLive = suitFile2.equals("Suit filed") ? suitCountLive += 1 : suitCountLive;
                    }
                }
            }
        }
        subStandardCount.put("subStandOpen",subCountOpen);
        subStandardCount.put("subStandClose",subCountClose);
        subStandardCount.put("subStandLive",subCountLive);

        wilfulCount.put("wilfulOpen",wilfulCountOpen);
        wilfulCount.put("wilfulClose",wilfulCountClose);
        wilfulCount.put("wilfulLive",wilfulCountLive);

        writtenOffCount.put("writtenOffOpen",writtenOffCountOpen);
        writtenOffCount.put("writtenOffClose",writtenOffCountClose);
        writtenOffCount.put("writtenOffLive",writtenOffCountLive);

        suitFileCount.put("suitCountOpen",suitCountOpen);
        suitFileCount.put("suitCountClose",suitCountClose);
        suitFileCount.put("suitCountLive",suitCountLive);

        settleCount.put("settleCountOpen",settleCountOpen);
        settleCount.put("settleCountClose",settleCountClose);
        settleCount.put("settleCountLive",settleCountOpen);


        allGuarantor.put("subStandardCount", new HashMap<>(subStandardCount));
        allGuarantor.put("wilfulCount", new HashMap<>(wilfulCount));
        allGuarantor.put("writtenOffCount", new HashMap<>(writtenOffCount));
        allGuarantor.put("suitFileCount", new HashMap<>(suitFileCount));
        allGuarantor.put("settleCount", new HashMap<>(settleCount));

        subStandardCount.clear();
        wilfulCount.clear();
        writtenOffCount.clear();
        suitFileCount.clear();
        settleCount.clear();

    }

   public void saveDpdData(EnvelopeResponse envelopeResponse,String reportId){
       CreditReport creditReport = envelopeResponse.getCombinedResponse().getApplicant().getDsCibilBureauData().getCibilBureauResponse().getCreditReport();
      ConsumerAccountSummaryDTO accountSummaryDTO=new ConsumerAccountSummaryDTO();
       for (Account eachAccount : creditReport.getAccountList()) {
           updateDPDCountForAccounts(accountSummaryDTO, eachAccount);
       }
            CibilDpdData cibilDpdData=cibilConsumerMapper.toCibilDpdData(accountSummaryDTO);
            cibilDpdData.setReportId(reportId);
            cibilDpdDataRepository.save(cibilDpdData);

   }

}

package com.pf.mas.mapper;

import com.pf.mas.model.dto.cibil.EMIMasterDto;
import com.pf.mas.model.dto.cibil.consumer.*;
import com.pf.mas.model.dto.cibil.enums.AssetClassificationStatus;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsDTO;
import com.pf.mas.model.dto.cibil.ui.AddressInfoDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerAccountSummaryDTO;
import com.pf.mas.model.dto.cibil.ui.LosDetailsDTO;
import com.pf.mas.model.entity.cibil.*;
import com.pf.mas.model.entity.consumer.*;
import com.pf.mas.service.cibilservice.CibilConsumerServiceImpl;
import com.pf.mas.utils.cibil.MappingRegistry;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class CibilConsumerMapper {

    @Lazy
    @Autowired
    private CibilConsumerServiceImpl cibilConsumerService;

    public abstract ApplicantAddressDSCB toApplicantAddressDSCB(ApplicantAddressDTO applicantAddressDTO);

    public abstract List<ApplicantAddressDSCB> toApplicantAddressDSCBList(List<ApplicantAddressesDTO> applicantAddressesDTOS);

    public abstract Applicant toApplicantDSCB(ApplicantDTO applicantDTO);

    public abstract ApplicationData toApplicationData(ApplicationDataDTO applicationDataDTO);

    public abstract HeaderCRI toHeaderCRI(CriHeaderDTO criHeaderDTO);

    public abstract List<HeaderCRI> toHeaderCRIList(List<CriHeaderDTO> criHeaderDTOList);


    @AfterMapping
    public void mapFieldsForHeaderCRI(@MappingTarget HeaderCRI headerCRI, CriHeaderDTO criHeaderDTO) {
        headerCRI.setScoreType(MappingRegistry.getHeaderCriScoreType(criHeaderDTO.getScoreType()));
        headerCRI.setOutputFormat(MappingRegistry.getHeaderCriOutputFormat(criHeaderDTO.getOutputFormat()));
        headerCRI.setMediaType(MappingRegistry.getHeaderCriMediaType(criHeaderDTO.getMediaType()));
        headerCRI.setAuthenticationMethod(MappingRegistry.getHeaderCriAuthenticationMethod(criHeaderDTO.getAuthenticationMethod()));
    }

    public abstract IdentifierApplicant toIdentifierApplicant(IdentifierDTO identifierDTO);

    public abstract List<IdentifierApplicant> toIdentifierApplicantList(List<IdentifierDTO> identifierDTOList);

    public abstract NameCRI toNameCRI(NameDTO nameDTO);

    public abstract List<NameCRI> toNameCRIList(List<NameDTO> nameDTOList);

    public abstract IdentificationCRI toIdentificationCRI(IdentificationDTO identificationDTO);

    public abstract List<IdentificationCRI> toIdentificationCRIList(List<IdentificationDTO> identificationDTOList);

    public abstract CreditReportAddress toCreditReportAddress(CreditReportAddressDTO creditReportAddressDTO);

    public abstract List<CreditReportAddress> toCreditReportAddressList(List<CreditReportAddressDTO> creditReportAddressDTOList);

    @AfterMapping
    public void mapFieldsForCreditReportAddress(@MappingTarget CreditReportAddress creditReportAddress, CreditReportAddressDTO creditReportAddressDTO) {
        creditReportAddress.setAddressCategory(MappingRegistry.getCreditReportAddressCategory(creditReportAddressDTO.getAddressCategory()));
        creditReportAddress.setResidenceCode(MappingRegistry.getCreditReportAddressResidenceCode(creditReportAddressDTO.getResidenceCode()));
    }

    public abstract CreditReportHeader toCreditReportHeader(CreditReportHeaderDTO creditReportHeaderDTO);

    public abstract List<CreditReportHeader> toCreditReportHeaderList(List<CreditReportHeaderDTO> creditReportHeaderDTOList);

    @AfterMapping
    public void mapFieldsForCreditReportHeader(@MappingTarget CreditReportHeader creditReportHeader, CreditReportHeaderDTO creditReportHeaderDTO) {
        creditReportHeader.setDateProcessed(convertStringToDate(creditReportHeaderDTO.getDateProcessed()));
    }

    public abstract EmploymentSegment toEmploymentSegment(EmploymentSegmentDTO employmentSegmentDTO);

    public abstract List<EmploymentSegment> toEmploymentSegmentList(List<EmploymentSegmentDTO> employmentSegmentDTOList);

    @AfterMapping
    public void mapFieldsForEmploymentSegment(@MappingTarget EmploymentSegment employmentSegment, EmploymentSegmentDTO employmentSegmentDTO) {
        employmentSegment.setOccupationCode(MappingRegistry.getEmploymentSegmentResponseOccupationCode(employmentSegmentDTO.getOccupationCode()));
        employmentSegment.setErrorCode(MappingRegistry.getEmploymentSegmentResponseErrorCode(employmentSegmentDTO.getErrorCode()));
        employmentSegment.setCibilRemarksCode(MappingRegistry.getCibilRemarksCodesMap(employmentSegmentDTO.getCibilRemarksCode()));
        employmentSegment.setNetGrossIndicator(MappingRegistry.getEmploymentSegmentResponseNetGrossIncIndicator(employmentSegmentDTO.getNetGrossIndicator()));
        employmentSegment.setMonthlyAnnualIndicator(MappingRegistry.getEmploymentSegmentResponseMonthlyAnnualIncIndicator(employmentSegmentDTO.getMonthlyAnnualIndicator()));
        employmentSegment.setDateReportedCertified(convertStringToDate(employmentSegmentDTO.getDateReportedCertified()));
        employmentSegment.setDateOfEntryForErrorCode(convertStringToDate(employmentSegmentDTO.getDateOfEntryForErrorCode()));
        employmentSegment.setDateOfEntryForCibilRemarksCode(convertStringToDate(employmentSegmentDTO.getDateOfEntryForCibilRemarksCode()));
        employmentSegment.setDateOfEntryForErrorDisputeRemarksCode(convertStringToDate(employmentSegmentDTO.getDateOfEntryForErrorDisputeRemarksCode()));
        employmentSegment.setDateOfSuppression(convertStringToDate(employmentSegmentDTO.getDateOfSuppression()));
    }

    public abstract NameSegment toNameSegment(NameSegmentDTO nameSegmentDTO);

    public abstract List<NameSegment> toNameSegmentList(List<NameSegmentDTO> nameSegmentDTOList);

    @AfterMapping
    public void mapFieldsForNameSegment(@MappingTarget NameSegment nameSegment, NameSegmentDTO nameSegmentDTO) {
        nameSegment.setGender(MappingRegistry.getNameSegmentResponseGender(nameSegmentDTO.getGender()));
        nameSegment.setCibilRemarksCode(MappingRegistry.getCibilRemarksCodesMap(nameSegmentDTO.getCibilRemarksCode()));
        nameSegment.setDateOfEntryForErrorCode(convertStringToDate(nameSegmentDTO.getDateOfEntryForErrorCode()));
        nameSegment.setDateOfEntryForCibilRemarksCode(convertStringToDate(nameSegmentDTO.getDateOfEntryForCibilRemarksCode()));
        nameSegment.setDateOfEntryForErrorDisputeRemarksCode(convertStringToDate(nameSegmentDTO.getDateOfEntryForErrorDisputeRemarksCode()));
        nameSegment.setDateOfSuppression(convertStringToDate(nameSegmentDTO.getDateOfSuppression()));
        nameSegment.setDateOfBirth(convertStringToDate(nameSegmentDTO.getDateOfBirth()));

    }

    public abstract IdSegment toIdSegment(IdSegmentDTO idSegmentDTO);

    public abstract List<IdSegment> toIdSegmentList(List<IdSegmentDTO> idSegmentDTOList);

    @AfterMapping
    public void mapFieldsForIdSegment(@MappingTarget IdSegment idSegment, IdSegmentDTO idSegmentDTO) {
        idSegment.setIdType(MappingRegistry.getIdSegmentResponseIdType(idSegmentDTO.getIdType()));
        idSegment.setIssueDate(convertStringToDate(idSegmentDTO.getIssueDate()));
        idSegment.setDateOfSuppression(convertStringToDate(idSegmentDTO.getDateOfSuppression()));

    }

    public abstract TelephoneSegment toTelephoneSegment(TelephoneSegmentDTO telephoneSegmentDTO);

    public abstract List<TelephoneSegment> toTelephoneSegmentList(List<TelephoneSegmentDTO> telephoneSegmentDTOList);

    @AfterMapping
    public void mapFieldsForTelephoneSegment(@MappingTarget TelephoneSegment telephoneSegment, TelephoneSegmentDTO telephoneSegmentDTO) {
        telephoneSegment.setTelephoneType(MappingRegistry.getTelephoneSegmentResponseTelephoneType(telephoneSegmentDTO.getTelephoneType()));
        telephoneSegment.setDateOfSuppression(convertStringToDate(telephoneSegmentDTO.getDateOfSuppression()));
    }

    public abstract EmailContactSegment toEmailContactSegment(EmailContactSegment emailContactSegment);

    public abstract List<EmailContactSegment> toEmailContactSegmentList(List<EmailContactSegmentDTO> emailContactSegmentDTOList);

    public abstract ScoreSegment toScoreSegment(ScoreSegmentDTO scoreSegmentDTO);

    public abstract List<ScoreSegment> mapScoreSegmentList(List<ScoreSegmentDTO> scoreSegmentDTOList);


    @Mapping(target = "paymentHistoryRecordList", source = "accountNonSummarySegment.paymentHistoryRecordList")
    @Mapping(target = "interestRate", source = "accountNonSummarySegment.rateOfInterest")
    @Mapping(target = "emi", source = "accountNonSummarySegment.emiAmount")
    @Mapping(target = "reportedDate", source = "accountNonSummarySegment.dateReportedAndCertified")
    @Mapping(target = "tenure", source = "accountNonSummarySegment.repaymentTenure")
    @Mapping(target = "overdue", source = "accountNonSummarySegment.amountOverdue")
    @Mapping(target = "ownership", source = "accountNonSummarySegment.ownershipIndicator")
    @Mapping(target = "bankNbfc", source = "accountNonSummarySegment.reportingMemberShortName")
    // #TODO pending
    @Mapping(target = "cibilStatus", source = "accountNonSummarySegment.creditFacility")
    @Mapping(target = "sanctionedDate", source = "accountNonSummarySegment.dateOpenedOrDisbursed")
    @Mapping(target = "outstandingAmount", source = "accountNonSummarySegment.currentBalance")
    @Mapping(target = "sanctionedAmount", source = "accountNonSummarySegment.highCreditOrSanctionedAmount")
    @Mapping(target = "typeOfLoan", source = "accountNonSummarySegment.accountType")
    @Mapping(target = "creditFacilityStatus", source = "accountNonSummarySegment.creditFacility")
    @Mapping(target = "writtenOffAmountPrincipal", source = "accountNonSummarySegment.writtenOffAmountPrincipal")
    @Mapping(target = "suitFiledOrWilfulDefault", source = "accountNonSummarySegment.suitFiledOrWilfulDefault")
    public abstract AccountBorrowingDetailsDTO toAccountBorrowingDetailsDTO(Account account);

    @AfterMapping
    public void mapFieldsForAccountBorrowingDetailsDTO(@MappingTarget AccountBorrowingDetailsDTO accountBorrowingDetailsDTO, Account account) {
        cibilConsumerService.calculateDpdSummary(account.getAccountNonSummarySegment().getPaymentHistoryRecordList(), accountBorrowingDetailsDTO);
    }

    @AfterMapping
    public void mapFieldsForScoreSegment(@MappingTarget ScoreSegment scoreSegment, ScoreSegmentDTO scoreSegmentDTO) {
        scoreSegment.setScoreCardName(MappingRegistry.getScoreSegmentResponseScoreCardName(scoreSegmentDTO.getScoreCardName()));
        scoreSegment.setErrorCode(MappingRegistry.getScoreSegmentErrorCode(scoreSegmentDTO.getErrorCode()));
        scoreSegment.setScoreDate(convertStringToDate(scoreSegmentDTO.getScoreDate()));
        Set<ScoreSegmentBureauCharacteristics> bureauCharacteristics = new HashSet<>();
        if(scoreSegmentDTO.getBureauCharacteristics() != null) {
            for (Map.Entry<String, String> entry : scoreSegmentDTO.getBureauCharacteristics().entrySet()) {
                ScoreSegmentBureauCharacteristics characteristic = new ScoreSegmentBureauCharacteristics();
                characteristic.setAlgoName(entry.getKey());
                characteristic.setAlgoValue(entry.getValue());
                bureauCharacteristics.add(characteristic);
            }
        }
        
        scoreSegment.setScoreSegmentBureauCharacteristicsList(bureauCharacteristics.stream().toList());
    }

    @Mapping(target = "accountNonSummarySegment", source = "accountNonSummarySegmentFieldsDTO")
    @Mapping(target = "accountSummarySegment", source = "accountSummarySegmentFieldsDTO")
    public abstract Account toAccount(AccountDTO accountDTO);

    public abstract LosAddressInfo toLosAddressInfo(AddressInfoDTO addressInfoDTO);

    public abstract AddressInfoDTO toAddressInfoDTO(LosAddressInfo losAddressInfo);

    @AfterMapping
    public void mapFieldsForAddressInfoDTO(@MappingTarget AddressInfoDTO addressInfoDTO, LosAddressInfo losAddressInfo) {
        addressInfoDTO.setStateCode(MappingRegistry.getStateFromStateCode(losAddressInfo.getStateCode()));
    }

    public abstract LosIdentification toLosIdentification(IdentificationDTO identificationDTO);

    public abstract IdentificationDTO toIdentificationDTO(LosIdentification losIdentification);

    @Mapping(target = "losIdentificationList", source = "losIdentificationDTOList")
    @Mapping(target = "losAddressInfoList", source = "losAddressInfoDTOList")
    public abstract LosDetails toLosDetails(LosDetailsDTO losDetailsDTO);

    @Mapping(target = "losIdentificationDTOList", source = "losIdentificationList")
    @Mapping(target = "losAddressInfoDTOList", source = "losAddressInfoList")
    public abstract LosDetailsDTO toLosDetailsDTO(LosDetails losDetails);


    public abstract LosAddressInfoRaw toLosAddressInfoRaw(AddressInfoDTO addressInfoDTO);
    public abstract LosIdentificationRaw toLosIdentificationRaw(IdentificationDTO identificationDTO);
    @Mapping(target = "losIdentificationRawList", source = "losIdentificationDTOList")
    @Mapping(target = "losAddressInfoRawList", source = "losAddressInfoDTOList")
    public abstract LosDetailsRaw toLosDetailsRaw(LosDetailsDTO losDetailsDTO);

    public abstract List<Account> mapAccountList(List<AccountDTO> accountDTOList);


    public abstract List<Enquiry> mapEnquiryList(List<EnquiryDTO> enquiryDTOList);

    public abstract Enquiry toEnquiry(EnquiryDTO enquiryDTO);

    @AfterMapping
    public void mapFieldsForEnquiry(@MappingTarget Enquiry enquiry, EnquiryDTO enquiryDTO) {
        enquiry.setDateOfEnquiryFields(convertStringToDate(enquiryDTO.getDateOfEnquiryFields()));
        enquiry.setDateOfSuppression(convertStringToDate(enquiryDTO.getDateOfSuppression()));
    }

    public abstract AddressCRI toDsCibilBureauAddress(CriAddressDTO criAddressDTO);

    public abstract List<AddressCRI> toDsCibilBureauAddressList(List<CriAddressDTO> criAddressDTOList);

    @AfterMapping
    public void mapFieldsForAddressCRI(@MappingTarget AddressCRI addressCRI, CriAddressDTO criAddressDTO) {
        addressCRI.setAddressCategory(MappingRegistry.getAddressCriAddressCategory(criAddressDTO.getAddressCategory()));
        addressCRI.setResidenceCode(MappingRegistry.getAddressCriResidenceCode(criAddressDTO.getResidenceCode()));
    }

    public abstract AccountNonSummarySegment toAccountNonSummarySegment(AccountNonSummarySegmentFieldsDTO accountNonSummarySegmentFieldsDTO);

    @AfterMapping
    public void mapFieldsForAccountNonSummarySegment(@MappingTarget AccountNonSummarySegment accountNonSummarySegment, AccountNonSummarySegmentFieldsDTO accountNonSummarySegmentFieldsDTO) {
        accountNonSummarySegment.setAccountType(MappingRegistry.getAccountAndLoanType(accountNonSummarySegmentFieldsDTO.getAccountType()));
        accountNonSummarySegment.setOwnershipIndicator(MappingRegistry.getAccountOwnershipIndicator(accountNonSummarySegmentFieldsDTO.getOwnershipIndicator()));
        accountNonSummarySegment.setTypeOfCollateral(MappingRegistry.getAccountTypeOfCollateral(accountNonSummarySegmentFieldsDTO.getTypeOfCollateral()));
        accountNonSummarySegment.setPaymentFrequency(MappingRegistry.getAccountPaymentFrequency(accountNonSummarySegmentFieldsDTO.getPaymentFrequency()));
        accountNonSummarySegment.setPaymentHistoryRecordList(this.processPaymentHistory(accountNonSummarySegmentFieldsDTO.getPaymentHistory1(), accountNonSummarySegmentFieldsDTO.getPaymentHistory2(), accountNonSummarySegmentFieldsDTO.getPaymentHistoryStartDate()));
        accountNonSummarySegment.setLiveAccount(accountNonSummarySegmentFieldsDTO.getCurrentBalance() != 0L);
        accountNonSummarySegment.setErrorCode(MappingRegistry.getAccountErrorCodes(accountNonSummarySegmentFieldsDTO.getErrorCode()));
        accountNonSummarySegment.setCibilRemarksCode(MappingRegistry.getCibilRemarksCodesMap(accountNonSummarySegmentFieldsDTO.getCibilRemarksCode()));
        accountNonSummarySegment.setSuitFiledOrWilfulDefault(MappingRegistry.getAccountSuitFiledWilfulDefault(accountNonSummarySegmentFieldsDTO.getSuitFiledOrWilfulDefault()));
        accountNonSummarySegment.setCreditFacility(MappingRegistry.getAccountCreditFacilityStatus(accountNonSummarySegmentFieldsDTO.getCreditFacility()));
        accountNonSummarySegment.setDateOpenedOrDisbursed(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOpenedOrDisbursed()));
        accountNonSummarySegment.setDateReportedAndCertified(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateReportedAndCertified()));
        accountNonSummarySegment.setPaymentHistoryEndDate(convertStringToDate(accountNonSummarySegmentFieldsDTO.getPaymentHistoryEndDate()));

        accountNonSummarySegment.setDateOfEntryForCibilRemarksCode(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOfEntryForCibilRemarksCode()));
        accountNonSummarySegment.setDateOfEntryForErrorDisputeRemarksCode(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOfEntryForErrorDisputeRemarksCode()));

        accountNonSummarySegment.setDateOfSuppression(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOfSuppression()));
        accountNonSummarySegment.setDateOfEntryForErrorCode(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOfEntryForErrorCode()));
        accountNonSummarySegment.setDateOfLastPayment(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateOfLastPayment()));
        accountNonSummarySegment.setDateClosed(convertStringToDate(accountNonSummarySegmentFieldsDTO.getDateClosed()));
        accountNonSummarySegment.setPaymentHistoryStartDate(convertStringToDate(accountNonSummarySegmentFieldsDTO.getPaymentHistoryStartDate()));

    }

    public abstract AccountSummarySegment toAccountSummarySegment(AccountSummarySegmentFieldsDTO accountSummarySegmentFieldsDTO);

    @Mapping(target = "nameSegmentList", source = "nameSegmentDTOList")
    @Mapping(target = "accountList", source = "accountDTOList")
    @Mapping(target = "enquiryList", source = "enquiryDTOList")
    @Mapping(target = "emailContactSegmentList", source = "emailContactSegmentDTOList")
    @Mapping(target = "creditReportAddressList", source = "creditReportAddressDTOList")
    @Mapping(target = "creditReportHeaderList", source = "creditReportHeaderDTOList")
    @Mapping(target = "employmentSegmentList", source = "employmentSegmentDTOList")
    @Mapping(target = "idSegmentList", source = "idSegmentDTOList")
    @Mapping(target = "telephoneSegmentList", source = "telephoneSegmentDTOList")
    @Mapping(target = "scoreSegmentList", source = "scoreSegmentDTOList")
    public abstract CreditReport toCreditReport(CreditReportDTO creditReportDTO);

    @Mapping(target = "creditReport", source = "bureauResponseXmlDTO.creditReportDTO")
    @Mapping(target = "secondaryCreditReport", source = "secondaryReportXmlDTO.root.creditReportDTO")
    public abstract CibilBureauResponse toCibilBureauResponse(CibilBureauResponseDTO cibilBureauResponseDTO);

    public abstract DocumentDSCB toDocumentDSCB(DocumentDTO documentDTO);

    public abstract DsCibilBureauData toDsCibilBureauData(DsCibilBureauDataDTO dsCibilBureauDataDTO);

    @Mapping(target = "nameCRIList", source = "namesDTO.nameDTOList")
    @Mapping(target = "addressCRIList", source = "criAddressesDTO.criAddressDTOList")
    @Mapping(target = "headerCRI", source = "criHeaderDTO")
    @Mapping(target = "telephones", source = "telephoneList")
    @Mapping(target = "identificationCRIList", source = "identificationsDTO.identificationDTOList")
    public abstract CreditReportInquiry creditReportInquiry(CreditReportInquiryDTO creditReportInquiryDTO);

    @Mapping(target = "creditReportInquiry", source = "dsCibilBureauDataDTO.requestDTO.innerRequestDTO.consumerDetailsDTO.creditReportInquiryDTO")
    @Mapping(target = "cibilBureauResponse", source = "responseDTO.cibilBureauResponseDTO")
    @Mapping(target = "dsCibilBureauStatus", source = "dsCibilBureauStatusDTO")
    @Mapping(target = "documentDSCB", source = "documentDTO")
    public abstract DsCibilBureauData toDsCibilBureauData(DsCibilBureauDTO dsCibilBureauDTO);

    @Mapping(target = "identifierApplicantList", source = "identifiersDTO.identifierDTOList")
    @Mapping(target = "applicantAddressDSCBList", source = "addresses.addressList")
    @Mapping(target = "dsCibilBureauData", source = "dsCibilBureauDTO")
    @Mapping(target = "telephoneList", source = "applicantTelephonesDTO.applicantTelephoneCommercialDTO")
    public abstract Applicant toApplicant(ApplicantDTO applicantDTO);

    public abstract AuthenticationInfo toAuthenticationInfo(AuthenticationDTO authenticationDTO);

    public abstract ResponseInfo toResponseInfo(ResponseInfoDTO responseInfoDTO);

    @Mapping(target = "combinedResponse", source = "contextDataDTO")
    @Mapping(target = "authenticationInfo", source = "authentication")
    public abstract EnvelopeResponse toEnvelopeResponse(DcResponseDTO dcResponseDTO);

    public CombinedResponse toCombinedResponse(ContextDataDTO contextDataDTO) {
        List<FieldDTO> fieldDTOList = contextDataDTO.getFieldDTOList();
        if (fieldDTOList.isEmpty()) {
            return null;
        }
        CombinedResponse combinedResponse = new CombinedResponse();
        for (FieldDTO eachFieldDTO : fieldDTOList) {
            if (eachFieldDTO.getApplicantsDTO() != null) {
                combinedResponse.setApplicant(toApplicant(eachFieldDTO.getApplicantsDTO().getApplicantDTO()));
            }
            if (eachFieldDTO.getApplicationDataDTO() != null) {
                combinedResponse.setApplicationData(toApplicationData(eachFieldDTO.getApplicationDataDTO()));
            }
            if (eachFieldDTO.getKey() != null && eachFieldDTO.getKey().equals("Decision")) {
                combinedResponse.setDecision(eachFieldDTO.getValue());
            }
            if (eachFieldDTO.getKey() != null && eachFieldDTO.getKey().equals("ApplicationId")) {
                combinedResponse.setApplicationId(eachFieldDTO.getValue());
            }
        }
        return combinedResponse;
    }

    private List<PaymentHistoryRecord> processPaymentHistory(String paymentHistory1, String paymentHistory2, String paymentHistoryStartDate) {
        List<PaymentHistoryRecord> combinedPaymentHistoryRecordList = new ArrayList<>();
        LocalDate currentDate = LocalDate.parse(paymentHistoryStartDate, DateTimeFormatter.ofPattern("ddMMyyyy"));

        if(paymentHistory1 != null) {
            for (int i = 0; i < paymentHistory1.length(); i += 3) {
                PaymentHistoryRecord paymentHistoryRecord = new PaymentHistoryRecord();
                String chunk = paymentHistory1.substring(i, Math.min(i + 3, paymentHistory1.length()));
                paymentHistoryRecord.setDate(java.sql.Date.valueOf(currentDate));
                currentDate = currentDate.minusMonths(1);
                mapChunkToStatus(chunk, paymentHistoryRecord);
                combinedPaymentHistoryRecordList.add(paymentHistoryRecord);
            }
        }

        if(paymentHistory2 != null) {
            for (int i = 0; i < paymentHistory2.length(); i += 3) {
                PaymentHistoryRecord paymentHistoryRecord = new PaymentHistoryRecord();
                String chunk = paymentHistory2.substring(i, Math.min(i + 3, paymentHistory2.length()));
                paymentHistoryRecord.setDate(java.sql.Date.valueOf(currentDate));
                currentDate = currentDate.minusMonths(1);
                mapChunkToStatus(chunk, paymentHistoryRecord);
                combinedPaymentHistoryRecordList.add(paymentHistoryRecord);
            }
        }

        return combinedPaymentHistoryRecordList;
    }

    private void mapChunkToStatus(String chunk, PaymentHistoryRecord paymentHistoryRecord) {

        switch (chunk) {
            case "STD":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.STD);
                break;
            case "SMA":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.SMA);
                break;
            case "SUB":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.SUB);
                break;
            case "DBT":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.DBT);
                break;
            case "LSS":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.LSS);
                break;
            case "XXX":
                paymentHistoryRecord.setStatus(AssetClassificationStatus.XXX);
                break;
            default:
                try {
                    Long dpd = Long.parseLong(chunk);
                    paymentHistoryRecord.setDpd(dpd);
                } catch (NumberFormatException e) {
                    log.error("Error parsing paymentHistory. Unable to convert to valid numeric value", e);
                }
        }
    }

    public static Date convertStringToDate(String dateString) {
        try {
            if(dateString == null) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
            return formatter.parse(dateString);
        } catch (Exception e) {
            log.error("Could not parse date.", e);
        }
        return null;
    }

    public abstract AuccountSummerySegmentDTO toAccountSummerySegment(AccountNonSummarySegment accountNonSummarySegment);

    public abstract EMIMaster toEmiMaster(EMIMasterDto emiMasterDto);

    public abstract EMIMasterDto toEmiMasterDto(EMIMaster emiMaster);

    public  CibilDpdData toCibilDpdData(ConsumerAccountSummaryDTO dto) {
        return CibilDpdData.builder()
                .dpd0AllAccountsInd(dto.getDpd0AllAccountsInd())
                .dpd1to30AllAccountsInd(dto.getDpd1to30AllAccountsInd())
                .dpd31to90AllAccountsInd(dto.getDpd31to90AllAccountsInd())
                .dpdGreaterThan90AllAccountsInd(dto.getDpdGreaterThan90AllAccountsInd())
                .dpd0LiveAccountsInd(dto.getDpd0LiveAccountsInd())
                .dpd1to30LiveAccountsInd(dto.getDpd1to30LiveAccountsInd())
                .dpd31to90LiveAccountsInd(dto.getDpd31to90LiveAccountsInd())
                .dpdGreaterThan90LiveAccountsInd(dto.getDpdGreaterThan90LiveAccountsInd())
                .dpd0ClosedAccountsInd(dto.getDpd0ClosedAccountsInd())
                .dpd1to30ClosedAccountsInd(dto.getDpd1to30ClosedAccountsInd())
                .dpd31to90ClosedAccountsInd(dto.getDpd31to90ClosedAccountsInd())
                .dpdGreaterThan90ClosedAccountsInd(dto.getDpdGreaterThan90ClosedAccountsInd())
                .dpd0AllAccountsJoint(dto.getDpd0AllAccountsJoint())
                .dpd1to30AllAccountsJoint(dto.getDpd1to30AllAccountsJoint())
                .dpd31to90AllAccountsJoint(dto.getDpd31to90AllAccountsJoint())
                .dpdGreaterThan90AllAccountsJoint(dto.getDpdGreaterThan90AllAccountsJoint())
                .dpd0LiveAccountsJoint(dto.getDpd0LiveAccountsJoint())
                .dpd1to30LiveAccountsJoint(dto.getDpd1to30LiveAccountsJoint())
                .dpd31to90LiveAccountsJoint(dto.getDpd31to90LiveAccountsJoint())
                .dpdGreaterThan90LiveAccountsJoint(dto.getDpdGreaterThan90LiveAccountsJoint())
                .dpd0ClosedAccountsJoint(dto.getDpd0ClosedAccountsJoint())
                .dpd1to30ClosedAccountsJoint(dto.getDpd1to30ClosedAccountsJoint())
                .dpd31to90ClosedAccountsJoint(dto.getDpd31to90ClosedAccountsJoint())
                .dpdGreaterThan90ClosedAccountsJoint(dto.getDpdGreaterThan90ClosedAccountsJoint())
                .dpd0AllAccountsGuarantor(dto.getDpd0AllAccountsGuarantor())
                .dpd1to30AllAccountsGuarantor(dto.getDpd1to30AllAccountsGuarantor())
                .dpd31to90AllAccountsGuarantor(dto.getDpd31to90AllAccountsGuarantor())
                .dpdGreaterThan90AllAccountsGuarantor(dto.getDpdGreaterThan90AllAccountsGuarantor())
                .dpd0LiveAccountsGuarantor(dto.getDpd0LiveAccountsGuarantor())
                .dpd1to30LiveAccountsGuarantor(dto.getDpd1to30LiveAccountsGuarantor())
                .dpd31to90LiveAccountsGuarantor(dto.getDpd31to90LiveAccountsGuarantor())
                .dpdGreaterThan90LiveAccountsGuarantor(dto.getDpdGreaterThan90LiveAccountsGuarantor())
                .dpd0ClosedAccountsGuarantor(dto.getDpd0ClosedAccountsGuarantor())
                .dpd1to30ClosedAccountsGuarantor(dto.getDpd1to30ClosedAccountsGuarantor())
                .dpd31to90ClosedAccountsGuarantor(dto.getDpd31to90ClosedAccountsGuarantor())
                .dpdGreaterThan90ClosedAccountsGuarantor(dto.getDpdGreaterThan90ClosedAccountsGuarantor())
                .build();
    }

}

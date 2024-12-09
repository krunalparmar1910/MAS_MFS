package com.pf.mas.mapper;

import com.pf.mas.model.dto.cibil.commercial.*;
import com.pf.mas.model.entity.commercial.*;
import com.pf.mas.utils.cibil.MappingRegistry;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CibilCommercialMapper {
    public abstract BdrYourInstitution toBdrYourInstitution(BdrYourInstitutionDTO bdrYourInstitutionDTO);
    public abstract BdrOutsideInstitution toBdrOutsideInstitution(BdrOutsideInstitutionDTO bdrOutsideInstitutionDTO);
    public abstract BorrowerAddressContactDetails toBorrowerAddressContactDetails(BorrowerAddressContactDetailsDTO borrowerAddressContactDetailsDTO);
    public abstract ReportIssuesVec toReportIssuesVec(ReportIssuesVecDTO reportIssuesVecDTO);

    @Mapping(target = "bdrYourInstitution", source = "bdrYourInstitutionDTO")
    @Mapping(target = "bdrOutsideInstitution", source = "bdrOutsideInstitutionDTO")
    public abstract BorrowerDelinquencyReportedOnBorrower toBorrowerDelinquencyReportedOnBorrower(BorrowerDelinquencyReportedOnBorrowerDTO borrowerDelinquencyReportedOnBorrowerDTO);

    public abstract BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months toBorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months(BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTO borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTO);

    @Mapping(target = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months", source = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTOList")
    public abstract BorrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVec toBorrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVec(BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO);

    public abstract BorrowerIdDetails toBorrowerIdDetails(BorrowerIdDetailsDTO borrowerIdDetailsDTO);

    public abstract OtherIdData toOtherIdData(OtherIdDataDTO otherIdDataDTO);

    @Mapping(target = "otherIdDataList", source = "otherIdDetailsDTOS.otherIdDataDTOList")
    @Mapping(target = "borrowerIdDetailsList", source = "borrowerIdDetailsDTOS")
    public abstract BorrowerIdDetailsVec toBorrowerIdDetailsVec(BorrowerIdDetailsVecDTO borrowerIdDetailsVecDTO);


    @Mapping(target = "borrowerDetails", source = "borrowerDetailsDTO")
    @Mapping(target = "borrowerAddressContactDetails", source = "borrowerAddressContactDetailsDTO")
    @Mapping(target = "borrowerIdDetailsVec", source = "borrowerIdDetailsVecDTO")
    @Mapping(target = "borrowerDelinquencyReportedOnBorrower", source = "borrowerDelinquencyReportedOnBorrowerDTO")
    @Mapping(target = "borrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVecList", source = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO")
    @Mapping(target = "disputeRemarks", source = "disputeRemarksDTO")
    public abstract BorrowerProfileSec toBorrowerProfileSec(BorrowerProfileSecDTO borrowerProfileSecDTO);
    public abstract CfHistoryForAcOrDpd toCfHistoryForAcOrDpd(CfHistoryForAcOrDpdDTO cfHistoryForAcOrDpdDTO);
    public abstract ChequeDishounouredDuetoInsufficientFunds toChequeDishounouredDuetoInsufficientFunds(ChequeDishounouredDuetoInsufficientFundsDTO chequeDishounouredDuetoInsufficientFundsDTO);
    public abstract CountOfCreditFacilities toCountOfCreditFacilities(CountOfCreditFacilitiesDTO countOfCreditFacilitiesDTO);

    @Mapping(target = "delinquentOutstanding", source = "delinquentOutstandingDTO")
    @Mapping(target = "delinquentCf", source = "delinquentCfDTO")
    @Mapping(target = "totalOutstanding", source = "totalOutstandingDTO")
    @Mapping(target = "totalCf", source = "totalCfDTO")
    public abstract CpsInstitutionWise toCpsInstitutionWise(CpsInstitutionWiseDTO cpsInstitutionWiseDTO);

    public abstract CpsInstitutionWiseTotal toCpsInstitutionWiseTotal(CpsInstitutionWiseTotalDTO cpsInstitutionWiseTotalDTO);
    @Mapping(target = "otherPublicSectorBanks", source = "otherPublicSectorBanksDTO")
    @Mapping(target = "otherPrivateForeignBanks", source = "otherPrivateForeignBanksDTO")
    @Mapping(target = "nbfcOthers", source = "nbfcOthersDTO")
    @Mapping(target = "outsideTotal", source = "outsideTotalDTO")
    public abstract CpsOutsideInstitution toCpsOutsideInstitution(CpsOutsideInstitutionDTO cpsOutsideInstitutionDTO);

    public abstract CreditFacilitiesDetailsSec toCreditFacilitiesDetailsSec(CreditFacilitiesDetailsSecDTO creditFacilitiesDetailsSecDTO);


    @Mapping(target = "creditFacilitiesDetailsSecList", source = "creditFacilitiesDetailsSecDTOList")
    public abstract CreditFacilitiesDetailsVec toCreditFacilitiesDetailsVec(CreditFacilitiesDetailsVecDTO creditFacilitiesDetailsVecDTO);

    public abstract CreditFacilityCurrentAmountDetails toCreditFacilityCurrentAmountDetails(CreditFacilityCurrentAmountDetailsDTO creditFacilityCurrentAmountDetailsDTO);

    public abstract SummaryOfCreditFacilitiesRec toSummaryOfCreditFacilitiesRec(SummaryOfCreditFacilitiesRecDTO summaryOfCreditFacilitiesRecDTO);


    @Mapping(target = "summaryOfCreditFacilitiesRecList", source = "summaryOfCreditFacilitiesVecDTO.summaryOfCreditFacilitiesRecDTOList")
    @Mapping(target = "countOfCreditFacilities", source = "countOfCreditFacilitiesDTO")
    public abstract CreditFacilitiesSummary toCreditFacilitiesSummary(CreditFacilitiesSummaryDTO creditFacilitiesSummaryDTO);
    public abstract CreditFacilityCurrentDatesDetail toCreditFacilityCurrentDatesDetail(CreditFacilityCurrentDatesDetailDTO creditFacilityCurrentDatesDetailDTO);
    public abstract CreditFacilityCurrentDetails toCreditFacilityCurrentDetails(CreditFacilityCurrentDetailsDTO creditFacilityCurrentDetailsDTO);
    public abstract CreditFacilityCurrentMiscDetail toCreditFacilityCurrentMiscDetail(CreditFacilityCurrentMiscDetailDTO creditFacilityCurrentMiscDetailDTO);

    public abstract GuarantorDetails toGuarantorDetails(GuarantorDetailsDTO guarantorDetailsDTO);
    public abstract GuarantorAddressContactDetails toGuarantorAddressContactDetails(GuarantorAddressContactDetailsDTO guarantorAddressContactDetailsDTO);

    public abstract GuarantorIdDetails toGuarantorIdDetails(GuarantorIdDetailsDTO guarantorIdDetailsDTO);

    @Mapping(target = "guarantorIdDetailsList", source = "guarantorIdDetailsDTOList")
    @Mapping(target = "otherIdDataList", source = "otherIdDetailsDTOList")
    public abstract GuarantorDetailsBorrowerIDDetailsVec toGuarantorDetailsBorrowerIDDetailsVec(GuarantorDetailsBorrowerIdDetailsVecDTO guarantorDetailsBorrowerIdDetailsVecDTO);

    @Mapping(target = "guarantorDetails", source = "guarantorDetailsDTO")
    @Mapping(target = "guarantorAddressContactDetails", source = "guarantorAddressContactDetailsDTO")
    @Mapping(target = "guarantorDetailsBorrowerIDDetailsVec", source = "guarantorDetailsBorrowerIdDetailsVecDTO")
    public abstract CreditFacilityGuarantorDetails toCreditFacilityGuarantorDetails(CreditFacilityGuarantorDetailsDTO creditFacilityGuarantorDetailsDTO);

    @Mapping(target = "creditFacilityGuarantorDetailsList", source = "creditFacilityGuarantorDetailsDTOList")
    public abstract CreditFacilityGuarantorDetailsVec toCreditFacilityGuarantorDetailsVec(CreditFacilityGuarantorDetailsVecDTO creditFacilityGuarantorDetailsVecDTO);

    public abstract BorrowerDetails toBorrowerDetails(BorrowerDetailsDTO borrowerDetailsDTO);


    @Mapping(target = "borrowerDetails", source = "borrowerDetailsDTO")
    @Mapping(target = "borrowerAddressContactDetailsList", source = "borrowerAddressContactDetailsDTOList")
    @Mapping(target = "borrowerIdDetailsVec", source = "borrowerIdDetailsVecDTO")
    public abstract BorrowerProfileGuarantorSec toBorrowerProfileGuarantorSec(BorrowerProfileGuarantorSecDTO borrowerProfileGuarantorSecDTO);

    @Mapping(target = "creditFacilityCurrentDetails", source = "creditFacilityCurrentDetailsDTO")
    @Mapping(target = "borrowerProfileGuarantorSec", source = "borrowerProfileGuarantorSecDTO")
    public abstract CreditFacilityDetailsAsGuarantorRec toCreditFacilityDetailsAsGuarantorRec(CreditFacilityDetailsAsGuarantorRecDTO creditFacilityDetailsAsGuarantorRecDTO);

    @Mapping(target = "creditFacilityDetailsAsGuarantorRecList", source = "creditFacilityDetailsAsGuarantorRecDTOList")
    public abstract CreditFacilityDetailsAsGuarantorVec toCreditFacilityDetailsAsGuarantorVec(CreditFacilityDetailsAsGuarantorVecDTO creditFacilityDetailsAsGuarantorVecDTO);

    @Mapping(target = "creditFacilityDetailsAsBorrowerSec", source = "creditFacilityDetailsAsBorrowerSecDTOList")
    public abstract CreditFacilityDetailsAsBorrowerSecVec toCreditFacilityDetailsAsBorrowerSecVec(CreditFacilityDetailsAsBorrowerSecVecDTO creditFacilityDetailsAsBorrowerSecVecDTO);

    @Mapping(target = "creditFacilityCurrentDetailsList", source = "creditFacilityCurrentDetailsVecDTO.creditFacilityCurrentDetailsDTOList")
    @Mapping(target = "cfHistoryForAcOrDpdList", source = "cfHistoryForAcOrDpdVecDTO.cfHistoryForAcOrDpdDTOList")
    @Mapping(target = "creditFacilityOverdueDetailsVec", source = "creditFacilityOverdueDetailsVecDTO")
    @Mapping(target = "chequeDishounouredDuetoInsufficientFunds", source = "chequeDishounouredDuetoInsufficientFundsDTO")
    @Mapping(target = "creditFacilitySecurityDetailsVec", source = "creditFacilitySecurityDetailsVecDTO")
    @Mapping(target = "creditFacilityGuarantorDetailsVec", source = "creditFacilityGuarantorDetailsVecDTO")
    public abstract CreditFacilityDetailsAsBorrowerSec toCreditFacilityDetailsAsBorrowerSec(CreditFacilityDetailsAsBorrowerSecDTO creditFacilityDetailsAsBorrowerSecDTO);


    public abstract CreditFacilityOverdueDetails toCreditFacilityOverdueDetails(CreditFacilityOverdueDetailsDTO creditFacilityOverdueDetailsDTO);
    @Mapping(target = "creditFacilityOverdueDetails", source = "creditFacilityOverdueDetails")
    public abstract CreditFacilityOverdueDetailsVec toCreditFacilityOverdueDetailsVec(CreditFacilityOverdueDetailsVecDTO creditFacilityOverdueDetailsVecDTO);
    public abstract CreditFacilitySecurityDetails toCreditFacilitySecurityDetails(CreditFacilitySecurityDetailsDTO creditFacilitySecurityDetailsDTO);
    @Mapping(target = "creditFacilitySecurityDetails", source = "creditFacilitySecurityDetailsDTOList")
    public abstract CreditFacilitySecurityDetailsVec toCreditFacilitySecurityDetailsVec(CreditFacilitySecurityDetailsVecDTO creditFacilitySecurityDetailsVecDTO);

    @Mapping(target = "cpsYourInstitution", source = "cpsYourInstitutionDTO")
    @Mapping(target = "cpsOutsideInstitution", source = "cpsOutsideInstitutionDTO")
    @Mapping(target = "total", source = "totalDTO")
    public abstract CreditProfileSummarySec toCreditProfileSummarySec(CreditProfileSummarySecDTO creditProfileSummarySecDTO);

    @Mapping(target = "totalCf", source = "totalCfDTO")
    @Mapping(target = "delinquentCf", source = "delinquentCfDTO")
    @Mapping(target = "delinquentOutstanding", source = "delinquentOutstandingDTO")
    public abstract CpsInstitutionWise toCpsInstitutionWise(CpsYourInstitutionDTO cpsYourInstitutionDTO);

    public abstract CreditRatingSummaryDetailsVec toCreditRatingSummaryDetailsVec(CreditRatingSummaryDetailsVecDTO creditRatingSummaryDetailsVecDTO);

    @Mapping(target = "creditRatingSummaryDetailsVecList", source = "creditRatingSummaryDetailsVecDTOList")
    public abstract CreditRatingSummaryRec toCreditRatingSummaryRec(CreditRatingSummaryRecDTO creditRatingSummaryRecDTO);

    @Mapping(target = "creditRatingSummaryRec", source = "creditRatingSummaryRecDTO")
    public abstract CreditRatingSummaryVec toCreditRatingSummaryVec(CreditRatingSummaryVecDTO creditRatingSummaryVecDTO);

    public abstract CreditVision toCreditVision(CreditVisionDTO creditVisionDTO);

    @Mapping(target = "creditVisionList", source = "creditVisionDTOList")
    public abstract CreditVisionSec toCreditVisionSec(CreditVisionSecDTO creditVisionSecDTO);

    public abstract DelinquentCf toDelinquentCf(DelinquentCfDTO delinquentCfDTO);
    public abstract DelinquentOutstanding toDelinquentOutstanding(DelinquentOutstandingDTO delinquentOutstandingDTO);
    public abstract DerogatoryInformationBorrower toDerogatoryInformationBorrower(DerogatoryInformationBorrowerDTO derogatoryInformationBorrowerDTO);
    public abstract DerogatoryInformationFinancialHistory toDerogatoryInformationFinancialHistory(DerogatoryInformationFinancialHistoryDTO derogatoryInformationFinancialHistoryDTO);
    public abstract DerogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec toDerogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec(DerogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSecDTO derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSecDTO);

    @Mapping(target = "derogatoryInformationReportedOnGuarantedPartiesVec", source = "derogatoryInformationReportedOnGuarantedPartiesVec.derogatoryInformationReportedOnGuarantedParties")
    @Mapping(target = "derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec", source = "derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSecDTO")
    @Mapping(target = "derogatoryInformationBorrower", source = "derogatoryInformationBorrowerDTO")
    public abstract DerogatoryInformationSec toDerogatoryInformationSec(DerogatoryInformationSecDTO derogatoryInformationSecDTO);
    public abstract DisputeRemarks toDisputeRemarks(DisputeRemarksDTO disputeRemarksDTO);
    public abstract DpdDetails toDpdDetails(DpdDetailsDTO dpdDetailsDTO);
    public abstract EnquiryDetails toEnquiryDetails(EnquiryDetailsDTO enquiryDetailsDTO);
    public abstract EnquiryDetailsHistory toEnquiryDetailsHistory(EnquiryDetailsHistoryDTO enquiryDetailsHistoryDTO);
    @Mapping(target = "enquiryDetailsHistory", source = "enquiryDetailsHistoryDTO")
    public abstract EnquiryDetailsVec toEnquiryDetailsVec(EnquiryDetailsVecDTO enquiryDetailsVecDTO);
    public abstract EnquiryInfoAddress toEnquiryInfoAddress(EnquiryInfoAddressDTO enquiryInfoAddressDTO);
    @Mapping(target = "enquiryInfoAddressList", source = "addressVecDTO.enquiryInfoAddressDTOList")
    public abstract EnquiryInformationRec toEnquiryInformationRec(EnquiryInformationRecDTO enquiryInformationRecDTO);
    @Mapping(target = "enquiryYourInstitution", source = "enquiryYourInstitution.enquiryDetailsDTO")
    @Mapping(target = "enquiryOutsideInstitution", source = "enquiryOutsideInstitution.enquiryDetailsDTO")
    @Mapping(target = "enquiryTotal", source = "enquiryTotal.enquiryDetailsDTO")
    public abstract EnquirySummarySec toEnquirySummarySec(EnquirySummarySecDTO enquirySummarySecDTO);
    @Mapping(target = "locationInformationList", source = "locationInformationDTOList")
    public abstract LocationDetailsSec toLocationDetailsSec(LocationDetailsSecDTO locationDetailsSecDTO);
    public abstract LocationInformation toLocationInformation(LocationInformationDTO locationInformationDTO);
    public abstract NonStdVec toNonStdVec(NonStdVecDTO nonStdVecDTO);
    public abstract NoOfCreditProviders toNoOfCreditProviders(NoOfCreditProvidersDTO noOfCreditProvidersDTO);
    public abstract ObCfAndAssetClassificationFinancialTransactionSummary toObCfAndAssetClassificationFinancialTransactionSummary(ObCfAndAssetClassificationFinancialTransactionSummaryDTO obCfAndAssetClassificationFinancialTransactionSummaryDTO);
    public abstract ObCfAndAssetClassificationSec toObCfAndAssetClassificationSec(ObCfAndAssetClassificationSecDTO obCfAndAssetClassificationSecDTO);

    @Mapping(target = "rankVec", source = "rankVecDTO")
    public abstract RankSec toRankSec(RankSecDTO rankSecDTO);


    @Mapping(target = "stdVec", source = "stdVecDTO")
    @Mapping(target = "nonStdVec", source = "nonStdVecDTO")
    public abstract ObCfAndAssetClassificationTransactionTypeDetails toObCfAndAssetClassificationTransactionTypeDetails(ObCfAndAssetClassificationTransactionTypeDetailsDTO obCfAndAssetClassificationTransactionTypeDetailsDTO);
    @Mapping(target = "borrowerProfileSec", source = "borrowerProfileSecDTO")
    @Mapping(target = "rankSec", source = "rankSecDTO")
    @Mapping(target = "creditVisionSec", source = "creditVisionSecDTO")
    @Mapping(target = "creditProfileSummarySec", source = "creditProfileSummarySecDTO")
    @Mapping(target = "enquirySummarySec", source = "enquirySummarySecDTO")
    @Mapping(target = "derogatoryInformationSec", source = "derogatoryInformationSecDTO")
    @Mapping(target = "obCfAndAssetClassificationSec", source = "obCfAndAssetClassificationSecDTO")
    @Mapping(target = "locationDetailsSec", source = "locationDetailsSecDTO")
    @Mapping(target = "relationshipDetailsVec", source = "relationshipDetailsVecDTO")
    @Mapping(target = "creditFacilityDetailsasBorrowerSecVec", source = "creditFacilityDetailsasBorrowerSecVecDTO")
    @Mapping(target = "creditFacilityDetailsasGuarantorVec", source = "creditFacilityDetailsasGuarantorVecDTO")
    @Mapping(target = "suitFiledVec", source = "suitFiledVecDTO")
    @Mapping(target = "creditRatingSummaryVec", source = "creditRatingSummaryVecDTO")
    @Mapping(target = "enquiryDetailsVec", source = "enquiryDetailsVecDTO")
    @Mapping(target = "creditFacilitiesDetailsVec", source = "creditFacilitiesDetailsVecDTO")
    @Mapping(target = "creditFacilitiesSummary", source = "creditFacilitiesSummaryDTO")
    public abstract ProductSec toProductSec(ProductSecDTO productSecDTO);
    public abstract RankVec toRankVec(RankVecDTO rankVecDTO);

    @Mapping(target = "borrowerAddressContactDetails", source = "borrowerAddressContactDetailsDTO")
    @Mapping(target = "relationshipInformation", source = "relationshipInformationDTO")
    @Mapping(target = "borrowerIdDetailsVec", source = "borrowerIdDetailsVecDTO")
    public abstract RelationshipDetails toRelationshipDetails(RelationshipDetailsDTO relationshipDetailsDTO);
    public abstract RelationshipDetailsVec toRelationshipDetailsVec(RelationshipDetailsVecDTO relationshipDetailsVecDTO);
    public abstract RelationshipInformation toRelationshipInformation(RelationshipInformationDTO relationshipInformationDTO);
    public abstract ReportHeaderRec toReportHeaderRec(ReportHeaderRecDTO reportHeaderRecDTO);
    @Mapping(target = "productSec", source = "productSecDTO")
    @Mapping(target = "enquiryInformationRec", source = "enquiryInformationRecDTO")
    @Mapping(target = "reportHeaderRec", source = "reportHeaderRecDTO")
    @Mapping(target = "reportIssuesVecList", source = "reportIssuesVecDTOList")
    public abstract ResponseReport toResponseReport(ResponseReportDTO responseReportDTO);

    public abstract StdVec toStdVec(StdVecDTO stdVecDTO);

    public abstract SuitFiledDetails toSuitFiledDetails(SuitFiledDetailsDTO suitFiledDetailsDTO);

    public abstract SuitFiledRecord toSuitFiledRecord(SuitFiledRecordDTO suitFiledRecordDTO);

    @Mapping(target = "suitFiledRecordList", source = "suitFiledRecordDTOList")
    public abstract SuitFiledVec toSuitFiledVec(SuitFiledVecDTO suitFiledVecDTO);
    public abstract TotalCf toTotalCf(TotalCfDTO totalCfDTO);
    public abstract TotalOutstanding toTotalOutstanding(TotalOutstandingDTO totalOutstandingDTO);
    @Mapping(target = "responseReport", source = "rawResponseDTO.base.responseReportDTO")
    public abstract DsCommercialCir toDsCommercialCir(DsCommercialCIRDTO dsCommercialCIRDTO);

    public abstract ApplicantAddressCommercial toApplicantAddressCommercial(ApplicantAddressCommercialDTO applicantAddressCommercialDTO);

    @AfterMapping
    public void mapFieldsForApplicantAddressCommercial(@MappingTarget ApplicantAddressCommercial applicantAddressCommercial, ApplicantAddressCommercialDTO applicantAddressCommercialDTO) {
        applicantAddressCommercial.setAddressType(MappingRegistry.getCreditReportAddressCategory(applicantAddressCommercialDTO.getAddressType()));
        applicantAddressCommercial.setResidenceType(MappingRegistry.getCreditReportAddressResidenceCode(applicantAddressCommercialDTO.getResidenceType()));
    }

    @Mapping(target = "identifiers", source = "identifiers.identifierDTOList")
    @Mapping(target = "addresses", source = "addresses.applicantAddressCommercialDTOList")
    @Mapping(target = "applicantRegisteredAddressCommercial", source = "registeredAddress")
    @Mapping(target = "telephones", source = "applicantTelephonesCommercialDTO.applicantTelephoneCommercialDTO")
    @Mapping(target = "otherAddresses", source = "otherAddressesVec.otherAddressesCommercialDTO")
    public abstract ApplicantCommercial toApplicantCommercial(ApplicantCommercialDTO applicantCommercialDTO);

    @AfterMapping
    public void mapFieldsForTelephoneCommercial(@MappingTarget ApplicantTelephoneCommercial applicantTelephoneCommercial, ApplicantTelephoneCommercialDTO applicantTelephoneCommercialDTO) {
        applicantTelephoneCommercial.setTelephoneType(MappingRegistry.getTelephoneSegmentResponseTelephoneType(applicantTelephoneCommercialDTO.getTelephoneType()));
    }
    public abstract ApplicantTelephoneCommercial toApplicantTelephoneCommercial(ApplicantTelephoneCommercialDTO applicantTelephoneCommercialDTO);



    public abstract List<ApplicantCommercial> toApplicantCommercialList(List<ApplicantCommercialDTO> applicantCommercialDTOList);

    public abstract ApplicationDataCommercial toApplicationDataCommercial(ApplicationDataCommercialDTO applicationDataCommercialDTO);


    public CombinedResponseCommercial toCombinedResponseCommercial(ContextDataCommercialDTO contextDataCommercialDTO){
        List<FieldCommercialDTO> fieldCommercialDTOList = contextDataCommercialDTO.getFieldDTOList();
        if(fieldCommercialDTOList.isEmpty()) {
            return null;
        }
        CombinedResponseCommercial combinedResponseCommercial = new CombinedResponseCommercial();
        for(FieldCommercialDTO eachFieldCommercialDTO : fieldCommercialDTOList) {
            if(eachFieldCommercialDTO.getApplicantsCommercialDTO() != null) {
                combinedResponseCommercial.setApplicantCommercialList(toApplicantCommercialList(eachFieldCommercialDTO.getApplicantsCommercialDTO().getApplicantCommercialDTOList()));
            }
            if(eachFieldCommercialDTO.getApplicationDataDTO() != null) {
                combinedResponseCommercial.setApplicationData(toApplicationDataCommercial(eachFieldCommercialDTO.getApplicationDataDTO()));
            }
            if(eachFieldCommercialDTO.getKey() != null && eachFieldCommercialDTO.getKey().equals("Decision")) {
                combinedResponseCommercial.setDecision(eachFieldCommercialDTO.getValue());
            }
            if(eachFieldCommercialDTO.getKey() != null && eachFieldCommercialDTO.getKey().equals("ApplicationId")) {
                combinedResponseCommercial.setApplicationId(eachFieldCommercialDTO.getValue());
            }
        }
        return combinedResponseCommercial;
    }

    @Mapping(target = "combinedResponseCommercial", source = "contextDataCommercialDTO")
    @Mapping(target = "authenticationInfo", source = "authentication")
    public abstract EnvelopeResponseCommercial toEnvelopeResponseCommercial(DcResponseCommercialDTO dcResponseCommercialDTO);

}

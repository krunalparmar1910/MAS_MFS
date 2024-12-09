package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "productSec")
public class ProductSecDTO {
    @JacksonXmlProperty(localName = "borrowerProfileSec")
    private BorrowerProfileSecDTO borrowerProfileSecDTO;
    @JacksonXmlProperty(localName = "rankSec")
    private RankSecDTO rankSecDTO;
    @JacksonXmlProperty(localName = "creditVisionSec")
    private CreditVisionSecDTO creditVisionSecDTO;
    @JacksonXmlProperty(localName = "creditProfileSummarySec")
    private CreditProfileSummarySecDTO creditProfileSummarySecDTO;
    @JacksonXmlProperty(localName = "enquirySummarySec")
    private EnquirySummarySecDTO enquirySummarySecDTO;
    @JacksonXmlProperty(localName = "derogatoryInformationSec")
    private DerogatoryInformationSecDTO derogatoryInformationSecDTO;
    @JacksonXmlProperty(localName = "oustandingBalanceByCFAndAssetClasificationSec")
    private ObCfAndAssetClassificationSecDTO obCfAndAssetClassificationSecDTO;
    @JacksonXmlProperty(localName = "locationDetailsSec")
    private LocationDetailsSecDTO locationDetailsSecDTO;
    @JacksonXmlProperty(localName = "relationshipDetailsVec")
    private RelationshipDetailsVecDTO relationshipDetailsVecDTO;
    @JacksonXmlProperty(localName = "creditFacilityDetailsasBorrowerSecVec")
    private CreditFacilityDetailsAsBorrowerSecVecDTO creditFacilityDetailsasBorrowerSecVecDTO;
    @JacksonXmlProperty(localName = "creditFacilityDetailsasGuarantorVec")
    private CreditFacilityDetailsAsGuarantorVecDTO creditFacilityDetailsasGuarantorVecDTO;
    @JacksonXmlProperty(localName = "suitFiledVec")
    private SuitFiledVecDTO suitFiledVecDTO;
    @JacksonXmlProperty(localName = "creditRatingSummaryVec")
    private CreditRatingSummaryVecDTO creditRatingSummaryVecDTO;
    @JacksonXmlProperty(localName = "enquiryDetailsVec")
    private EnquiryDetailsVecDTO enquiryDetailsVecDTO;
    @JacksonXmlProperty(localName = "creditFacilitiesDetailsVec")
    private CreditFacilitiesDetailsVecDTO creditFacilitiesDetailsVecDTO;
    @JacksonXmlProperty(localName = "creditFacilitiesSummary")
    private CreditFacilitiesSummaryDTO creditFacilitiesSummaryDTO;
}

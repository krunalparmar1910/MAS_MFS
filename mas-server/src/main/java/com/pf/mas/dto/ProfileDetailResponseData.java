package com.pf.mas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
public class ProfileDetailResponseData {
    private final String uuid;

    private final String panNumber;

    private final String gstNumber;

    private final String legalName;

    private final String tradeName;

    private final String constitutionOfBusiness;

    private final String natureOfBusiness;

    private final String natureOfCoreBusinessActivity;

    private final String status;

    private final String taxPayerType;

    private final String address;

    private final String placeOfBusiness;

    private final String state;

    private final String stateJurisdiction;

    private final String stateJurisdictionCode;

    private final String centerJurisdiction;

    private final String centerJurisdictionCode;

    private final String latitude;

    private final String longitude;

    private final LocalDate dateOfRegistration;

    private final LocalDate dateOfCancellation;

    private final LocalDate lastUpdatedDate;
}

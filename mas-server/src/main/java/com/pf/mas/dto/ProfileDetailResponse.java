package com.pf.mas.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder
@Jacksonized
public class ProfileDetailResponse {
    private String clientOrderId;

    private String entityId;

    private List<ProfileDetailResponseData> profileDetails;
}

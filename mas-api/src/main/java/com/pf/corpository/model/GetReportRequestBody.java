package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetReportRequestBody {
    @ToString.Include
    @JsonProperty("client-order-id")
    private final String clientOrderId;

    @JsonProperty("pan")
    private final String pan;

    @JsonProperty("user_id")
    private final Integer userId;

    @JsonProperty("api_auth_token")
    private final String apiAuthToken;
}

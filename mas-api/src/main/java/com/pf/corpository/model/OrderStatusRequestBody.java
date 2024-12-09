package com.pf.corpository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusRequestBody {
    @JsonProperty("api_auth_token")
    private final String apiAuthToken;

    @JsonProperty("user_id")
    private final Integer userId;

    @JsonProperty("client-order-id")
    private final String clientOrderId;
}

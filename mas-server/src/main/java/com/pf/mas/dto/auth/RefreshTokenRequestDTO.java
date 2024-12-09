package com.pf.mas.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RefreshTokenRequestDTO {
    private final String clientId;
    private final String refreshToken;
}

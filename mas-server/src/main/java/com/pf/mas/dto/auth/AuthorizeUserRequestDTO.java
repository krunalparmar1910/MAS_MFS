package com.pf.mas.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AuthorizeUserRequestDTO {
    private final String username;
    private final String password;
    private final String clientId;
}

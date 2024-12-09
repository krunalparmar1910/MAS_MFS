package com.pf.mas.service.authservice;


import com.pf.mas.dto.auth.AuthorizeUserRequestDTO;
import com.pf.mas.dto.auth.RefreshTokenRequestDTO;
import org.keycloak.representations.AccessTokenResponse;

import java.util.Optional;

public interface AuthService {

    Optional<AccessTokenResponse> getAccessTokenResponse(AuthorizeUserRequestDTO authorizeUserRequestDTO);

    Optional<AccessTokenResponse> getAccessTokenUsingRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}

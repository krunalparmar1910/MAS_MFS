package com.pf.mas.controller;

import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.dto.auth.AuthorizeUserRequestDTO;
import com.pf.mas.dto.auth.RefreshTokenRequestDTO;
import com.pf.mas.service.authservice.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/authorize")
    public ResponseEntity<AccessTokenResponse> getAccessTokenByUsernamePass(@RequestBody AuthorizeUserRequestDTO authorizeUserRequestDTO) {
        Optional<AccessTokenResponse> accessTokenResponse = authService.getAccessTokenResponse(authorizeUserRequestDTO);
        if (accessTokenResponse.isEmpty()) {
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Unable to generate token.");
        }
        return ResponseEntity.ok(accessTokenResponse.get());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse> getAccessTokenByUsernamePass(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        Optional<AccessTokenResponse> accessTokenResponse = authService.getAccessTokenUsingRefreshToken(refreshTokenRequestDTO);
        if (accessTokenResponse.isEmpty()) {
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Unable to generate token.");
        }
        return ResponseEntity.ok(accessTokenResponse.get());
    }
}

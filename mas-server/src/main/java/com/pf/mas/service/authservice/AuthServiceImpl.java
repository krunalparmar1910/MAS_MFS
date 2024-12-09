package com.pf.mas.service.authservice;

import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.dto.auth.AuthorizeUserRequestDTO;
import com.pf.mas.dto.auth.RefreshTokenRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Value("${auth-service.keycloak.api.keycloakBaseUrl:}")
    private String keycloakBaseUrl;
    @Value("${auth-service.keycloak.api.realm:mas}")
    private String realm;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<AccessTokenResponse> getAccessTokenResponse(AuthorizeUserRequestDTO authorizeUserRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", authorizeUserRequestDTO.getClientId());
        requestBody.add("username", authorizeUserRequestDTO.getUsername());
        requestBody.add("password", authorizeUserRequestDTO.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        String url = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        try {
            ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<AccessTokenResponse>() {
            });
            if (response.getStatusCode().is2xxSuccessful() && (response.getBody() != null && response.getBody().getToken() != null)) {
                return Optional.of(response.getBody());
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new MasRuntimeException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
            }
            throw httpClientErrorException;
        } catch (ResourceAccessException e) {
            log.error("Resource Access Exception for keycloak", e);
            throw new MasRuntimeException(HttpStatus.SERVICE_UNAVAILABLE, e.getLocalizedMessage());
        } catch (Exception ex) {
            log.error("Error fetching access token ", ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<AccessTokenResponse> getAccessTokenUsingRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", refreshTokenRequestDTO.getClientId());
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshTokenRequestDTO.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        String url = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        try {
            ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<AccessTokenResponse>() {
            });
            if (response.getStatusCode().is2xxSuccessful() && (response.getBody() != null && response.getBody().getToken() != null)) {
                return Optional.of(response.getBody());
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Invalid refresh token.");
            }
            throw httpClientErrorException;
        } catch (Exception ex) {
            log.error("Error while fetching access token ", ex);
        }

        return Optional.empty();
    }

}

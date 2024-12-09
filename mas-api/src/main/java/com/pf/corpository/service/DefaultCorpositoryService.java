package com.pf.corpository.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.enums.ProgressStatus;
import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.common.model.entity.APIInfoCredentials;
import com.pf.common.model.entity.CredentialType;
import com.pf.common.repository.APIInfoCredentialsRepository;
import com.pf.common.utils.EnvironmentUtils;
import com.pf.corpository.exception.MasRequestException;
import com.pf.corpository.model.GetReportRequest;
import com.pf.corpository.model.GetReportRequestBody;
import com.pf.corpository.model.MasGetReportRequest;
import com.pf.corpository.model.OrderStatusRequest;
import com.pf.corpository.model.OrderStatusResponse;
import com.pf.corpository.model.base.CorpositoryRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

@Slf4j
@Service
public class DefaultCorpositoryService implements CorpositoryService {
    private static final String BASE_URL = "corpository.base-url";
    private static final String BASE_API = "corpository.base-api";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final APIInfoCredentialsRepository apiInfoCredentialsRepository;
    private final String environmentBaseUrl;
    private final String environmentBaseApi;

    public DefaultCorpositoryService(
            Environment environment,
            OkHttpClient httpClient,
            ObjectMapper objectMapper,
            APIInfoCredentialsRepository apiInfoCredentialsRepository) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiInfoCredentialsRepository = apiInfoCredentialsRepository;
        environmentBaseUrl = EnvironmentUtils.readEnvironmentVariable(environment, BASE_URL);
        environmentBaseApi = EnvironmentUtils.readEnvironmentVariable(environment, BASE_API);
    }

    @Override
    public OrderStatusResponse getOrderStatus(OrderStatusRequest orderStatusRequest) throws MasRequestException, MasThirdPartyApiException {
        Request request = createPostRequest(orderStatusRequest);
        String response = performRequest(request);

        try {
            return objectMapper.readValue(response, OrderStatusResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Could not read value from {} into type {} due to {}", response, OrderStatusResponse.class.getSimpleName(), e.getLocalizedMessage());
            throw new MasRequestException(e);
        }
    }

    @Override
    public byte[] getReport(MasGetReportRequest reportRequest) throws MasRequestException, MasThirdPartyApiException {
        GetReportRequest getReportRequest = GetReportRequest.builder()
                .request(reportRequest.getRequest())
                .requestBody(GetReportRequestBody.builder()
                        .clientOrderId(reportRequest.getRequestBody().getClientOrderId())
                        .pan(reportRequest.getRequestBody().getPan())
                        .userId(reportRequest.getRequestBody().getUserId())
                        .apiAuthToken(reportRequest.getRequestBody().getApiAuthToken())
                        .build())
                .build();
        Request request = createPostRequest(getReportRequest);
        return performRequestAndGetByteArrayResponse(request);
    }

    private String performRequest(Request request) throws MasThirdPartyApiException {
        try (Response response = httpClient.newCall(request).execute()) {
            final String baseApi = getBaseApi();
            final ResponseBody body = response.body();
            String bodyAsString = body == null ? "<null>" : body.string();

            if (response.code() == HTTP_OK) {
                return bodyAsString;
            } else if (response.code() == HTTP_UNAUTHORIZED) {
                log.error("Not authenticated. Got code {}: {}", response.code(), bodyAsString);
                throw new MasRequestException(baseApi, response.code(), bodyAsString);
            } else {
                log.debug("Corpository returned code {} for endpoint {} : body={}", response.code(), baseApi, bodyAsString);
                throw new MasRequestException(baseApi, response.code(), bodyAsString);
            }
        } catch (Exception e) {
            log.error("Unexpected error while performing request {}", e.getLocalizedMessage());
            throw new MasThirdPartyApiException(HttpStatus.SERVICE_UNAVAILABLE, e.getLocalizedMessage(), ProgressStatus.API_CALL_FAILED);
        }
    }

    private byte[] performRequestAndGetByteArrayResponse(Request request) throws MasThirdPartyApiException, MasRequestException {
        try (Response response = httpClient.newCall(request).execute()) {
            final String baseApi = getBaseApi();
            final ResponseBody body = response.body();

            if (response.code() == HTTP_OK) {
                return body != null ? body.byteStream().readAllBytes() : null;
            } else if (response.code() == HTTP_UNAUTHORIZED) {
                log.error("Not authenticated. Got code {}", response.code());
                throw new MasRequestException(baseApi, response.code());
            } else {
                log.debug("Corpository returned code {} for endpoint {}", response.code(), baseApi);
                throw new MasRequestException(baseApi, response.code());
            }
        } catch (MasRequestException masRequestException) {
            throw masRequestException;
        } catch (Exception e) {
            log.error("Unexpected error while performing request {}", e.getLocalizedMessage(), e);
            throw new MasThirdPartyApiException(HttpStatus.SERVICE_UNAVAILABLE, e.getLocalizedMessage(), ProgressStatus.API_CALL_FAILED);
        }
    }

    private Request createPostRequest(CorpositoryRequest request) throws MasRequestException {
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize request into string due to {}", e.getLocalizedMessage());
            throw new MasRequestException(e);
        }
        RequestBody body = RequestBody.create(requestBody, null);
        return new Request.Builder()
                .url(getBaseUrl() + getBaseApi())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .post(body)
                .build();
    }

    private String getBaseUrl() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.CORPOSITORY_GST);
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getBaseUrl())) {
            log.warn("No base url was found for type {}, using default values configured in application.yml", CredentialType.CORPOSITORY_GST.getName());
            return environmentBaseUrl;
        } else {
            return apiInfoCredentials.getBaseUrl();
        }
    }

    private String getBaseApi() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.CORPOSITORY_GST);
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getBaseApi())) {
            log.warn("No base api was found for type {}, using default values configured in application.yml", CredentialType.CORPOSITORY_GST.getName());
            return environmentBaseApi;
        } else {
            return apiInfoCredentials.getBaseApi();
        }
    }
}

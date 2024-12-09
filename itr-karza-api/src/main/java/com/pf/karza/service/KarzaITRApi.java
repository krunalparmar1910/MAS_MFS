package com.pf.karza.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.enums.ProgressStatus;
import com.pf.common.exception.MasRuntimeException;
import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.common.model.entity.APIInfoCredentials;
import com.pf.common.model.entity.CredentialType;
import com.pf.common.repository.APIInfoCredentialsRepository;
import com.pf.common.utils.EnvironmentUtils;
import com.pf.karza.constant.KarzaITRConstants;
import com.pf.karza.model.dto.request.ItrRequest;
import com.pf.karza.model.dto.request.KarzaItrAdvancedRequest;
import com.pf.karza.model.dto.request.KarzaItrBusinessRequest;
import com.pf.karza.model.dto.request.KarzaItrSalariedRequest;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.UserRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class KarzaITRApi {
    private static final String API_VERSION = "itr.api-version";
    private static final String KARZA_KEY = "itr.karza-key";
    private static final String BASE_URL = "itr.base-url";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final ErrorLoggingService errorLoggingService;
    private final BusinessITRDataStore businessITRDataStore;
    private final SalariedITRDataStore salariedITRDataStore;
    private final AdvancedITRDataStore advancedITRDataStore;
    private final KarzaITREncryptionService karzaITREncryptionService;
    private final APIInfoCredentialsRepository apiInfoCredentialsRepository;
    private final String environmentApiVersion;
    private final String environmentKarzaKey;
    private final String environmentBaseUrl;

    public KarzaITRApi(
            Environment environment,
            OkHttpClient httpClient,
            ObjectMapper objectMapper,
            ModelMapper modelMapper,
            ErrorLoggingService errorLoggingService,
            BusinessITRDataStore businessITRDataStore,
            SalariedITRDataStore salariedITRDataStore,
            AdvancedITRDataStore advancedITRDataStore,
            KarzaITREncryptionService karzaITREncryptionService,
            APIInfoCredentialsRepository apiInfoCredentialsRepository) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.errorLoggingService = errorLoggingService;
        this.businessITRDataStore = businessITRDataStore;
        this.salariedITRDataStore = salariedITRDataStore;
        this.advancedITRDataStore = advancedITRDataStore;
        this.karzaITREncryptionService = karzaITREncryptionService;
        this.apiInfoCredentialsRepository = apiInfoCredentialsRepository;
        environmentApiVersion = EnvironmentUtils.readEnvironmentVariable(environment, API_VERSION);
        environmentKarzaKey = EnvironmentUtils.readEnvironmentVariable(environment, KARZA_KEY);
        environmentBaseUrl = EnvironmentUtils.readEnvironmentVariable(environment, BASE_URL);
    }

    public ResponseEntity<ItrResponse> getItrData(
            ItrRequest itrRequest,
            Object request,
            UserRequest userRequest,
            String karzaKey,
            String endpoint,
            String masRefId,
            String ipAddress,
            String createdBy) throws MasThirdPartyApiException {
        long startTime = System.currentTimeMillis(); // Capture start time

        String apiUrl = getBaseUrl() + endpoint; // Use PROD_HOST for production

        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(objectMapper.writeValueAsString(request), MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Error creating request body.");
        }

        Request httpRequest = new Request.Builder()
                .url(apiUrl)
                .addHeader(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                .addHeader(KarzaITRConstants.KARZA_KEY_HEADER, karzaKey)
                .post(requestBody)
                .build();

        String rawResponse = null;
        String responseMessage;

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            final ResponseBody body = response.body();
            String bodyAsString = body == null ? "<null>" : body.string();
            rawResponse = bodyAsString;
            responseMessage = response.message();
            log.info("Request URL: {}", apiUrl);
            log.info("Response Code: {}", response.code());

            JsonNode jsonNode = objectMapper.readTree(bodyAsString);
            int statusCode = jsonNode.path("statusCode").asInt(101);
            if (statusCode != 101) {
                errorLoggingService.saveRawData(response.message(), bodyAsString, itrRequest, ProgressStatus.API_CALL_FAILED);
                return ResponseEntity.ok(ItrResponse.builder()
                        .statusCode(statusCode)
                        .error(jsonNode.has("error") ? jsonNode.path("error").asText() : null)
                        .requestId(jsonNode.path("requestId").asText())
                        .result(objectMapper.readValue(jsonNode.path("result").toString(), Map.class))
                        .build());
            }

            if (response.code() == HttpStatus.OK.value()) {
                errorLoggingService.saveRawData("Itr data fetched successfully", response.toString(), itrRequest, ProgressStatus.COMPLETED);
            } else {
                errorLoggingService.saveRawData(response.message(), bodyAsString, itrRequest, ProgressStatus.API_CALL_FAILED);
            }

            if (response.code() == HttpStatus.OK.value()) {
                ItrResponse itrResponse = null;
                if (KarzaITRConstants.ITR_BUSINESS_ENDPOINT.equals(endpoint)) {
                    itrResponse = businessITRDataStore.saveBusinessITRData(userRequest, bodyAsString, masRefId, ipAddress, createdBy);
                } else if (KarzaITRConstants.ITR_SALARIED_ENDPOINT.equals(endpoint)) {
                    itrResponse = salariedITRDataStore.saveSalariedITRData(userRequest, bodyAsString, masRefId, ipAddress, createdBy);
                } else if (KarzaITRConstants.ITR_ADVANCED_ENDPOINT.equals(endpoint)) {
                    itrResponse = advancedITRDataStore.saveAdvancedITRData(userRequest, bodyAsString, masRefId, ipAddress, createdBy);
                }

                if (itrResponse != null) {
                    long endTime = System.currentTimeMillis(); // Capture end time
                    long durationInMillis = endTime - startTime; // Calculate duration in milliseconds
                    log.info("Total time taken: {} milliseconds", durationInMillis);
                    return ResponseEntity.ok(itrResponse);
                }
            } else {
                log.debug("Response Body: {}", bodyAsString);
                throw new MasThirdPartyApiException(HttpStatus.valueOf(response.code()), String.format("Error while getting api response: %s", responseMessage), ProgressStatus.API_CALL_FAILED);
            }
        } catch (MasThirdPartyApiException e) {
            errorLoggingService.logItrError(e.getMessage(), rawResponse, itrRequest);
            throw e;
        } catch (Exception e) {
            log.error("Error executing HTTP request and saving data. ", e);
            errorLoggingService.logItrError(e.getMessage(), rawResponse, itrRequest);
            throw new MasThirdPartyApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ProgressStatus.PARSING_ERROR);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public ResponseEntity<ItrResponse> getItrSalaried(ItrRequest request, String ipAddress, String createdBy) throws MasThirdPartyApiException {
        log.debug("Requesting Salaried ITR");
        String karzaKey = getKarzaKey();
        String apiVersion = getApiVersion();
        Integer numberOfYears = request.getNumberOfYears() != null ? request.getNumberOfYears() : KarzaITRConstants.NO_OF_YEARS;
        UserRequest userRequest = createRequest(request, apiVersion, numberOfYears, null, ipAddress, createdBy);
        KarzaItrSalariedRequest salariedItrRequest = modelMapper.map(request, KarzaItrSalariedRequest.class);
        salariedItrRequest.setApiVersion(apiVersion);
        salariedItrRequest.setNumberOfYears(numberOfYears);
        return getItrData(request, salariedItrRequest, userRequest, karzaKey, KarzaITRConstants.ITR_SALARIED_ENDPOINT, request.getMasRefId(), ipAddress, createdBy);
    }

    public ResponseEntity<ItrResponse> getItrBusiness(ItrRequest request, String ipAddress, String createdBy) throws MasThirdPartyApiException {
        log.debug("Requesting Business ITR");
        String karzaKey = getKarzaKey();
        String apiVersion = getApiVersion();
        Integer numberOfYears = request.getNumberOfYears() != null ? request.getNumberOfYears() : KarzaITRConstants.NO_OF_YEARS;
        UserRequest userRequest = createRequest(request, apiVersion, numberOfYears, true, ipAddress, createdBy);
        KarzaItrBusinessRequest businessItrRequest = modelMapper.map(request, KarzaItrBusinessRequest.class);
        businessItrRequest.setAdditionalData(true);
        businessItrRequest.setApiVersion(apiVersion);
        businessItrRequest.setNumberOfYears(numberOfYears);
        return getItrData(request, businessItrRequest, userRequest, karzaKey, KarzaITRConstants.ITR_BUSINESS_ENDPOINT, request.getMasRefId(), ipAddress, createdBy);
    }

    public ResponseEntity<ItrResponse> getItrAdvanced(ItrRequest request, String ipAddress, String createdBy) throws MasThirdPartyApiException {
        log.debug("Requesting Advanced ITR");
        Integer numberOfYears = request.getNumberOfYears() != null ? request.getNumberOfYears() : 4;
        UserRequest userRequest = createRequest(request, null, numberOfYears, null, ipAddress, createdBy);
        KarzaItrAdvancedRequest itrAdvancedRequest = modelMapper.map(request, KarzaItrAdvancedRequest.class);
        itrAdvancedRequest.setNumberOfYears(numberOfYears);
        String karzaKey = getKarzaKey();
        return getItrData(request, itrAdvancedRequest, userRequest, karzaKey, KarzaITRConstants.ITR_ADVANCED_ENDPOINT, request.getMasRefId(), ipAddress, createdBy);
    }

    public UserRequest createRequest(ItrRequest itrRequest, String apiVersion, Integer numberOfYears, Boolean additionalData, String ipAddress, String createdBy) {
        return UserRequest.builder()
                .masRefId(itrRequest.getMasRefId())
                .username(itrRequest.getUsername())
                .password(karzaITREncryptionService.encrypt(itrRequest.getPassword()))
                .numberOfYears(numberOfYears)
                .apiVersion(apiVersion)
                .consent(itrRequest.getConsent())
                .requestType(itrRequest.getRequestType())
                .ipAddress(ipAddress)
                .createdBy(createdBy)
                .additionalData(additionalData)
                .build();
    }

    private String getApiVersion() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.KARZA_ITR);
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getApiVersion())) {
            log.warn("No api version was found for type {}, using default values configured in application.yml", CredentialType.KARZA_ITR.getName());
            return environmentApiVersion;
        } else {
            return apiInfoCredentials.getApiVersion();
        }
    }

    private String getKarzaKey() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.KARZA_ITR);
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getApiKey())) {
            log.warn("No karza key was found for type {}, using default values configured in application.yml", CredentialType.KARZA_ITR.getName());
            return environmentKarzaKey;
        } else {
            return apiInfoCredentials.getApiKey();
        }
    }

    private String getBaseUrl() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.KARZA_ITR);
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getBaseUrl())) {
            log.warn("No base url was found for type {}, using default values configured in application.yml", CredentialType.KARZA_ITR.getName());
            return environmentBaseUrl;
        } else {
            return apiInfoCredentials.getBaseUrl();
        }
    }
}

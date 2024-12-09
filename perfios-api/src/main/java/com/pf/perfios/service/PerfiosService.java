package com.pf.perfios.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.model.entity.APIInfoCredentials;
import com.pf.common.model.entity.CredentialType;
import com.pf.common.repository.APIInfoCredentialsRepository;
import com.pf.common.utils.EnvironmentUtils;
import com.pf.perfios.constant.PerfiosUrlConstants;
import com.pf.perfios.exception.MasBadRequestException;
import com.pf.perfios.exception.MasEntityNotFoundException;
import com.pf.perfios.exception.MasPerfiosException;
import com.pf.perfios.model.dto.CreateReportDTO;
import com.pf.perfios.model.dto.CreateReportResDTO;
import com.pf.perfios.model.dto.InitiateTransactionRequestBodyDTO;
import com.pf.perfios.model.dto.InstitutionResponseDTO;
import com.pf.perfios.model.dto.PerfiosCallBackStatus;
import com.pf.perfios.model.dto.ProcessStatementReqDTO;
import com.pf.perfios.model.dto.ReprocessTransactionReqDTO;
import com.pf.perfios.model.dto.TransactionCallBackDTO;
import com.pf.perfios.model.entity.MasReqStatus;
import com.pf.perfios.model.entity.MasRequests;
import com.pf.perfios.model.entity.MasWebhookStatus;
import com.pf.perfios.model.entity.TransactionCallBackTemp;
import com.pf.perfios.repository.MasRequestsRepository;
import com.pf.perfios.repository.TransactionCallBackTempRepository;
import com.pf.perfios.utils.ApiClient;
import com.pf.perfios.utils.MASConst;
import com.pf.perfios.utils.PerfiosHeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class PerfiosService {
    private static final String BASE_URL = "perfios.base-url";
    private static final String WEBHOOK_URL = "perfios.webhook-url";
    private static final String TRANSACTION_ID = "{transactionId}";
    private static final String[] LOCAL_VAR_ACCEPTS = {MediaType.APPLICATION_JSON_VALUE};
    private static final String[] LOCAL_VAR_CONTENT_TYPES = {MediaType.APPLICATION_JSON_VALUE};
    private final SaveReportService saveReportService;
    private final ApiClient apiClient;
    private final TransactionCallBackTempRepository transactionCallBackTempRepository;
    private final MasRequestsRepository masRequestsRepository;
    private final APIInfoCredentialsRepository apiInfoCredentialsRepository;
    private final AsyncTaskExecutor taskExecutor;
    private final ObjectMapper objectMapper;
    private final String environmentBaseUrl;
    private final String environmentTransactionCompleteCallbackUrl;

    public PerfiosService(
            SaveReportService saveReportService,
            ApiClient apiClient,
            TransactionCallBackTempRepository transactionCallBackTempRepository,
            MasRequestsRepository masRequestsRepository,
            APIInfoCredentialsRepository apiInfoCredentialsRepository,
            AsyncTaskExecutor taskExecutor,
            ObjectMapper objectMapper,
            Environment environment) {
        this.saveReportService = saveReportService;
        this.apiClient = apiClient;
        this.transactionCallBackTempRepository = transactionCallBackTempRepository;
        this.masRequestsRepository = masRequestsRepository;
        this.apiInfoCredentialsRepository = apiInfoCredentialsRepository;
        this.taskExecutor = taskExecutor;
        this.objectMapper = objectMapper;
        environmentBaseUrl = EnvironmentUtils.readEnvironmentVariable(environment, BASE_URL);
        environmentTransactionCompleteCallbackUrl = EnvironmentUtils.readEnvironmentVariable(environment, WEBHOOK_URL);
    }

    public ResponseEntity<String> initiateTransaction(InitiateTransactionRequestBodyDTO body) throws RestClientException {
        log.debug("Called initateTransaction");
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling initiateTransaction");
        }

        String transactionCompleteCallbackUrl = getTransactionCompleteCallbackUrl();
        body.setTransactionCompleteCallbackUrl(transactionCompleteCallbackUrl);

        String jsonConvertedBody = PerfiosHeaderUtil.convertObjectToJSON(body);

        String baseUrl = getBaseUrl();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        PerfiosHeaderUtil.updateHeaders(PerfiosUrlConstants.INITIATE_TRANSACTION_URL, localVarHeaderParams, jsonConvertedBody, HttpMethod.POST, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(
                baseUrl, PerfiosUrlConstants.INITIATE_TRANSACTION_URL, HttpMethod.POST,
                new HashMap<>(), new LinkedMultiValueMap<>(),
                jsonConvertedBody, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );
    }

    public ResponseEntity<String> cancelTransaction(String transactionId) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        String url = PerfiosUrlConstants.CANCEL_TRANSACTION_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.POST, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.POST, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );

    }

    public ResponseEntity<String> deleteTransaction(String transactionId) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        String url = PerfiosUrlConstants.DELETE_TRANSACTION_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.DELETE, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };
        return apiClient.invokeAPI(baseUrl, url, HttpMethod.DELETE, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType);

    }

    public ResponseEntity<String> uploadFile(String transactionId, MultipartFile file) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        String url = PerfiosUrlConstants.FILE_UPLOAD_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.POST, baseUrl);

        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<>();
        localVarFormParams.add("file", file.getResource());

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(new String[]{"multipart/form-data"});

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };
        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.POST, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, localVarFormParams,
                localVarAccept, localVarContentType, localReturnType
        );
    }

    public ResponseEntity<String> processStatement(String transactionId, ProcessStatementReqDTO body, boolean isReProcess) {
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling processStatement");
        }

        String jsonConvertedBody = PerfiosHeaderUtil.convertObjectToJSON(body);

        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        String url = isReProcess ?
                PerfiosUrlConstants.RE_PROCESS_STATEMENT_URL.replace(TRANSACTION_ID, transactionId).replace("{fileId}", body.getFileId()) :
                PerfiosUrlConstants.PROCESS_STATEMENT_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, jsonConvertedBody, HttpMethod.POST, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(baseUrl, url, HttpMethod.POST, new HashMap<>(),
                new LinkedMultiValueMap<>(), jsonConvertedBody, localVarHeaderParams,
                new LinkedMultiValueMap<>(), localVarAccept, localVarContentType, localReturnType
        );
    }

    public ResponseEntity<String> generateReport(String transactionId) {
        log.debug("Calling generate report");
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        String url = PerfiosUrlConstants.GENERATE_REPORT_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.POST, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };
        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.POST, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(), localVarAccept,
                localVarContentType, localReturnType
        );
    }

    public ResponseEntity<byte[]> getReport(String transactionId, String type) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        String url = PerfiosUrlConstants.RETRIEVE_REPORT_URL.replace(TRANSACTION_ID, transactionId).replace("{types}", type);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.GET, baseUrl);
        ParameterizedTypeReference<byte[]> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.GET, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(), null,
                null, localReturnType);
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttemptsExpression = "${perfios.getReportRetry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${perfios.getReportRetry.delayInMilli}")
    )
    public ResponseEntity<byte[]> getReportWithRetry(String transactionId, String type) {
        return getReport(transactionId, type);
    }

    public ResponseEntity<String> saveJsonReport(MasRequests masRequests) {
        ResponseEntity<byte[]> response = getReportWithRetry(masRequests.getPerfiosTransactionId(), "json");

        JSONObject jsonData = new JSONObject(new String(Objects.requireNonNull(response.getBody()),
                StandardCharsets.UTF_8));
        try {
            saveReportService.saveJSON(jsonData, masRequests);

            masRequests.setStatus(MasReqStatus.REPORT_COMPLETED);
            masRequests.setReportFetched(true);
            masRequestsRepository.save(masRequests);

            return ResponseEntity.ok("Saved SuccessFully");
        } catch (Exception e) {
            saveReportService.saveFailedDataSaveIntoDb(jsonData, masRequests.getPerfiosTransactionId(), masRequests.getMasFinancialId(), e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<String> reProcessTransaction(ReprocessTransactionReqDTO body) {
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling reProcessTransaction");
        }

        String jsonConvertedBody = PerfiosHeaderUtil.convertObjectToJSON(body);
        String baseUrl = getBaseUrl();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        PerfiosHeaderUtil.updateHeaders(PerfiosUrlConstants.REPROCESS_TRANSACTION_URL, localVarHeaderParams, jsonConvertedBody, HttpMethod.POST, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };
        return apiClient.invokeAPI(
                baseUrl, PerfiosUrlConstants.REPROCESS_TRANSACTION_URL, HttpMethod.POST, new HashMap<>(),
                new LinkedMultiValueMap<>(), jsonConvertedBody, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );
    }

    public void transactionComplete(TransactionCallBackDTO transactionCallBackDTO) {
        log.info("Transaction Callback received :" + transactionCallBackDTO.getPerfiosTransactionId());

        MasRequests masRequests = masRequestsRepository.findByPerfiosTransactionId(transactionCallBackDTO.getPerfiosTransactionId())
                .orElseThrow(
                        () -> new MasPerfiosException("Unable to find the perfios transaction in Mas with id " + transactionCallBackDTO.getPerfiosTransactionId()));

        masRequests.setPerfiosStatus(transactionCallBackDTO.getStatus());
        masRequests.setPerfiosErrorCode(transactionCallBackDTO.getErrorCode());
        masRequests.setPerfiosErrorMessage(transactionCallBackDTO.getErrorMessage());
        masRequestsRepository.save(masRequests);

        transactionCallBackDTO.setMasFinancialId(masRequests.getMasFinancialId());

        taskExecutor.execute(() -> {
                    try {
                        transactionCallBackTempRepository.save(TransactionCallBackTemp.builder().jsonData(new ObjectMapper().writeValueAsString(transactionCallBackDTO)).build());
                    } catch (JsonProcessingException jpe) {
                        //ignore
                    }
                }
        );

        //call webhook of MAS Financials
        taskExecutor.execute(() -> callMasFinancialWebHook(masRequests, transactionCallBackDTO));

        //save report in the background
        taskExecutor.execute(() -> {
            //if report status is completed then only save it
            if (PerfiosCallBackStatus.COMPLETED.getValue().equalsIgnoreCase(transactionCallBackDTO.getStatus())) {
                getAndSaveJSONReport(transactionCallBackDTO, masRequests);
                log.info("Report with perfiosTransactionId : {} saved successfully", transactionCallBackDTO.getPerfiosTransactionId());
            } else {
                masRequests.setStatus(MasReqStatus.ERROR_FROM_PERFIOS);
                masRequestsRepository.save(masRequests);
                log.info("call back from perfios with errorCode: {} & message: {}", transactionCallBackDTO.getErrorCode(), transactionCallBackDTO.getErrorMessage());
            }
        });
    }

    private void callMasFinancialWebHook(MasRequests masRequests, TransactionCallBackDTO transactionCallBackDTO) {
        try {
            ResponseEntity<String> masResponse = apiClient.callMASWebHookWithData(masRequests.getTransactionCompleteCallbackUrl(), transactionCallBackDTO);

            if (masResponse.getStatusCode().is2xxSuccessful()) {
                masRequests.setMasWebhookStatus(MasWebhookStatus.CALLBACK_SUCCESS);
                masRequestsRepository.save(masRequests);
                log.info("MAS-webhook-call success for perfiosTransactionId : {} ", transactionCallBackDTO.getPerfiosTransactionId());
            } else {
                masRequests.setMasWebhookStatus(MasWebhookStatus.CALLBACK_FAILED);
                masRequests.setMasWebhookFailedError(masResponse.getBody());
                masRequestsRepository.save(masRequests);
                throw new MasPerfiosException("Mas Financial Webhook call failed with response status " + masResponse.getStatusCode().value() + " & body: " + masResponse.getBody());
            }
        } catch (RestClientException e) {
            masRequests.setMasWebhookStatus(MasWebhookStatus.CALLBACK_FAILED);
            masRequests.setMasWebhookFailedError(e.getMessage());
            masRequestsRepository.save(masRequests);
            //handle restClientException
            throw new MasPerfiosException("Mas Financial Webhook call failed with error " + e.getMessage());
        }

    }

    public void notifyMas(MasRequests masRequests, TransactionCallBackDTO transactionCallBackDTO) {
        apiClient.callMASWebHookWithData(masRequests.getTransactionCompleteCallbackUrl(), transactionCallBackDTO);
    }

    private void getAndSaveJSONReport(TransactionCallBackDTO transactionCallBackDTO, MasRequests masRequests) {
        try {
            ResponseEntity<String> response = saveJsonReport(masRequests);
            if (response.getStatusCode().isError()) {
                log.error("Error while saving JSON Report with perfiosTransactionId: {}", transactionCallBackDTO.getPerfiosTransactionId());
            }
        } catch (RestClientException restClientException) {
            log.error("Error while saving JSON Report with perfiosTransactionId: {} with message {}", transactionCallBackDTO.getPerfiosTransactionId(), restClientException.getMessage());
        }

    }

    public ResponseEntity<String> transactionStatus(String transactionId) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        String url = PerfiosUrlConstants.TRANSACTION_STATUS_URL.replace(TRANSACTION_ID, transactionId);
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.GET, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.GET, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );
    }

    public ResponseEntity<String> bulkTransactionStatus(Date startTime, Date endTime) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String url = PerfiosUrlConstants.BULK_TRANSACTION_STATUS_URL.replace("{startTime}", sdf.format(startTime)).replace("{endTime}", sdf.format(endTime));
        String baseUrl = getBaseUrl();
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.GET, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        return apiClient.invokeAPI(
                baseUrl, url, HttpMethod.GET, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );
    }

    public InstitutionResponseDTO getInstitutions(String processingType, String dataSource) {
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        String baseUrl = getBaseUrl();
        String url = PerfiosUrlConstants.LIST_INSTITUTIONS_URL.replace("{processingType}", processingType).replace("{dataSource}", dataSource);
        PerfiosHeaderUtil.updateHeaders(url, localVarHeaderParams, StringUtils.EMPTY, HttpMethod.GET, baseUrl);

        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(LOCAL_VAR_ACCEPTS);
        final MediaType localVarContentType = apiClient.selectHeaderContentType(LOCAL_VAR_CONTENT_TYPES);

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<String> response = apiClient.invokeAPI(
                baseUrl, url, HttpMethod.GET, new HashMap<>(), new LinkedMultiValueMap<>(),
                StringUtils.EMPTY, localVarHeaderParams, new LinkedMultiValueMap<>(),
                localVarAccept, localVarContentType, localReturnType
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                return objectMapper.readValue(response.getBody(), InstitutionResponseDTO.class);
            } catch (JsonProcessingException e) {
                log.error("Error while converting json to DTO, {}", e.getMessage());
                throw new MasPerfiosException("Unable to parse response");
            }
        } else {
            throw new MasPerfiosException(response.getBody());
        }
    }

    public CreateReportResDTO createMASReport(CreateReportDTO request, String createdBy, String ipAddress) throws MasBadRequestException {
        log.debug("Creating new report with id {}", request.getMasFinancialId());
        //validate institution id
        if (Boolean.TRUE.equals(request.getUploadingScannedStatements()) && StringUtils.isEmpty(request.getInstitutionId())) {
            throw new MasBadRequestException("field 'institutionId' is missing");
        }

        Map<String, String> filePasswords = new HashMap<>();

        if (Strings.isNotEmpty(request.getPasswords())) {
            JSONArray jsonArray = new JSONArray(request.getPasswords());
            for (int i = 0; i < jsonArray.length(); i++) {
                String fileName = jsonArray.getJSONObject(i).getString("fileName");
                String password = jsonArray.getJSONObject(i).getString("password");
                filePasswords.put(fileName, password);
            }
        }

        String customerTransactionId = UUID.randomUUID().toString();
        log.debug("Created new customer transaction ID {}", customerTransactionId);
        request.setCustomerTransactionId(customerTransactionId);

        MasRequests masRequests =
                MasRequests.builder()
                        .masFinancialId(request.getMasFinancialId())
                        .customerTransactionId(request.getCustomerTransactionId())
                        .transactionCompleteCallbackUrl(request.getTransactionCompleteCallbackUrl())
                        .createdBy(createdBy)
                        .ipAddress(ipAddress)
                        .reportFetched(false)
                        .reportExpired(false)
                        .masWebhookStatus(MasWebhookStatus.PENDING)
                        .creditLimit(request.getCreditLimit())
                        .uniqueFirmId(request.getUniqueFirmId())
                        .build();

        masRequestsRepository.save(masRequests);

        //initiate a transaction
        InitiateTransactionRequestBodyDTO initiateReq = InitiateTransactionRequestBodyDTO.from(request);
        try {
            ResponseEntity<String> initiateRes = initiateTransaction(initiateReq);
            if (initiateRes.getStatusCode().is2xxSuccessful()) {
                JSONObject data = new JSONObject(initiateRes.getBody());
                JSONObject transaction = data.getJSONObject("transaction");
                String perfiosTransactionId = transaction.getString("perfiosTransactionId");
                log.debug("Transaction initiated");

                masRequests.setPerfiosTransactionId(perfiosTransactionId);
                masRequests.setStatus(MasReqStatus.TRANSACTION_INITIATED);
                masRequestsRepository.save(masRequests);

                //upload and process all statements
                uploadAndProcessStatements(request, perfiosTransactionId, masRequests, filePasswords);

                //generate report
                ResponseEntity<String> generateReportRes = generateReport(perfiosTransactionId);
                log.debug("Report generated");
                if (!generateReportRes.getStatusCode().is2xxSuccessful()) {
                    masRequests.setStatus(MasReqStatus.FAILED_WHILE_GENERATING_REPORT);
                    masRequests.setErrorMessage(generateReportRes.getBody());
                    masRequestsRepository.save(masRequests);
                    throw new MasPerfiosException("Error occurred while generating report" + generateReportRes.getBody());
                }
            } else {
                masRequests.setStatus(MasReqStatus.FAILED_WHILE_INITIATING_TRANSACTION);
                masRequests.setErrorMessage(initiateRes.getBody());
                masRequestsRepository.save(masRequests);
                throw new MasPerfiosException("Error occurred while initiating transaction" + initiateRes.getBody());
            }
        } catch (RestClientException e) {
            masRequests.setStatus(MasReqStatus.API_FAILURE);
            masRequests.setErrorMessage(e.getMessage());
            masRequestsRepository.save(masRequests);
            throw e;
        }

        return CreateReportResDTO
                .builder()
                .perfiosTransactionId(masRequests.getPerfiosTransactionId())
                .customerTransactionId(masRequests.getCustomerTransactionId())
                .masFinancialId(masRequests.getMasFinancialId())
                .build();
    }

    private void uploadAndProcessStatements(CreateReportDTO request, String perfiosTransactionId, MasRequests masRequests, Map<String, String> filePasswords) {
        log.debug("Uploading {} reports to process statement", request.getFiles().length);
        List<Future<Void>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < request.getFiles().length; i++) {
                MultipartFile file = request.getFiles()[i];
                futures.add(taskExecutor.submit(() -> {
                            uploadAndProcessStatement(request, perfiosTransactionId, masRequests, filePasswords, file);
                            return null;
                        }
                ));
            }

            for (Future<Void> future : futures) {
                future.get();
            }
            log.debug("Uploaded {} reports successfully", request.getFiles().length);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while uploading and processing statements: {}", e.getMessage());
            try {
                Thread.currentThread().interrupt();
                for (Future<Void> future : futures) {
                    future.cancel(true);
                }
            } catch (Exception ex) {
                log.error("Unexpected error while cancelling futures", ex);
            }
            masRequests.setStatus(MasReqStatus.FAILED_WHILE_UPLOADING_FILES);
            if (e.getCause() instanceof RestClientException) {
                masRequests.setErrorMessage(e.getCause().getMessage());
                masRequestsRepository.save(masRequests);
                throw (RestClientException) e.getCause();
            } else {
                masRequests.setErrorMessage(e.getMessage());
                masRequestsRepository.save(masRequests);
                throw new MasPerfiosException(e.getMessage());
            }

        }
    }

    private void uploadAndProcessStatement(CreateReportDTO request, String perfiosTransactionId, MasRequests masRequests, Map<String, String> filePasswords, MultipartFile file) {
        ResponseEntity<String> response = uploadFile(perfiosTransactionId, file);
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject data = new JSONObject(response.getBody());
            String fileId = data.getJSONObject("file").getString("fileId");
            ProcessStatementReqDTO processStatementReqDTO = new ProcessStatementReqDTO();
            processStatementReqDTO.setFileId(fileId);
            if (filePasswords.containsKey(file.getOriginalFilename())) {
                processStatementReqDTO.setPassword(filePasswords.get(file.getOriginalFilename()));
            }
            if (Boolean.TRUE.equals(request.getUploadingScannedStatements())) {
                processStatementReqDTO.setInstitutionId(request.getInstitutionId());
            }
            ResponseEntity<String> processStatementRes = processStatement(perfiosTransactionId, processStatementReqDTO, false);
            if (!processStatementRes.getStatusCode().is2xxSuccessful()) {
                masRequests.setStatus(MasReqStatus.FAILED_WHILE_PROCESSING_STATEMENTS);
                masRequests.setErrorMessage(processStatementRes.getBody());
                masRequestsRepository.save(masRequests);
                throw new MasPerfiosException("Error occurred while processing statement" + processStatementRes.getBody());
            }
        } else {
            masRequests.setStatus(MasReqStatus.FAILED_WHILE_UPLOADING_FILES);
            masRequests.setErrorMessage(response.getBody());
            masRequestsRepository.save(masRequests);
            throw new MasPerfiosException("Error occurred while uploading statement" + response.getBody());
        }
    }

    public ResponseEntity<byte[]> retrieveReport(CreateReportResDTO createReportResDTO, String type) throws MasEntityNotFoundException, MasBadRequestException {
        if (!MASConst.REPORT_TYPE_JSON.equals(type) && !MASConst.REPORT_TYPE_XLSX.equals(type)) {
            throw new MasBadRequestException("Only '" + MASConst.REPORT_TYPE_JSON + "' and '" + MASConst.REPORT_TYPE_XLSX + "' type values are supported");
        }

        MasRequests masRequests = masRequestsRepository.findByMasFinancialIdAndCustomerTransactionId(createReportResDTO.getMasFinancialId(), createReportResDTO.getCustomerTransactionId())
                .orElseThrow(() -> new MasEntityNotFoundException("Unable to find the request with masFinancialId and customerTransactionId"));

        ResponseEntity<byte[]> response = getReport(masRequests.getPerfiosTransactionId(), type);
        if (response.getStatusCode().is2xxSuccessful()) {
            masRequests.setReportFetched(true);
            masRequestsRepository.save(masRequests);

            if (masRequests.getCustomerInfo() == null && !MasReqStatus.REPORT_COMPLETED.equals(masRequests.getStatus())) {
                //giving another try to save report if it's not already saved
                taskExecutor.execute(() -> {
                    log.info("Giving another try to save it when report is fetched for perfiosTransactionId {}", masRequests.getPerfiosTransactionId());
                    saveJsonReport(masRequests);
                });
            }
        }

        return response;
    }

    private String getBaseUrl() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.PERFIOS);
        if (apiInfoCredentials == null || org.apache.commons.lang3.StringUtils.isBlank(apiInfoCredentials.getBaseUrl())) {
            log.warn("No base url was found for type {}, using default values configured in application.yml", CredentialType.PERFIOS.getName());
            return environmentBaseUrl;
        } else {
            return apiInfoCredentials.getBaseUrl();
        }
    }

    private String getTransactionCompleteCallbackUrl() {
        APIInfoCredentials apiInfoCredentials = apiInfoCredentialsRepository.findByCredentialType(CredentialType.PERFIOS);
        String transactionCompleteCallbackUrl;
        if (apiInfoCredentials == null || StringUtils.isBlank(apiInfoCredentials.getWebhookUrl())) {
            log.warn("No base api was found for type {}, using default values configured in application.yml", CredentialType.PERFIOS.getName());
            transactionCompleteCallbackUrl = environmentTransactionCompleteCallbackUrl;
        } else {
            transactionCompleteCallbackUrl = apiInfoCredentials.getWebhookUrl();
        }
        if (StringUtils.isBlank(transactionCompleteCallbackUrl)) {
            throw new RestClientException("Transaction Complete Callback is required to be set either in Database or in Environment Variable");
        } else {
            return transactionCompleteCallbackUrl;
        }
    }
}

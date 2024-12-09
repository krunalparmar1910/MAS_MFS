package com.pf.perfios.utils;

import com.pf.perfios.model.dto.TransactionCallBackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ApiClient {
    private final HttpHeaders defaultHeaders = new HttpHeaders();
    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("java:S107")
    public <T> ResponseEntity<T> invokeAPI(
            String basePath,
            String path,
            HttpMethod method,
            Map<String, Object> pathParams,
            MultiValueMap<String, String> queryParams,
            Object body,
            HttpHeaders headerParams,
            MultiValueMap<String, Object> formParams,
            List<MediaType> accept,
            MediaType contentType,
            ParameterizedTypeReference<T> returnType
    ) throws RestClientException {
        Map<String, Object> uriParams = new HashMap<>(pathParams);

        String finalUri = path;

        if (queryParams != null && !queryParams.isEmpty()) {
            String queryUri = generateQueryUri(queryParams, uriParams);
            finalUri += "?" + queryUri;
        }
        String expandedPath = this.expandPath(finalUri, uriParams);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(basePath).path(expandedPath);

        try {
            new URI(builder.build().toUriString());
        } catch (URISyntaxException ex) {
            throw new RestClientException("Could not build URL: " + builder.toUriString(), ex);
        }

        final RequestEntity.BodyBuilder requestBuilder = RequestEntity.method(method, UriComponentsBuilder.fromHttpUrl(basePath).toUriString() + finalUri, uriParams);
        if (accept != null) {
            requestBuilder.accept(accept.toArray(new MediaType[0]));
        }
        if (contentType != null) {
            requestBuilder.contentType(contentType);
        }

        addHeadersToRequest(headerParams, requestBuilder);
        addHeadersToRequest(defaultHeaders, requestBuilder);


        RequestEntity<Object> requestEntity = requestBuilder.body(selectBody(body, formParams, contentType));

        ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, returnType);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        } else {
            // The error handler built into the RestTemplate should handle 400 and 500 series errors.
            throw new RestClientException("API returned " + responseEntity.getStatusCode() + " and it wasn't handled by the RestTemplate error handler");
        }
    }

    protected Object selectBody(Object obj, MultiValueMap<String, Object> formParams, MediaType contentType) {
        boolean isForm = MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType) || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType);
        return isForm ? formParams : obj;
    }

    public ApiClient addDefaultHeader(String name, String value) {
        defaultHeaders.remove(name);
        defaultHeaders.add(name, value);
        return this;
    }


    public String generateQueryUri(MultiValueMap<String, String> queryParams, Map<String, Object> uriParams) {
        StringBuilder queryBuilder = new StringBuilder();
        queryParams.forEach((name, values) -> {
            final String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            if (CollectionUtils.isEmpty(values)) {
                if (!queryBuilder.isEmpty()) {
                    queryBuilder.append('&');
                }
                queryBuilder.append(encodedName);
            } else {
                int valueItemCounter = 0;
                for (Object value : values) {
                    if (!queryBuilder.isEmpty()) {
                        queryBuilder.append('&');
                    }
                    queryBuilder.append(encodedName);
                    if (value != null) {
                        String templatizedKey = encodedName + valueItemCounter++;
                        uriParams.put(templatizedKey, value.toString());
                        queryBuilder.append('=').append("{").append(templatizedKey).append("}");
                    }
                }
            }
        });
        return queryBuilder.toString();

    }

    protected void addHeadersToRequest(HttpHeaders headers, RequestEntity.BodyBuilder requestBuilder) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = entry.getValue();
            for (String value : values) {
                if (value != null) {
                    requestBuilder.header(entry.getKey(), value);
                }
            }
        }
    }

    public String expandPath(String pathTemplate, Map<String, Object> variables) {
        return restTemplate.getUriTemplateHandler().expand(pathTemplate, variables).toString();
    }

    public List<MediaType> selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        return MediaType.parseMediaTypes(StringUtils.arrayToCommaDelimitedString(accepts));
    }

    public MediaType selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0) {
            return MediaType.APPLICATION_XML;
        }
        return MediaType.parseMediaType(contentTypes[0]);
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttemptsExpression = "${perfios.webhookRetry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${perfios.webhookRetry.delayInMilli}")
    )
    public ResponseEntity<String> callMASWebHookWithData(String transactionCompleteCallbackUrl, TransactionCallBackDTO transactionCallBackDTO) {

        log.info("calling MAS webhook with perfiosTransactionId : {}, with status {}", transactionCallBackDTO.getPerfiosTransactionId(), transactionCallBackDTO.getStatus());
        return restTemplate.postForEntity(transactionCompleteCallbackUrl, transactionCallBackDTO, String.class);
    }
}

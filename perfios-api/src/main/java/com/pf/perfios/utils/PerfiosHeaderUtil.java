package com.pf.perfios.utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.pf.common.exception.MasRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;
import java.util.TreeSet;

@Component
@Slf4j
public class PerfiosHeaderUtil {
    private PerfiosHeaderUtil() {
        // utils class
    }

    public static void updateHeaders(String url, HttpHeaders localVarHeaderParams, String body, HttpMethod method, String baseUrl) {
        String formattedDate = getFormattedDate();
        String signature = getSignature(url, body, formattedDate, method, baseUrl);

        localVarHeaderParams.add("X-Perfios-Algorithm", "PERFIOS-RSA-SHA256");
        localVarHeaderParams.add("X-Perfios-Content-Sha256", EncodeDecodeHelper.getSHA256Hex(body));
        localVarHeaderParams.add("X-Perfios-Date", formattedDate);
        localVarHeaderParams.add("X-Perfios-Signature", signature);
        localVarHeaderParams.add("X-Perfios-Signed-Headers", "host;x-perfios-content-sha256;x-perfios-date");
        localVarHeaderParams.add("Host", PerfiosHeaderUtil.getHost(baseUrl));
    }

    private static String getSignature(String url, String payload, String xPerfiosDate, HttpMethod httpMethod, String baseUrl) {

        StringBuilder uriEncodedQuery = new StringBuilder();
        if (url.contains("?")) {
            MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(url).build().getQueryParams();
            SortedSet<String> keySet = new TreeSet<>(queryParams.keySet());
            for (String key : keySet) {
                uriEncodedQuery.append(EncodeDecodeHelper.uriEncode(key)).append("=");
                if (!queryParams.get(key).isEmpty() && queryParams.get(key).get(0) != null) {
                    uriEncodedQuery.append(EncodeDecodeHelper.uriEncode(queryParams.get(key).stream().reduce((x, y) -> x + "," + y).orElse("")));
                }
                uriEncodedQuery.append("&");
            }
            uriEncodedQuery.deleteCharAt(uriEncodedQuery.lastIndexOf("&"));

            url = url.substring(0, url.lastIndexOf('?'));
        }


        String sha256Payload = EncodeDecodeHelper.getSHA256Hex(payload);

        String canonicalRequest = httpMethod + "\n"
                + EncodeDecodeHelper.uriEncode(url) + "\n"
                + uriEncodedQuery + "\n"
                + "host:" + getHost(baseUrl) + "\n"
                + "x-perfios-content-sha256:" + sha256Payload + "\n"
                + "x-perfios-date:" + xPerfiosDate + "\n"
                + "host;x-perfios-content-sha256;x-perfios-date" + "\n"
                + sha256Payload;

        String stringToSign = "PERFIOS-RSA-SHA256" + "\n"
                + xPerfiosDate + "\n"
                + EncodeDecodeHelper.getSHA256Hex(canonicalRequest);

        String checksum = EncodeDecodeHelper.getSHA256Hex(stringToSign);
        return EncodeDecodeHelper.encryptWithRSA(checksum, EncodeDecodeHelper.loadPrivateKey("prod-keys-masFinancial/private_key"), EncodeDecodeHelper.loadPublicKey("prod-keys-masFinancial/public_key"));
    }

    private static String getFormattedDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        return currentDateTime.format(formatter);
    }

    public static <T> String convertObjectToJSON(T object) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new MasRuntimeException(HttpStatus.BAD_GATEWAY, String.format("Error while converting to json body. {%s}", e));
        }
    }

    private static String getHost(String baseUrl) {
        try {
            URL url = new URL(baseUrl);
            return url.getHost();
        } catch (MalformedURLException e) {
            throw new MasRuntimeException(HttpStatus.BAD_GATEWAY, String.format("Not able to extract host. {%s}", e));
        }
    }

    public static String getErrorMessage(String responseBodyAsString) {
        try {
            JSONObject response = new JSONObject(responseBodyAsString);

            JSONObject error = response.optJSONObject("error");

            if (error != null) {
                JSONArray details = error.optJSONArray("details");
                StringBuilder errorMessage = new StringBuilder();
                if (details != null) {
                    for (int i = 0; i < details.length(); i++) {
                        JSONObject errorData = details.getJSONObject(i);
                        String message = errorData.optString("message");
                        if (message != null) {
                            if (i != 0) {
                                errorMessage.append(", ");
                            }
                            errorMessage.append(message);
                        }
                    }
                    return errorMessage.toString();
                } else {
                    return responseBodyAsString;
                }
            }

        } catch (JSONException e) {
            //if any error occurs ignore the error and send the whole response
        }

        return responseBodyAsString;
    }
}

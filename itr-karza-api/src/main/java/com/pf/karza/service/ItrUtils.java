package com.pf.karza.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Slf4j
public final class ItrUtils {
    public static final String RESULT = "result";
    private static final String PDF_DOWNLOAD_LINK = "pdfDownloadLink";
    private static final String EXCEL_DOWNLOAD_LINK = "excelDownloadLink";

    private ItrUtils() {
        // utils
    }

    public static ItrResponse getItrResponse(Response response, String masRefId) {
        return ItrResponse.builder()
                .requestId(response.getRequestId())
                .statusCode(response.getStatusCode())
                .masRefId(masRefId)
                .result(Map.of(PDF_DOWNLOAD_LINK, response.getPdfDownloadLink(), EXCEL_DOWNLOAD_LINK, response.getExcelDownloadLink()))
                .build();
    }

    public static Integer parseIntegerValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.trace("Could not parse integer value {} due to {}", value, e.getLocalizedMessage());
            return null;
        }
    }

    public static Response extractResponse(JsonNode jsonNode, String masRefId) {
        String requestId = getValue(jsonNode.path("requestId"));
        String excelDownloadLink = getValue(jsonNode.path(RESULT).path("excelReportLink"));
        String pdfDownloadLink = getValue(jsonNode.path(RESULT).path("pdfDownloadLink"));
        Integer statusCode = ItrUtils.parseIntegerValue(getValue(jsonNode.path("statusCode")));

        if (requestId != null && masRefId != null) {
            return Response.builder()
                    .requestId(requestId)
                    .excelDownloadLink(excelDownloadLink != null ? excelDownloadLink : StringUtils.EMPTY)
                    .pdfDownloadLink(pdfDownloadLink != null ? pdfDownloadLink : StringUtils.EMPTY)
                    .masRefId(masRefId)
                    .statusCode(statusCode)
                    .build();
        } else {
            return null; // Indicate failure to extract request data
        }
    }

    public static String getValue(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        } else if (node.isTextual()) {
            return node.asText();
        } else if (node.isInt() || node.isLong() || node.isDouble() || node.isBoolean()) {
            return String.valueOf(node);
        } else {
            return null; // Unsupported type
        }
    }

    public static JsonNode flattenCptyArrays(JsonNode node) {
        if (node.isObject()) {
            ObjectNode newNode = (ObjectNode) node;
            if (newNode.has("ais")) {
                // Traverse through "data" array
                JsonNode dataArray = newNode.get("ais").get("data");
                if (dataArray.isArray()) {
                    for (JsonNode dataNode : dataArray) {
                        flattenTotalCpty(dataNode);
                    }
                }
            }

            return newNode;
        } else {
            return node;
        }
    }

    private static void flattenTotalCpty(JsonNode dataNode) {
        if (dataNode.has("total")) {
            JsonNode totalArray = dataNode.get("total");
            if (totalArray.isArray()) {
                for (JsonNode totalNode : totalArray) {
                    flattenCpty(totalNode);
                }
            }
        }
    }

    private static void flattenCpty(JsonNode totalNode) {
        if (totalNode.has("cpty")) {
            JsonNode cptyArray = totalNode.get("cpty");
            if (cptyArray.isArray()) {
                ArrayNode flattenedCpty = flattenArray(cptyArray);
                ((ObjectNode) totalNode).set("cpty", flattenedCpty);
            }
        }
    }

    private static ArrayNode flattenArray(JsonNode arrayNode) {
        ArrayNode result = new ArrayNode(null);
        for (JsonNode element : arrayNode) {
            if (element.isArray()) {
                for (JsonNode subElement : element) {
                    result.add(subElement);
                }
            } else {
                result.add(element);
            }
        }
        return result;
    }
}

package com.pf.mas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.mas.dto.ProfileDetailResponse;
import com.pf.mas.dto.ProfileDetailResponseData;
import com.pf.mas.dto.report.GST3BCustomersDetails;
import com.pf.mas.dto.report.GST3BSales;
import com.pf.mas.dto.report.GST3BSalesDetail;
import com.pf.mas.dto.report.GST3BSalesReport;
import com.pf.mas.dto.report.GST3BSuppliersDetails;
import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.UpdateGST3BReportManualEntryRequest;
import com.pf.mas.dto.user.GetGST3BReportRequestHistoryResponseDTO;
import com.pf.mas.dto.user.GetGST3BReportRequestResponseInfo;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.GSTReportManualBankingEntry;
import com.pf.mas.model.entity.GSTReportManualEntry;
import com.pf.mas.model.entity.user.UserInfo;
import com.pf.mas.repository.ClientOrderRepository;
import com.pf.mas.repository.GSTReportManualEntryRepository;
import com.pf.mas.repository.user.UserInfoRepository;
import com.pf.mas.service.report.SheetTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ReportControllerIntegrationTest extends IntegrationTestExtension {
    // constants
    private static final String BASE_REPORT_PATH = "src/test/resources/full-report/";
    private static final String SINGLE_REPORT_GST_NUMBER = "23AAUFR9715R1Z4";
    // autowired
    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private GSTReportManualEntryRepository gstReportManualEntryRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    private static Stream<Arguments> provideDetailsForParseExcelReport() throws IOException {
        return Stream.of(
                Arguments.of(ReportControllerTestData.builder()
                        .fileContent(SheetTestUtils.getFileData(SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(0))))
                        .expectedProfileCount(1)
                        .expectedPanNumber("AAUFR9715R")
                        .expectedGstNumbers(List.of(SINGLE_REPORT_GST_NUMBER))
                        .expectedCustomersRecords(11)
                        .expectedSuppliersRecords(4)
                        .expectedSalesRecords(12)
                        .build()),
                Arguments.of(ReportControllerTestData.builder()
                        .fileContent(SheetTestUtils.getFileData(SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(1))))
                        .expectedProfileCount(1)
                        .expectedPanNumber("APIPV8676C")
                        .expectedGstNumbers(List.of("33APIPV8676C1ZG"))
                        .expectedCustomersRecords(11)
                        .expectedSuppliersRecords(11)
                        .expectedSalesRecords(12)
                        .build()),
                Arguments.of(ReportControllerTestData.builder()
                        .fileContent(SheetTestUtils.getFileData(SheetTestUtils.getFilePath(SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(0))))
                        .expectedProfileCount(2)
                        .expectedPanNumber("AAUFR9715R")
                        .expectedGstNumbers(List.of(SINGLE_REPORT_GST_NUMBER, "24AAUFR9715R1Z2"))
                        .expectedCustomersRecords(11)
                        .expectedSuppliersRecords(11)
                        .expectedSalesRecords(12)
                        .build()),
                Arguments.of(ReportControllerTestData.builder()
                        .fileContent(SheetTestUtils.getFileData(SheetTestUtils.getFilePath(SheetTestUtils.CONSOLIDATED_REPORT_FILES.get(1))))
                        .expectedProfileCount(3)
                        .expectedPanNumber("ACRFS8434P")
                        .expectedGstNumbers(List.of("24ACRFS8434P1Z6", "27ACRFS8434P3ZY", "33ACRFS8434P1Z7"))
                        .expectedCustomersRecords(11)
                        .expectedSuppliersRecords(11)
                        .expectedSalesRecords(12)
                        .build())
        );
    }

    private static Stream<Arguments> provideDetailsForSingleParseExcelReport() throws IOException {
        return Stream.of(
                Arguments.of(ReportControllerTestData.builder()
                        .fileContent(SheetTestUtils.getFileData(SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(0))))
                        .expectedProfileCount(1)
                        .expectedPanNumber("AAUFR9715R")
                        .expectedGstNumbers(List.of(SINGLE_REPORT_GST_NUMBER))
                        .expectedCustomersRecords(11)
                        .expectedSuppliersRecords(4)
                        .expectedSalesRecords(12)
                        .build())
        );
    }

    private static Stream<Arguments> provideDetailsForParseFullExcelReport() throws IOException {
        return Stream.of(
                Arguments.of(ReportControllerTestData.builder()
                                .fileContent(SheetTestUtils.getFileData(BASE_REPORT_PATH + "single_report_1.xlsx"))
                                .expectedProfileCount(1)
                                .expectedPanNumber("AZDPS5443F")
                                .expectedGstNumbers(List.of("09AZDPS5443F1Z6"))
                                .expectedCustomersRecords(5)
                                .expectedSuppliersRecords(11)
                                .expectedSalesRecords(12)
                                .build(),
                        ReportControllerTestData.builder()
                                .fileContent(SheetTestUtils.getFileData(BASE_REPORT_PATH + "single_report_2.xlsx"))
                                .expectedProfileCount(1)
                                .expectedPanNumber("AZDPS5443F")
                                .expectedGstNumbers(List.of("07AZDPS5443F1ZA"))
                                .expectedCustomersRecords(11)
                                .expectedSuppliersRecords(11)
                                .expectedSalesRecords(12)
                                .build(),
                        ReportControllerTestData.builder()
                                .fileContent(SheetTestUtils.getFileData(BASE_REPORT_PATH + "full_console_report.xlsx"))
                                .expectedProfileCount(2)
                                .expectedPanNumber("AZDPS5443F")
                                .expectedGstNumbers(List.of("07AZDPS5443F1ZA", "09AZDPS5443F1Z6"))
                                .expectedCustomersRecords(11)
                                .expectedSuppliersRecords(11)
                                .expectedSalesRecords(12)
                                .build())
        );
    }

    @Timeout(value = 120)
    @ParameterizedTest
    @MethodSource("provideDetailsForParseExcelReport")
    void testParseExcelReportAndGetGSTDetails(ReportControllerTestData testData) throws Exception {
        String clientOrderId = null;
        String entityId = null;
        if (testData.getExpectedGstNumbers().size() > 1) {
            entityId = LocalDateTime.now().toString();
        } else {
            clientOrderId = LocalDateTime.now().toString();
        }

        testParseExcelReport(entityId, clientOrderId, testData);

        MvcResult reportResponse = sendGetGST3BReportRequest(entityId, Collections.singletonList(clientOrderId));
        validateGST3BReport(reportResponse, testData);
        validateGetGST3BReportRequestHistory(entityId, clientOrderId != null ? Collections.singletonList(clientOrderId) : null);
    }

    @Timeout(value = 120)
    @ParameterizedTest
    @MethodSource("provideDetailsForSingleParseExcelReport")
    void testParseExcelReportAndGetGSTDetailsTwoCalls(ReportControllerTestData testData) throws Exception {
        final String clientOrderId = UUID.randomUUID().toString();
        callParseExcelReportAndGetResponse(null, clientOrderId, testData);
        ClientOrder clientOrder1 = clientOrderRepository.findByEntityIdAndClientOrderId(null, clientOrderId);
        assertNotNull(clientOrder1);

        callParseExcelReportAndGetResponse(null, clientOrderId, testData);
        ClientOrder clientOrder2 = clientOrderRepository.findByEntityIdAndClientOrderId(null, clientOrderId);
        assertNotNull(clientOrder2);

        // verify that client order was deleted and new was created
        assertNotEquals(clientOrder1.getId(), clientOrder2.getId());
    }

    @Timeout(value = 300)
    @ParameterizedTest
    @MethodSource("provideDetailsForParseFullExcelReport")
    void testParseFullExcelReportAndGetByAllIds(
            ReportControllerTestData singleReportData1,
            ReportControllerTestData singleReportData2,
            ReportControllerTestData consoleReportData) throws Exception {
        String entityId = UUID.randomUUID().toString();
        String clientOrderId1 = UUID.randomUUID().toString();
        String clientOrderId2 = UUID.randomUUID().toString();

        testParseExcelReport(entityId, null, consoleReportData);
        testParseExcelReport(null, clientOrderId1, singleReportData1);
        testParseExcelReport(null, clientOrderId2, singleReportData2);

        MvcResult reportResponse = sendGetGST3BReportRequest(entityId, List.of(clientOrderId1, clientOrderId2));
        GSTReport gstReport = getGSTReportFromResponse(reportResponse);
        validateFullGST3BReport(gstReport, consoleReportData, List.of(singleReportData1, singleReportData2));

        // validate sales details
        GST3BSales consolidatedSales = gstReport.getGst3BConsolidatedSalesReport().getGst3BSales();
        List<GST3BSalesDetail> consolidatedSalesDetails = consolidatedSales.getGst3BSalesDetails().stream()
                .map(gst3BSalesDetail -> GST3BSalesDetail.builder()
                        .delayInFiling(gst3BSalesDetail.getDelayInFiling())
                        .delayedDays(gst3BSalesDetail.getDelayedDays())
                        .month(gst3BSalesDetail.getMonth())
                        .build())
                .toList();
        GST3BSales singleReportData2Sales = gstReport.getGst3BSalesReports().get(singleReportData2.getExpectedGstNumbers().get(0)).getGst3BSales();
        assertNotNull(singleReportData2Sales);
        int similarMonthCount = 0;
        for (GST3BSalesDetail gst3BSalesDetail : singleReportData2Sales.getGst3BSalesDetails()) {
            for (GST3BSalesDetail consolidatedSalesDetail : consolidatedSalesDetails) {
                if (consolidatedSalesDetail.getMonth().isEqual(gst3BSalesDetail.getMonth())) {
                    ++similarMonthCount;
                    assertEquals(consolidatedSalesDetail.getDelayedDays(), gst3BSalesDetail.getDelayedDays());
                    assertEquals(consolidatedSalesDetail.getDelayInFiling(), gst3BSalesDetail.getDelayInFiling());
                    break;
                }
            }
        }
        assertTrue(similarMonthCount >= 11, "At most 1 month of mismatch is expected");
        assertEquals(consolidatedSales.getAverageDelayInDays(), singleReportData2Sales.getAverageDelayInDays());
        assertEquals(consolidatedSales.getTotalDelayedDays(), singleReportData2Sales.getTotalDelayedDays());
    }

    @Timeout(value = 300)
    @ParameterizedTest
    @MethodSource("provideDetailsForParseFullExcelReport")
    void testParseFullExcelReportAndGetByOnlyEntityId(
            ReportControllerTestData singleReportData1,
            ReportControllerTestData singleReportData2,
            ReportControllerTestData consoleReportData) throws Exception {
        String entityId = UUID.randomUUID().toString();
        String clientOrderId1 = UUID.randomUUID().toString();
        String clientOrderId2 = UUID.randomUUID().toString();

        testParseExcelReport(entityId, null, consoleReportData);
        testParseExcelReport(entityId, clientOrderId1, singleReportData1);
        testParseExcelReport(entityId, clientOrderId2, singleReportData2);

        MvcResult reportResponse = sendGetGST3BReportRequest(entityId, null);
        GSTReport gstReport = getGSTReportFromResponse(reportResponse);
        validateFullGST3BReport(gstReport, consoleReportData, List.of(singleReportData1, singleReportData2));
        validateGetGST3BReportRequestHistory(entityId, null);
    }

    @Test
    void testParseExcelReportWithInvalidParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/report/parse-excel-report")
                        .file("file", new byte[0])
                        .param("client-order-id", (String) null)
                        .param("entity-id", (String) null)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/report/parse-excel-report")
                        .file("file", new byte[0])
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/report/parse-excel-report")
                        .file("file", new byte[0])
                        .param("client-order-id", "")
                        .param("entity-id", "")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Timeout(value = 120)
    @ParameterizedTest
    @MethodSource("provideDetailsForSingleParseExcelReport")
    void testUpdateManualEntriesGST3BReport(ReportControllerTestData testData) throws Exception {
        String clientOrderId = UUID.randomUUID().toString();
        callParseExcelReportAndGetResponse(null, clientOrderId, testData);

        MvcResult reportResponse = sendGetGST3BReportRequest(null, List.of(clientOrderId));
        validateGST3BReport(reportResponse, testData);
        validateGetGST3BReportRequestHistory(null, List.of(clientOrderId));

        // create entries
        updateReportAndValidate(reportResponse, testData, clientOrderId, BigDecimal.ONE);

        // update entries
        updateReportAndValidate(reportResponse, testData, clientOrderId, BigDecimal.TEN);
    }

    private MvcResult callParseExcelReportAndGetResponse(String entityId, String clientOrderId, ReportControllerTestData testData) throws Exception {
        MvcResult parseResponse = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/report/parse-excel-report")
                        .file("file", testData.getFileContent())
                        .param("client-order-id", clientOrderId)
                        .param("entity-id", entityId)
                        .param("created-by", TEST_USER)
                        .param("ip-address", IP_ADRRESS)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(parseResponse);
        return parseResponse;
    }

    private void testParseExcelReport(String entityId, String clientOrderId, ReportControllerTestData testData) throws Exception {
        MvcResult parseResponse = callParseExcelReportAndGetResponse(entityId, clientOrderId, testData);

        ProfileDetailResponse profileDetailResponse = objectMapper.readValue(parseResponse.getResponse().getContentAsString(), ProfileDetailResponse.class);
        assertNotNull(profileDetailResponse);
        assertNotNull(profileDetailResponse.getProfileDetails());
        assertEquals(testData.getExpectedProfileCount(), profileDetailResponse.getProfileDetails().size());
        for (int index = 0; index < profileDetailResponse.getProfileDetails().size(); ++index) {
            ProfileDetailResponseData profileDetailResponseData = profileDetailResponse.getProfileDetails().get(index);
            assertEquals(testData.getExpectedPanNumber(), profileDetailResponseData.getPanNumber());
            assertEquals(testData.getExpectedGstNumbers().get(index), profileDetailResponseData.getGstNumber());
        }

        ClientOrder clientOrder = clientOrderRepository.findByEntityIdAndClientOrderId(entityId, clientOrderId);
        assertNotNull(clientOrder);
    }

    private void updateReportAndValidate(MvcResult reportResponse, ReportControllerTestData testData, String clientOrderId, BigDecimal value) throws Exception {
        GSTReport gstReport = getGSTReportFromResponse(reportResponse);
        GSTReport updatedGstReport = getUpdatedGSTReport(gstReport, value);
        MvcResult parseResponse = mockMvc.perform(put("/api/report/update-manual-entries-gst-3b-report")
                        .content(objectMapper.writeValueAsString(UpdateGST3BReportManualEntryRequest.builder()
                                .clientOrderId(clientOrderId)
                                .gstReport(updatedGstReport)
                                .build()))
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(parseResponse);

        GSTReport responseReport = objectMapper.readValue(parseResponse.getResponse().getContentAsString(), GSTReport.class);
        assertEquals(updatedGstReport, responseReport);
        validateManualEntriesFromRepository(clientOrderId, value, testData);
    }

    private GSTReport getUpdatedGSTReport(GSTReport gstReport, BigDecimal value) {
        Map<String, GST3BSalesReport> gst3BSalesReportMap = gstReport.getGst3BSalesReports();
        assertEquals(1, gst3BSalesReportMap.size());
        assertNotNull(gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER));

        List<GST3BCustomersDetails> gst3BCustomersDetails = gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER).getGst3BCustomers().getGst3BCustomersDetails()
                .stream()
                .map(details -> details.toBuilder().numericEntryAsPerBanking(value).numericEntryAddToInterfirm(false).build())
                .toList();
        List<GST3BSuppliersDetails> gst3BSuppliersDetails = gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER).getGst3BSuppliers().getGst3BSuppliersDetails()
                .stream()
                .map(details -> details.toBuilder().numericEntryAsPerBanking(value).numericEntryAddToInterfirm(true).build())
                .toList();
        GST3BSalesReport updatedGST3BSalesReport = gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER).toBuilder()
                .circularOrOthersSuppliersAnalysis(value)
                .circularOrOthersCustomersAnalysis(value)
                .totalNumberOfMonthsSuppliersAnalysis(value.intValue())
                .totalNumberOfMonthsCustomersAnalysis(value.intValue())
                .gst3BCustomers(gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER).getGst3BCustomers().toBuilder().gst3BCustomersDetails(gst3BCustomersDetails).build())
                .gst3BSuppliers(gst3BSalesReportMap.get(SINGLE_REPORT_GST_NUMBER).getGst3BSuppliers().toBuilder().gst3BSuppliersDetails(gst3BSuppliersDetails).build())
                .build();

        return gstReport.toBuilder()
                .gst3BSalesReports(Map.of(SINGLE_REPORT_GST_NUMBER, updatedGST3BSalesReport))
                .build();
    }

    private void validateManualEntriesFromRepository(String clientOrderId, BigDecimal value, ReportControllerTestData testData) {
        ClientOrder clientOrder = clientOrderRepository.findByEntityIdAndClientOrderId(null, clientOrderId);
        assertNotNull(clientOrder);

        GSTReportManualEntry gstReportManualEntry = gstReportManualEntryRepository.findByClientOrderId(clientOrder.getId());
        assertNotNull(gstReportManualEntry);

        assertEquals(value.setScale(2, RoundingMode.HALF_UP), gstReportManualEntry.getCircularOrOthersCustomersAnalysis());
        assertEquals(value.setScale(2, RoundingMode.HALF_UP), gstReportManualEntry.getCircularOrOthersSuppliersAnalysis());
        assertEquals(value.intValue(), gstReportManualEntry.getTotalNumberOfMonthsCustomersAnalysis());
        assertEquals(value.intValue(), gstReportManualEntry.getTotalNumberOfMonthsSuppliersAnalysis());

        List<GSTReportManualBankingEntry> gstReportManualBankingEntries = gstReportManualEntry.getGstReportManualBankingEntryList();
        assertNotNull(gstReportManualBankingEntries);
        assertEquals(Math.max(testData.getExpectedCustomersRecords(), testData.getExpectedSuppliersRecords()), gstReportManualBankingEntries.size());
        for (int index = 0; index < gstReportManualBankingEntries.size(); index++) {
            GSTReportManualBankingEntry entry = gstReportManualBankingEntries.get(index);
            assertNotNull(entry.getGstReportManualEntry());
            if (index < testData.getExpectedCustomersRecords()) {
                assertEquals(value.setScale(2, RoundingMode.HALF_UP), entry.getCustomersAnalysisNumericEntryAsPerBanking());
                assertEquals(false, entry.getCustomersAnalysisNumericEntryAddToInterfirm());
            }
            if (index < testData.getExpectedSuppliersRecords()) {
                assertEquals(value.setScale(2, RoundingMode.HALF_UP), entry.getSuppliersAnalysisNumericEntryAsPerBanking());
                assertEquals(true, entry.getSuppliersAnalysisNumericEntryAddToInterfirm());
            }
        }
    }

    private void validateGetGST3BReportRequestHistory(String entityId, List<String> clientOrderIds) throws JsonProcessingException {
        awaitReportCompletion();
        UserInfo userInfo = userInfoRepository.findByUsername(TEST_USER);
        assertNotNull(userInfo);
        assertEquals(TEST_USER, userInfo.getUsername());

        ObjectMapper objectMapper = new ObjectMapper();
        GetGST3BReportRequestHistoryResponseDTO historyResponseDTO = objectMapper.readValue(
                userInfo.getGetGstReportRequestHistoryJSON(), GetGST3BReportRequestHistoryResponseDTO.class);
        assertNotNull(historyResponseDTO);
        List<GetGST3BReportRequestResponseInfo> responseInfos = historyResponseDTO.getGetGST3BReportRequestResponseInfoList();
        assertNotNull(responseInfos);
        assertFalse(responseInfos.isEmpty());
        GetGST3BReportRequestResponseInfo expectedInfo = GetGST3BReportRequestResponseInfo.builder().entityId(entityId).clientOrderIds(clientOrderIds).build();
        assertEquals(expectedInfo.getEntityId(), responseInfos.get(responseInfos.size() - 1).getEntityId());
        assertEquals(expectedInfo.getClientOrderIds(), responseInfos.get(responseInfos.size() - 1).getClientOrderIds());
    }

    private void awaitReportCompletion() {
        await().pollDelay(Duration.ofSeconds(5))
                .timeout(Duration.ofSeconds(60))
                .until(() -> userInfoRepository.findByUsername(TEST_USER) != null);
    }
}

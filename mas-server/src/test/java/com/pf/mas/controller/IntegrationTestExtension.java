package com.pf.mas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.mas.dto.report.GST3BSalesReport;
import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.GSTReportProfileDetail;
import lombok.Builder;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.ComposeContainer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IntegrationTestExtension implements BeforeAllCallback {
    // constants
    protected static final String TEST_USER = "test_user";
    protected static final String IP_ADRRESS = "127.0.0.1";
    private static final ComposeContainer COMPOSE_CONTAINER = new ComposeContainer(new File("src/test/resources/docker-compose/docker-compose-integration-test.yml"))
            .withLocalCompose(true)
            .withExposedService("mas_sql_server_db", 1433);
    private static final AtomicBoolean STARTED_FLAG = new AtomicBoolean(false);
    private static final String[] GET_GST_3B_REPORT_IGNORE_FIELDS = new String[]{
            ".*entityId.*",
            ".*clientOrderId.*",
            ".*numericEntryAsPerBanking.*",
            ".*numericEntryAddToInterfirm.*",
            ".*circularOrOthersCustomersAnalysis.*",
            ".*circularOrOthersSuppliersAnalysis.*",
            ".*totalNumberOfMonthsCustomersAnalysis.*",
            ".*totalNumberOfMonthsSuppliersAnalysis.*",
            ".*delayedDays.*",
            ".*delayInFiling.*",
            ".*averageDelayInDays.*",
            ".*totalDelayedDays.*"
    };
    // autowired
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ObjectMapper objectMapper;

    // fields
    protected MockMvc mockMvc;

    public static void close() {
        if (STARTED_FLAG.get()) {
            COMPOSE_CONTAINER.stop();
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!STARTED_FLAG.get() &&
                context.getTestClass().isPresent() && Arrays.stream(context.getTestClass().get().getAnnotations()).anyMatch(annotation -> annotation instanceof SpringBootTest)) {
            STARTED_FLAG.getAndSet(true);
            COMPOSE_CONTAINER.start();
        }
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        objectMapper.findAndRegisterModules();
    }

    protected MvcResult sendGetGST3BReportRequest(String entityId, List<String> clientOrderIds) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/report/gst-3b-report")
                .secure(true)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        if (entityId != null) {
            requestBuilder = requestBuilder.param("entity-id", entityId);
        }
        if (clientOrderIds != null) {
            requestBuilder = requestBuilder.param("client-order-ids", clientOrderIds.toArray(new String[0]));
        }
        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    protected void validateGST3BReport(MvcResult reportResponse, ReportControllerTestData testData) throws UnsupportedEncodingException, JsonProcessingException {
        GSTReport gstReport = getGSTReportFromResponse(reportResponse);

        validateGSTReportProfileDetails(gstReport, testData);

        if (testData.getExpectedGstNumbers().size() > 1) {
            validateGST3BReportFields(gstReport.getGst3BConsolidatedSalesReport(), testData, true);
        } else {
            assertEquals(testData.getExpectedGstNumbers().size(), gstReport.getGst3BSalesReports().size());
            for (Map.Entry<String, GST3BSalesReport> gst3BSalesReportEntry : gstReport.getGst3BSalesReports().entrySet()) {
                validateGST3BReportFields(gst3BSalesReportEntry.getValue(), testData, false);
            }
        }
    }

    protected void validateFullGST3BReport(
            GSTReport gstReport,
            ReportControllerTestData consoleReportTestData,
            List<ReportControllerTestData> singleReportTestDataList) {
        validateGSTReportProfileDetails(gstReport, consoleReportTestData);

        assertEquals(singleReportTestDataList.size(), gstReport.getGst3BSalesReports().size());
        assertEquals(singleReportTestDataList.stream().map(data -> data.getExpectedGstNumbers().get(0)).toList(),
                gstReport.getGst3BSalesReports().keySet().stream().toList());
        validateGST3BReportFields(gstReport.getGst3BConsolidatedSalesReport(), consoleReportTestData, true);

        Map<String, ReportControllerTestData> singleReportTestDataMap = singleReportTestDataList.stream()
                .collect(Collectors.toMap(testData -> testData.getExpectedGstNumbers().get(0), Function.identity()));
        for (Map.Entry<String, GST3BSalesReport> gst3BSalesReportEntry : gstReport.getGst3BSalesReports().entrySet()) {
            validateGST3BReportFields(gst3BSalesReportEntry.getValue(), singleReportTestDataMap.get(gst3BSalesReportEntry.getKey()), false);
        }
    }

    protected GSTReport getGSTReportFromResponse(MvcResult reportResponse) throws UnsupportedEncodingException, JsonProcessingException {
        assertNotNull(reportResponse);
        GSTReport gstReport = objectMapper.readValue(reportResponse.getResponse().getContentAsString(), GSTReport.class);
        assertNotNull(gstReport);
        return gstReport;
    }

    private void validateGSTReportProfileDetails(GSTReport gstReport, ReportControllerTestData testData) {
        assertNotNull(gstReport.getGstReportProfileDetails());
        assertEquals(testData.getExpectedProfileCount(), gstReport.getGstReportProfileDetails().size());
        for (int index = 0; index < gstReport.getGstReportProfileDetails().size(); ++index) {
            GSTReportProfileDetail gstReportProfileDetail = gstReport.getGstReportProfileDetails().get(index);
            assertEquals(testData.getExpectedPanNumber(), gstReportProfileDetail.getPanNumber());
            assertEquals(testData.getExpectedGstNumbers().get(index), gstReportProfileDetail.getGstNumber());
        }
    }

    private void validateGST3BReportFields(GST3BSalesReport gst3BSalesReport, ReportControllerTestData testData, boolean isConsolidated) {
        if (isConsolidated) {
            Assertions.assertThat(gst3BSalesReport).usingRecursiveAssertion().ignoringFieldsMatchingRegexes(GET_GST_3B_REPORT_IGNORE_FIELDS).hasNoNullFields();
        } else {
            List<String> newFields = new ArrayList<>(Arrays.asList(GET_GST_3B_REPORT_IGNORE_FIELDS));
            newFields.add("grossAdjustedRevenue");
            Assertions.assertThat(gst3BSalesReport).usingRecursiveAssertion().ignoringFieldsMatchingRegexes(newFields.toArray(new String[0])).hasNoNullFields();
        }
        assertEquals(testData.getExpectedCustomersRecords(), gst3BSalesReport.getGst3BCustomers().getGst3BCustomersDetails().size());
        assertEquals(testData.getExpectedSuppliersRecords(), gst3BSalesReport.getGst3BSuppliers().getGst3BSuppliersDetails().size());
        assertEquals(testData.getExpectedSalesRecords(), gst3BSalesReport.getGst3BSales().getGst3BSalesDetails().size());
    }

    @Getter
    @Builder
    protected static class ReportControllerTestData {
        private final byte[] fileContent;
        private final int expectedProfileCount;
        private final String expectedPanNumber;
        private final List<String> expectedGstNumbers;
        private final int expectedCustomersRecords;
        private final int expectedSuppliersRecords;
        private final int expectedSalesRecords;
    }
}

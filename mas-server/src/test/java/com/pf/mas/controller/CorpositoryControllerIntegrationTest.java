package com.pf.mas.controller;

import com.pf.corpository.model.MasGetReportRequest;
import com.pf.corpository.model.MasGetReportRequestBody;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.repository.ClientOrderRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.catchUncaughtExceptions;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class CorpositoryControllerIntegrationTest extends IntegrationTestExtension {
    // constants
    private static final String CLIENT_ORDER_ID = "1";
    private static final MasGetReportRequest GET_REPORT_REQUEST = MasGetReportRequest.builder()
            .request("get-report")
            .requestBody(MasGetReportRequestBody.builder()
                    .clientOrderId(CLIENT_ORDER_ID)
                    .build())
            .build();
    private static final String SAMPLE_RESPONSE_FILE_PATH = "src/test/resources/response/data";
    private static final byte[] REPORT_DOES_NOT_EXIST_RESPONSE = "report does not exist on api response".getBytes(StandardCharsets.UTF_8);

    @SpyBean
    private ClientOrderRepository clientOrderRepository;

    // mocks
    @MockBean
    private OkHttpClient okHttpClient;

    private static Stream<Arguments> provideDetailsForGSTDetails() {
        return Stream.of(
                Arguments.of(ReportControllerTestData.builder()
                        .expectedProfileCount(1)
                        .expectedPanNumber("ANNPV0350L")
                        .expectedGstNumbers(List.of("23ANNPV0350L1ZQ"))
                        .expectedCustomersRecords(0)
                        .expectedSuppliersRecords(11)
                        .expectedSalesRecords(12)
                        .build())
        );
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        mockCall(getSampleBinaryResponse());
    }

    @Test
    void testGetReport() throws Exception {
        MvcResult parseResponse = sendGetReportRequest();
        assertNotNull(parseResponse);
        assertArrayEquals(getSampleBinaryResponse(), parseResponse.getResponse().getContentAsByteArray());
        awaitReportCompletion();
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForGSTDetails")
    void testGetReportWhenOriginalCallFailedAndRetried(ReportControllerTestData testData) throws Exception {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientOrderId(CLIENT_ORDER_ID);
        clientOrder.setReportStatus(ClientOrder.ClientOrderReportStatus.IN_PROGRESS.toString());
        clientOrder.setCreatedBy(TEST_USER);
        clientOrder.setIpAddress(IP_ADRRESS);
        doThrow(new RuntimeException("expected failure")).when(clientOrderRepository).save(clientOrder);

        sendGetReportRequest();
        catchUncaughtExceptions().timeout(Duration.ofSeconds(60));
        assertTrue(clientOrderRepository.findAll().isEmpty());

        Mockito.reset(clientOrderRepository);
        mockCall(REPORT_DOES_NOT_EXIST_RESPONSE);

        MvcResult parseResponse = sendGetReportRequest();
        assertNotNull(parseResponse);
        assertArrayEquals(REPORT_DOES_NOT_EXIST_RESPONSE, parseResponse.getResponse().getContentAsByteArray());
        awaitReportCompletion();

        MvcResult reportResponse = sendGetGST3BReportRequest(null, List.of(CLIENT_ORDER_ID));
        validateGST3BReport(reportResponse, testData);
    }

    private void mockCall(byte[] responseArray) throws IOException {
        Call call = mock(Call.class);
        doReturn(call).when(okHttpClient).newCall(any());

        Response response = mock(Response.class);
        doReturn(response).when(call).execute();
        doReturn(HttpStatus.OK.value()).when(response).code();

        ResponseBody responseBody = mock(ResponseBody.class);
        doReturn(responseBody).when(response).body();

        InputStream is = mock(InputStream.class);
        doReturn(is).when(responseBody).byteStream();
        doReturn(responseArray).when(is).readAllBytes();
    }

    private MvcResult sendGetReportRequest() throws Exception {
        return mockMvc.perform(post("/api/corpository-api/get-report")
                        .secure(true)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(GET_REPORT_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("created-by", TEST_USER)
                        .param("ip-address", IP_ADRRESS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andReturn();
    }

    private byte[] getSampleBinaryResponse() throws IOException {
        File file = new File(SAMPLE_RESPONSE_FILE_PATH);
        assertTrue(file.exists());

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = inputStream.readAllBytes();
        assertTrue(data.length > 0);
        return data;
    }

    private void awaitReportCompletion() {
        await().pollDelay(Duration.ofSeconds(5))
                .timeout(Duration.ofSeconds(60))
                .until(() -> ClientOrder.ClientOrderReportStatus.COMPLETED.toString().equals(clientOrderRepository.findAll().get(0).getReportStatus()));
    }
}

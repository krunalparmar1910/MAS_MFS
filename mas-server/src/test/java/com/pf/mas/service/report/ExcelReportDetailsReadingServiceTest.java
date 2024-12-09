package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.ClientOrderReportDetails;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.ClientOrderReportDetailsRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExcelReportDetailsReadingServiceTest {
    // constants
    private static final ClientOrderReportDetails CLIENT_ORDER_REPORT_DETAILS_CONSOLIDATED_1 = ClientOrderReportDetails.builder()
            .reportCompanyName("RUSHABH CROP SCIENCE (Consolidated Report)")
            .reportDetailsString("""
                    PAN:\t\t\t\t\tAAUFR9715R
                    GSTN:\t\t\t\t\t23AAUFR9715R1Z4,24AAUFR9715R1Z2
                    Period Covered:\t\tApr-21 to Apr-23""")
            .reportPan("AAUFR9715R")
            .reportGstn("23AAUFR9715R1Z4,24AAUFR9715R1Z2")
            .periodCoveredFrom(LocalDate.parse("2021-04-01"))
            .periodCoveredTo(LocalDate.parse("2023-04-30"))
            .build();
    private static final ClientOrderReportDetails CLIENT_ORDER_REPORT_DETAILS_CONSOLIDATED_2 = ClientOrderReportDetails.builder()
            .reportCompanyName("SARVAN CARBOCHEM LLP & SARVAN  CARBOCHEM  LLP (Consolidated Report)")
            .reportDetailsString("""
                    PAN:\t\t\t\t\tACRFS8434P
                    GSTN:\t\t\t\t\t27ACRFS8434P3ZY,24ACRFS8434P1Z6,33ACRFS8434P1Z7
                    Period Covered:\t\tApr-21 to Nov-23""")
            .reportPan("ACRFS8434P")
            .reportGstn("27ACRFS8434P3ZY,24ACRFS8434P1Z6,33ACRFS8434P1Z7")
            .periodCoveredFrom(LocalDate.parse("2021-04-01"))
            .periodCoveredTo(LocalDate.parse("2023-11-30"))
            .build();
    private static final ClientOrderReportDetails CLIENT_ORDER_REPORT_DETAILS_SINGLE_1 = ClientOrderReportDetails.builder()
            .reportCompanyName("RUSHABH CROP SCIENCE")
            .reportDetailsString("""
                    PAN:\t\t\t\t\tAAUFR9715R
                    GSTN:\t\t\t\t\t23AAUFR9715R1Z4
                    Period Covered:\t\tApr-21 to Apr-23""")
            .reportPan("AAUFR9715R")
            .reportGstn("23AAUFR9715R1Z4")
            .periodCoveredFrom(LocalDate.parse("2021-04-01"))
            .periodCoveredTo(LocalDate.parse("2023-04-30"))
            .build();
    private static final ClientOrderReportDetails CLIENT_ORDER_REPORT_DETAILS_SINGLE_2 = ClientOrderReportDetails.builder()
            .reportCompanyName("BR CASHEWNUTS")
            .reportDetailsString("""
                    PAN:\t\t\t\t\tAPIPV8676C
                    GSTN:\t\t\t\t\t33APIPV8676C1ZG
                    Period Covered:\t\tApr-21 to Nov-23""")
            .reportPan("APIPV8676C")
            .reportGstn("33APIPV8676C1ZG")
            .periodCoveredFrom(LocalDate.parse("2021-04-01"))
            .periodCoveredTo(LocalDate.parse("2023-11-30"))
            .build();

    // mocks
    @Mock
    private ClientOrderReportDetailsRepository clientOrderReportDetailsRepository;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<ClientOrderReportDetails> clientOrderReportDetailsCaptor;

    // fields
    private ExcelReportDetailsReadingService reportDetailsReadingService;

    private static Stream<Arguments> provideDetailsForReadReportDetails() {
        SheetProvider sheetProvider = SheetProvider.getInstance();

        return Stream.of(
                Arguments.of(sheetProvider.getSheetMapConsolidated1FileName(), sheetProvider.getSheetMapConsolidated1(), CLIENT_ORDER_REPORT_DETAILS_CONSOLIDATED_1),
                Arguments.of(sheetProvider.getSheetMapConsolidated2FileName(), sheetProvider.getSheetMapConsolidated2(), CLIENT_ORDER_REPORT_DETAILS_CONSOLIDATED_2),
                Arguments.of(sheetProvider.getSheetMapSingle1FileName(), sheetProvider.getSheetMapSingle1(), CLIENT_ORDER_REPORT_DETAILS_SINGLE_1),
                Arguments.of(sheetProvider.getSheetMapSingle2FileName(), sheetProvider.getSheetMapSingle2(), CLIENT_ORDER_REPORT_DETAILS_SINGLE_2)
        );
    }

    @BeforeEach
    public void setup() {
        reportDetailsReadingService = new ExcelReportDetailsReadingService(clientOrderReportDetailsRepository);
        when(clientOrderReportDetailsRepository.save(any())).thenAnswer(m -> m.getArgument(0));
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadReportDetails")
    void testReadReportDetails(String fileName, Map<SheetTypeName, Sheet> sheetMap, ClientOrderReportDetails expectedClientOrderReportDetails) throws MasReportSheetReaderException {
        reportDetailsReadingService.readAndStoreReportDetails(fileName, sheetMap, clientOrder);
        validatedRecords(expectedClientOrderReportDetails, fileName);
    }

    private void validatedRecords(ClientOrderReportDetails expectedClientOrderReportDetails, String fileName) {
        verify(clientOrderReportDetailsRepository, times(1)).save(clientOrderReportDetailsCaptor.capture());
        ClientOrderReportDetails clientOrderReportDetails = clientOrderReportDetailsCaptor.getValue();
        assertNotNull(clientOrderReportDetails);

        assertEquals(clientOrder, clientOrderReportDetails.getClientOrder());
        assertEquals(fileName, clientOrderReportDetails.getReportFileName());
        assertEquals(expectedClientOrderReportDetails.getReportCompanyName(), clientOrderReportDetails.getReportCompanyName());
        assertEquals(expectedClientOrderReportDetails.getReportDetailsString(), clientOrderReportDetails.getReportDetailsString());
        assertEquals(expectedClientOrderReportDetails.getReportPan(), clientOrderReportDetails.getReportPan());
        assertEquals(expectedClientOrderReportDetails.getReportGstn(), clientOrderReportDetails.getReportGstn());
        assertEquals(expectedClientOrderReportDetails.getPeriodCoveredFrom(), clientOrderReportDetails.getPeriodCoveredFrom());
        assertEquals(expectedClientOrderReportDetails.getPeriodCoveredTo(), clientOrderReportDetails.getPeriodCoveredTo());
    }
}

package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.service.report.sheet.BifurcationStateAmountSheetReader;
import com.pf.mas.service.report.sheet.CustomerSupplierAnalysisSheetReader;
import com.pf.mas.service.report.sheet.CustomerSupplierWiseSheetReader;
import com.pf.mas.service.report.sheet.FilingDetailsSheetReader;
import com.pf.mas.service.report.sheet.IntraGroupSheetReader;
import com.pf.mas.service.report.sheet.SeasonalityAnalysisSheetReader;
import com.pf.mas.service.report.sheet.SheetReader;
import com.pf.mas.service.report.sheet.SummaryGSTYearlyTaxSheetReader;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExcelSheetReadingServiceTest {
    // constants
    private static final List<SheetTypeName> SUMMARY_GST_TAX_YEARLY_TAX_SHEETS = List.of(
            SheetTypeName.SUMMARY_ANALYSIS,
            SheetTypeName.SUMMARY,
            SheetTypeName.GSTR_3B,
            SheetTypeName.TAX,
            SheetTypeName.YEARLY_TAX
    );
    private static final List<SheetTypeName> CUSTOMER_SUPPLIER_ANALYSIS_SHEETS = List.of(
            SheetTypeName.CUSTOMERS_ANALYSIS,
            SheetTypeName.SUPPLIERS_ANALYSIS,
            SheetTypeName.HSN_CHAPTER_ANALYSIS,
            SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP
    );
    private static final List<SheetTypeName> CUSTOMER_SUPPLIER_ANALYSIS_SHEETS_CONSOLIDATED = List.of(
            SheetTypeName.CUSTOMERS_ANALYSIS,
            SheetTypeName.SUPPLIERS_ANALYSIS,
            SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP
    );
    private static final List<SheetTypeName> CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS = List.of(
            SheetTypeName.CIRCULAR_TRANSACTIONS,
            SheetTypeName.CUSTOMER_WISE,
            SheetTypeName.SUPPLIER_WISE,
            SheetTypeName.PRODUCT_WISE
    );
    private static final List<SheetTypeName> CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS_CONSOLIDATED = List.of(
            SheetTypeName.CIRCULAR_TRANSACTIONS,
            SheetTypeName.CUSTOMER_WISE,
            SheetTypeName.SUPPLIER_WISE
    );
    private static final List<SheetTypeName> BIFURCATION_STATE_AMOUNT_RETURN_SHEETS = List.of(
            SheetTypeName.BIFURCATION,
            SheetTypeName.ADJUSTED_AMOUNTS,
            SheetTypeName.STATE_WISE,
            SheetTypeName.YEARLY_RETURN_SUMMARY
    );
    private static final SheetType SHEET_TYPE = mock(SheetType.class);

    // mocks
    @Mock
    private SummaryGSTYearlyTaxSheetReader summaryGSTYearlyTaxSheetReader;
    @Mock
    private CustomerSupplierAnalysisSheetReader customerSupplierAnalysisSheetReader;
    @Mock
    private CustomerSupplierWiseSheetReader customerSupplierWiseSheetReader;
    @Mock
    private BifurcationStateAmountSheetReader bifurcationStateAmountSheetReader;
    @Mock
    private SeasonalityAnalysisSheetReader seasonalityAnalysisSheetReader;
    @Mock
    private IntraGroupSheetReader intraGroupSheetReader;
    @Mock
    private FilingDetailsSheetReader filingDetailsSheetReader;
    @Mock
    private ClientOrder clientOrder;
    @Captor
    private ArgumentCaptor<SheetTypeName> summaryGSTYearlyTaxSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> customerSupplierAnalysisSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> customerSupplierWiseSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> bifurcationStateAmountSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> seasonalityAnalysisSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> intraGroupSheetReaderCaptor;
    @Captor
    private ArgumentCaptor<SheetTypeName> filingDetailsSheetReaderCaptor;

    // fields
    private SheetReadingService sheetReadingService;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();

        Map<SheetTypeName, SheetType> sheetTypeMap = Arrays.stream(SheetTypeName.values())
                .collect(Collectors.toMap(Function.identity(), (unused) -> SHEET_TYPE));

        return Stream.of(
                Arguments.of(sheetProvider.getSheetMapSingle1(), sheetTypeMap),
                Arguments.of(sheetProvider.getSheetMapSingle1(), sheetTypeMap)
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();

        Map<SheetTypeName, SheetType> sheetTypeMap = Arrays.stream(SheetTypeName.values())
                .collect(Collectors.toMap(Function.identity(), (unused) -> SHEET_TYPE));

        return Stream.of(
                Arguments.of(sheetProvider.getSheetMapConsolidated1(), sheetTypeMap),
                Arguments.of(sheetProvider.getSheetMapConsolidated1(), sheetTypeMap)
        );
    }

    @BeforeEach
    public void setup() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.initialize();

        sheetReadingService = new ExcelSheetReadingService(
                taskExecutor,
                summaryGSTYearlyTaxSheetReader,
                customerSupplierAnalysisSheetReader,
                customerSupplierWiseSheetReader,
                bifurcationStateAmountSheetReader,
                seasonalityAnalysisSheetReader,
                intraGroupSheetReader,
                filingDetailsSheetReader
        );
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadSingleReport")
    void testReadAllSheetsAndStoreDataForSingleReport(
            Map<SheetTypeName, Sheet> sheetMap, Map<SheetTypeName, SheetType> sheetTypeMap) throws MasReportSheetReaderException {
        sheetReadingService.readAllSheetsAndStoreData(sheetMap, sheetTypeMap, clientOrder);
        validateInvocation(false);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadConsolidatedReport")
    void testReadAllSheetsAndStoreDataForConsolidatedReport(
            Map<SheetTypeName, Sheet> sheetMap, Map<SheetTypeName, SheetType> sheetTypeMap) throws MasReportSheetReaderException {
        sheetReadingService.readAllSheetsAndStoreData(sheetMap, sheetTypeMap, clientOrder);
        validateInvocation(true);
    }

    private void validateInvocation(boolean isConsolidatedReport) throws MasReportSheetReaderException {
        validateSheetReaderInvocation(summaryGSTYearlyTaxSheetReader, SUMMARY_GST_TAX_YEARLY_TAX_SHEETS, summaryGSTYearlyTaxSheetReaderCaptor);
        validateSheetReaderInvocation(customerSupplierAnalysisSheetReader,
                isConsolidatedReport ? CUSTOMER_SUPPLIER_ANALYSIS_SHEETS_CONSOLIDATED : CUSTOMER_SUPPLIER_ANALYSIS_SHEETS, customerSupplierAnalysisSheetReaderCaptor);
        validateSheetReaderInvocation(customerSupplierWiseSheetReader,
                isConsolidatedReport ? CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS_CONSOLIDATED : CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS, customerSupplierWiseSheetReaderCaptor);
        validateSheetReaderInvocation(bifurcationStateAmountSheetReader,
                BIFURCATION_STATE_AMOUNT_RETURN_SHEETS, bifurcationStateAmountSheetReaderCaptor);
        validateSheetReaderInvocation(seasonalityAnalysisSheetReader, List.of(SheetTypeName.SEASONALITY_ANALYSIS), seasonalityAnalysisSheetReaderCaptor);
        validateSheetReaderInvocation(intraGroupSheetReader, isConsolidatedReport ? List.of(SheetTypeName.INTRA_GROUP) : List.of(), intraGroupSheetReaderCaptor);
        validateSheetReaderInvocation(filingDetailsSheetReader, List.of(SheetTypeName.PROFILE_FILING_TABLE), filingDetailsSheetReaderCaptor);
    }

    private void validateSheetReaderInvocation(
            SheetReader sheetReader,
            List<SheetTypeName> sheetTypeNameList,
            ArgumentCaptor<SheetTypeName> argumentCaptor) throws MasReportSheetReaderException {
        verify(sheetReader, times(sheetTypeNameList.size())).readSheetAndStoreData(argumentCaptor.capture(), any(), eq(SHEET_TYPE), eq(clientOrder));
        assertEquals(sheetTypeNameList, argumentCaptor.getAllValues());
    }
}

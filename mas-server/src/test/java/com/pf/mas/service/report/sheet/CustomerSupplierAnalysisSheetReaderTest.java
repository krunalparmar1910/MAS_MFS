package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.CustomerSupplierAnalysisTotal;
import com.pf.mas.model.entity.sheet.CustomersAnalysis;
import com.pf.mas.model.entity.sheet.DetailsOfCustomersAndSuppliers;
import com.pf.mas.model.entity.sheet.HSNChapterAnalysis;
import com.pf.mas.model.entity.sheet.SuppliersAnalysis;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.CustomerSupplierAnalysisTotalRepository;
import com.pf.mas.repository.sheet.CustomersAnalysisRepository;
import com.pf.mas.repository.sheet.DetailsOfCusomtersAndSuppliersRepository;
import com.pf.mas.repository.sheet.HSNChapterAnalysisRepository;
import com.pf.mas.repository.sheet.SuppliersAnalysisRepository;
import com.pf.mas.service.report.SheetProvider;
import com.pf.mas.service.report.SheetTestUtils;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerSupplierAnalysisSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private CustomersAnalysisRepository customersAnalysisRepository;
    @Mock
    private SuppliersAnalysisRepository suppliersAnalysisRepository;
    @Mock
    private HSNChapterAnalysisRepository hsnChapterAnalysisRepository;
    @Mock
    private DetailsOfCusomtersAndSuppliersRepository detailsOfCusomtersAndSuppliersRepository;
    @Mock
    private CustomerSupplierAnalysisTotalRepository customerSupplierAnalysisTotalRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<CustomersAnalysis>> customersAnalysisCaptor;
    @Captor
    private ArgumentCaptor<List<SuppliersAnalysis>> suppliersAnalysisCaptor;
    @Captor
    private ArgumentCaptor<List<HSNChapterAnalysis>> hsnChapterAnalysisCaptor;
    @Captor
    private ArgumentCaptor<List<DetailsOfCustomersAndSuppliers>> detailsOfCustAndSuppCaptor;
    @Captor
    private ArgumentCaptor<List<CustomerSupplierAnalysisTotal>> customerSupplierTotalCaptor;

    // fields
    private CustomerSupplierAnalysisSheetReader customerSupplierAnalysisSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CUSTOMERS_ANALYSIS).expectedGroups(4).expectedRecords(60).expectedTotalFields(9).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUPPLIERS_ANALYSIS).expectedGroups(4).expectedRecords(10).expectedTotalFields(9).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.HSN_CHAPTER_ANALYSIS).expectedGroups(8).expectedRecords(24).expectedTotalFields(30).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP).expectedGroups(2).expectedRecords(24).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CUSTOMERS_ANALYSIS).expectedGroups(4).expectedRecords(80).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUPPLIERS_ANALYSIS).expectedGroups(4).expectedRecords(80).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.HSN_CHAPTER_ANALYSIS).expectedGroups(0).expectedRecords(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP).expectedGroups(2).expectedRecords(40).expectedTotalFields(0).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CUSTOMERS_ANALYSIS).expectedGroups(4).expectedRecords(74).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUPPLIERS_ANALYSIS).expectedGroups(4).expectedRecords(76).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP).expectedGroups(2).expectedRecords(40).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CUSTOMERS_ANALYSIS).expectedGroups(4).expectedRecords(80).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUPPLIERS_ANALYSIS).expectedGroups(4).expectedRecords(80).expectedTotalFields(12).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP).expectedGroups(2).expectedRecords(40).expectedTotalFields(0).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.CUSTOMERS_ANALYSIS).expectedGroups(5).expectedRecords(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.SUPPLIERS_ANALYSIS).expectedGroups(4).expectedRecords(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.HSN_CHAPTER_ANALYSIS).expectedGroups(7).expectedRecords(7).expectedTotalFields(5).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP).expectedGroups(2).expectedRecords(1).expectedTotalFields(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        customerSupplierAnalysisSheetReader = new CustomerSupplierAnalysisSheetReader(
                fieldDateMonthYearSheetReader,
                fieldGroupRepository,
                customersAnalysisRepository,
                suppliersAnalysisRepository,
                hsnChapterAnalysisRepository,
                detailsOfCusomtersAndSuppliersRepository,
                customerSupplierAnalysisTotalRepository);
        when(fieldDateMonthYearRepository.save(any())).thenAnswer(m -> m.getArgument(0));
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadSingleReport")
    void testReadSingleReport(SheetTestUtils.SheetReaderTestParams params) throws MasReportSheetReaderException {
        readSheetAndStoreData(params);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadConsolidatedReport")
    void testReadConsolidatedReport(SheetTestUtils.SheetReaderTestParams params) throws MasReportSheetReaderException {
        readSheetAndStoreData(params);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadInvalidReport")
    void testReadInvalidReport(SheetTestUtils.SheetReaderTestParams params) throws MasReportSheetReaderException {
        readSheetAndStoreData(params);
    }

    private void readSheetAndStoreData(SheetTestUtils.SheetReaderTestParams params) throws MasReportSheetReaderException {
        customerSupplierAnalysisSheetReader.readSheetAndStoreData(
                params.getSheetTypeName(), params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params);
    }

    private void validateGroups(int expectedGroups) {
        verify(fieldGroupRepository, times(1)).saveAll(fieldGroupCaptor.capture());
        SheetTestUtils.validateFieldGroups(fieldGroupCaptor.getValue(), expectedGroups);
    }

    private void validateRecords(SheetTestUtils.SheetReaderTestParams params) {
        SheetTypeName sheetTypeName = params.getSheetTypeName();
        int expectedRecords = params.getExpectedRecords();
        int expectedTotalFields = params.getExpectedTotalFields();

        verify(customerSupplierAnalysisTotalRepository, times(1)).saveAll(customerSupplierTotalCaptor.capture());
        SheetTestUtils.validateNonNullFields(customerSupplierTotalCaptor.getValue(), expectedTotalFields, "id", "asPercentOfTotalValue", "asPercentOfTotalValueNumeric");
        List<CustomerSupplierAnalysisTotal> numericTotalFields = customerSupplierTotalCaptor.getValue().stream()
                .filter(total -> SheetTestUtils.expectNumericValue(total.getAsPercentOfTotalValue())).toList();
        SheetTestUtils.validateNonNullFields(numericTotalFields, numericTotalFields.size(), "id");

        if (expectedTotalFields > 0) {
            assertTrue(customerSupplierTotalCaptor.getValue().stream().anyMatch(c -> c.getAsPercentOfTotalValue() != null));
        }

        if (SheetTypeName.CUSTOMERS_ANALYSIS == sheetTypeName) {
            verify(customersAnalysisRepository, times(1)).saveAll(customersAnalysisCaptor.capture());
            SheetTestUtils.validateNonNullFields(customersAnalysisCaptor.getValue(), expectedRecords, "id", "flagForCategoryChange");
        } else if (SheetTypeName.SUPPLIERS_ANALYSIS == sheetTypeName) {
            verify(suppliersAnalysisRepository, times(1)).saveAll(suppliersAnalysisCaptor.capture());
            SheetTestUtils.validateNonNullFields(suppliersAnalysisCaptor.getValue(), expectedRecords, "id", "flagForCategoryChange", "averageInvoiceValueNumeric");

            List<SuppliersAnalysis> numericValueFields = suppliersAnalysisCaptor.getValue().stream()
                    .filter(suppliersAnalysis -> SheetTestUtils.expectNumericValue(suppliersAnalysis.getAverageInvoiceValue())).toList();
            SheetTestUtils.validateNonNullFields(numericValueFields, numericValueFields.size(), "id", "flagForCategoryChange");
        } else if (SheetTypeName.HSN_CHAPTER_ANALYSIS == sheetTypeName) {
            verify(hsnChapterAnalysisRepository, times(1)).saveAll(hsnChapterAnalysisCaptor.capture());
            assertEquals(expectedRecords, hsnChapterAnalysisCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<HSNChapterAnalysis> chapters = hsnChapterAnalysisCaptor.getValue().stream().filter(c -> c.getChapter() != null).toList();
                List<HSNChapterAnalysis> products = hsnChapterAnalysisCaptor.getValue().stream().filter(c -> c.getProduct() != null).toList();
                assertNotEquals((expectedTotalFields > 0), chapters.isEmpty());
                assertNotEquals((expectedTotalFields > 0), products.isEmpty());
                SheetTestUtils.validateNonNullFields(chapters, chapters.size(), "id", "product");
                SheetTestUtils.validateNonNullFields(products, products.size(), "id", "chapter");
            }
        } else if (SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP == sheetTypeName) {
            verify(detailsOfCusomtersAndSuppliersRepository, times(1)).saveAll(detailsOfCustAndSuppCaptor.capture());
            assertEquals(expectedRecords, detailsOfCustAndSuppCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<DetailsOfCustomersAndSuppliers> customers = detailsOfCustAndSuppCaptor.getValue().stream().filter(c -> c.getCustomerGSTN() != null).toList();
                List<DetailsOfCustomersAndSuppliers> suppliers = detailsOfCustAndSuppCaptor.getValue().stream().filter(c -> c.getSupplierGSTN() != null).toList();
                assertFalse(customers.isEmpty());
                assertFalse(suppliers.isEmpty());
                SheetTestUtils.validateNonNullFields(customers, customers.size(), "id", "supplierGSTN", "dateOfCancellation");
                SheetTestUtils.validateNonNullFields(suppliers, suppliers.size(), "id", "customerGSTN", "dateOfCancellation");
            }
        }
    }
}

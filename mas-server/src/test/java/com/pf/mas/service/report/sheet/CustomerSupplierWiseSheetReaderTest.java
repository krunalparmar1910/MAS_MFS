package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.CircularTransactions;
import com.pf.mas.model.entity.sheet.CircularTransactionsValue;
import com.pf.mas.model.entity.sheet.CustomerProductSupplierWiseTotal;
import com.pf.mas.model.entity.sheet.CustomerWise;
import com.pf.mas.model.entity.sheet.CustomerWiseValue;
import com.pf.mas.model.entity.sheet.ProductWise;
import com.pf.mas.model.entity.sheet.ProductWiseValue;
import com.pf.mas.model.entity.sheet.SupplierWise;
import com.pf.mas.model.entity.sheet.SupplierWiseValue;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.CircularTransactionsRepository;
import com.pf.mas.repository.sheet.CircularTransactionsValueRepository;
import com.pf.mas.repository.sheet.CustomerProductSupplierWiseTotalRepository;
import com.pf.mas.repository.sheet.CustomerWiseRepository;
import com.pf.mas.repository.sheet.CustomerWiseValueRepository;
import com.pf.mas.repository.sheet.ProductWiseRepository;
import com.pf.mas.repository.sheet.ProductWiseValueRepository;
import com.pf.mas.repository.sheet.SupplierWiseRepository;
import com.pf.mas.repository.sheet.SupplierWiseValueRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerSupplierWiseSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private CircularTransactionsRepository circularTransactionsRepository;
    @Mock
    private CustomerWiseRepository customerWiseRepository;
    @Mock
    private ProductWiseRepository productWiseRepository;
    @Mock
    private SupplierWiseRepository supplierWiseRepository;
    @Mock
    private CircularTransactionsValueRepository circularTransactionsValueRepository;
    @Mock
    private CustomerWiseValueRepository customerWiseValueRepository;
    @Mock
    private ProductWiseValueRepository productWiseValueRepository;
    @Mock
    private SupplierWiseValueRepository supplierWiseValueRepository;
    @Mock
    private CustomerProductSupplierWiseTotalRepository customerProductSupplierWiseTotalRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<CustomerWise>> customerWiseCaptor;
    @Captor
    private ArgumentCaptor<List<SupplierWise>> supplierWiseCaptor;
    @Captor
    private ArgumentCaptor<List<ProductWise>> productWiseCaptor;
    @Captor
    private ArgumentCaptor<List<CircularTransactions>> circularTransactionsCaptor;
    @Captor
    private ArgumentCaptor<List<CustomerWiseValue>> customerWiseValueCaptor;
    @Captor
    private ArgumentCaptor<List<SupplierWiseValue>> supplierWiseValueCaptor;
    @Captor
    private ArgumentCaptor<List<ProductWiseValue>> productWiseValueCaptor;
    @Captor
    private ArgumentCaptor<List<CircularTransactionsValue>> circularTransactionsValueCaptor;
    @Captor
    private ArgumentCaptor<List<CustomerProductSupplierWiseTotal>> customerProductSupplierWiseTotalCaptor;

    // fields
    private CustomerSupplierWiseSheetReader customerSupplierWiseSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CIRCULAR_TRANSACTIONS)
                        .expectedGroups(1).expectedRecords(2).expectedValueFields(58).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CUSTOMER_WISE)
                        .expectedGroups(1).expectedRecords(45).expectedValueFields(1305).expectedTotalFields(58).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUPPLIER_WISE)
                        .expectedGroups(1).expectedRecords(4).expectedValueFields(116).expectedTotalFields(29).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.PRODUCT_WISE)
                        .expectedGroups(1).expectedRecords(8).expectedValueFields(232).expectedTotalFields(58).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CIRCULAR_TRANSACTIONS)
                        .expectedGroups(1).expectedRecords(14).expectedValueFields(504).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CUSTOMER_WISE)
                        .expectedGroups(1).expectedRecords(52).expectedValueFields(1872).expectedTotalFields(72).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUPPLIER_WISE)
                        .expectedGroups(1).expectedRecords(84).expectedValueFields(3024).expectedTotalFields(36).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.PRODUCT_WISE)
                        .expectedGroups(1).expectedRecords(0).expectedValueFields(0).expectedTotalFields(0).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CIRCULAR_TRANSACTIONS)
                        .expectedGroups(1).expectedRecords(2).expectedValueFields(58).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.CUSTOMER_WISE)
                        .expectedGroups(1).expectedRecords(140).expectedValueFields(4060).expectedTotalFields(58).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUPPLIER_WISE)
                        .expectedGroups(1).expectedRecords(85).expectedValueFields(2465).expectedTotalFields(29).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CIRCULAR_TRANSACTIONS)
                        .expectedGroups(1).expectedRecords(18).expectedValueFields(648).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.CUSTOMER_WISE)
                        .expectedGroups(1).expectedRecords(460).expectedValueFields(16560).expectedTotalFields(72).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUPPLIER_WISE)
                        .expectedGroups(1).expectedRecords(289).expectedValueFields(10404).expectedTotalFields(36).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.CIRCULAR_TRANSACTIONS)
                        .expectedGroups(1).expectedRecords(0).expectedValueFields(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.CUSTOMER_WISE)
                        .expectedGroups(0).expectedRecords(0).expectedValueFields(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.SUPPLIER_WISE)
                        .expectedGroups(1).expectedRecords(0).expectedValueFields(0).expectedTotalFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.PRODUCT_WISE)
                        .expectedGroups(1).expectedRecords(1).expectedValueFields(0).expectedTotalFields(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        customerSupplierWiseSheetReader = new CustomerSupplierWiseSheetReader(
                fieldDateMonthYearSheetReader,
                fieldGroupRepository,
                circularTransactionsRepository,
                customerWiseRepository,
                productWiseRepository,
                supplierWiseRepository,
                circularTransactionsValueRepository,
                customerWiseValueRepository,
                productWiseValueRepository,
                supplierWiseValueRepository,
                customerProductSupplierWiseTotalRepository);
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
        customerSupplierWiseSheetReader.readSheetAndStoreData(
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
        int expectedValueFields = params.getExpectedValueFields();

        verify(customerProductSupplierWiseTotalRepository, times(1)).saveAll(customerProductSupplierWiseTotalCaptor.capture());
        SheetTestUtils.validateNonNullFields(customerProductSupplierWiseTotalCaptor.getValue(), expectedTotalFields, "id", "fieldValueNumeric");

        List<CustomerProductSupplierWiseTotal> numericValueFields = customerProductSupplierWiseTotalCaptor.getValue().stream()
                .filter(total -> SheetTestUtils.expectNumericValue(total.getTotalValue())).toList();
        SheetTestUtils.validateNonNullFields(numericValueFields, numericValueFields.size(), "id");

        if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            verify(customerWiseRepository, times(1)).saveAll(customerWiseCaptor.capture());
            SheetTestUtils.validateNonNullFields(customerWiseCaptor.getValue(), expectedRecords, "id", "values");

            verify(customerWiseValueRepository, times(1)).saveAll(customerWiseValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(customerWiseValueCaptor.getValue(), expectedValueFields, "id", "fieldValueNumeric");
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            verify(supplierWiseRepository, times(1)).saveAll(supplierWiseCaptor.capture());
            SheetTestUtils.validateNonNullFields(supplierWiseCaptor.getValue(), expectedRecords, "id", "values");

            verify(supplierWiseValueRepository, times(1)).saveAll(supplierWiseValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(supplierWiseValueCaptor.getValue(), expectedValueFields, "id", "fieldValueNumeric");
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            verify(productWiseRepository, times(1)).saveAll(productWiseCaptor.capture());
            if (!params.isInvalidData()) {
                SheetTestUtils.validateNonNullFields(productWiseCaptor.getValue(), expectedRecords, "id", "values");
            }

            verify(productWiseValueRepository, times(1)).saveAll(productWiseValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(productWiseValueCaptor.getValue(), expectedValueFields, "id", "fieldValueNumeric");
        } else if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            verify(circularTransactionsRepository, times(1)).saveAll(circularTransactionsCaptor.capture());
            SheetTestUtils.validateNonNullFields(circularTransactionsCaptor.getValue(), expectedRecords, "id", "values");

            verify(circularTransactionsValueRepository, times(1)).saveAll(circularTransactionsValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(circularTransactionsValueCaptor.getValue(), expectedValueFields, "id", "fieldValueNumeric");
        }
    }
}

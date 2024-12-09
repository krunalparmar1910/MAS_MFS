package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.IntraGroup;
import com.pf.mas.model.entity.sheet.IntraGroupPurchasesAndExpensesValue;
import com.pf.mas.model.entity.sheet.IntraGroupRevenueValue;
import com.pf.mas.model.entity.sheet.IntraGroupSummaryOfGroupTransaction;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.IntraGroupPurchasesAndExpensesValueRepository;
import com.pf.mas.repository.sheet.IntraGroupRepository;
import com.pf.mas.repository.sheet.IntraGroupRevenueValueRepository;
import com.pf.mas.repository.sheet.IntraGroupSummaryOfGroupTransactionRepository;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntraGroupSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private IntraGroupRepository intraGroupRepository;
    @Mock
    private IntraGroupRevenueValueRepository intraGroupRevenueValueRepository;
    @Mock
    private IntraGroupPurchasesAndExpensesValueRepository intraGroupPurchasesAndExpensesValueRepository;
    @Mock
    private IntraGroupSummaryOfGroupTransactionRepository intraGroupSummaryOfGroupTransactionRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<IntraGroup>> intraGroupListCaptor;
    @Captor
    private ArgumentCaptor<List<IntraGroupRevenueValue>> intraGroupRevenueValueListCaptor;
    @Captor
    private ArgumentCaptor<List<IntraGroupPurchasesAndExpensesValue>> intraGroupPurchasesAndExpensesValueListCaptor;
    @Captor
    private ArgumentCaptor<List<IntraGroupSummaryOfGroupTransaction>> intraGroupSummaryOfGroupTransactionListCaptor;
    @Captor
    private ArgumentCaptor<FieldDateMonthYear> fieldDateMonthYearCaptor;

    // fields
    private IntraGroupSheetReader intraGroupSheetReader;

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.INTRA_GROUP).expectedGroups(6).expectedRecords(3).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.INTRA_GROUP).expectedGroups(6).expectedRecords(4).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.INTRA_GROUP).expectedGroups(6).expectedRecords(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        intraGroupSheetReader = new IntraGroupSheetReader(
                fieldDateMonthYearSheetReader,
                fieldGroupRepository,
                intraGroupRepository,
                intraGroupRevenueValueRepository,
                intraGroupPurchasesAndExpensesValueRepository,
                intraGroupSummaryOfGroupTransactionRepository
        );
        when(fieldDateMonthYearRepository.save(any())).thenAnswer(m -> m.getArgument(0));
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
        intraGroupSheetReader.readSheetAndStoreData(
                params.getSheetTypeName(), params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params.getExpectedRecords());
    }

    private void validateGroups(int expectedGroups) {
        verify(fieldGroupRepository, times(1)).saveAll(fieldGroupCaptor.capture());
        SheetTestUtils.validateFieldGroups(fieldGroupCaptor.getValue(), expectedGroups);
    }

    private void validateRecords(int expectedRecords) {
        verify(fieldDateMonthYearRepository, atLeastOnce()).save(fieldDateMonthYearCaptor.capture());
        // subtract one since sheet date is also saved which is not a part of calculating total records
        int dates = fieldDateMonthYearCaptor.getAllValues().size() - 1;

        verify(intraGroupRepository, times(1)).saveAll(intraGroupListCaptor.capture());
        SheetTestUtils.validateNonNullFields(intraGroupListCaptor.getValue(), expectedRecords, "id",
                "intraGroupRevenueValues", "intraGroupPurchasesAndExpensesValues", "intraGroupSummaryOfGroupTransactions");

        verify(intraGroupRevenueValueRepository, times(1)).saveAll(intraGroupRevenueValueListCaptor.capture());
        SheetTestUtils.validateNonNullSheetDateValueFields(intraGroupRevenueValueListCaptor.getValue(), expectedRecords * dates,
                "id", "percentOfIntraGroupRevenueNumeric");
        List<IntraGroupRevenueValue> numericRevenueValues = intraGroupRevenueValueListCaptor.getValue().stream()
                .filter(revenueValue -> SheetTestUtils.expectNumericValue(revenueValue.getPercentOfIntraGroupRevenue())).toList();
        SheetTestUtils.validateNonNullSheetDateValueFields(numericRevenueValues, numericRevenueValues.size(), "id");

        verify(intraGroupPurchasesAndExpensesValueRepository, times(1)).saveAll(intraGroupPurchasesAndExpensesValueListCaptor.capture());
        SheetTestUtils.validateNonNullSheetDateValueFields(intraGroupPurchasesAndExpensesValueListCaptor.getValue(), expectedRecords * dates,
                "id", "percentOfIntraGroupPurchasesAndExpensesNumeric");
        List<IntraGroupPurchasesAndExpensesValue> numericPurchasesExpensesValues = intraGroupPurchasesAndExpensesValueListCaptor.getValue().stream()
                .filter(revenueValue -> SheetTestUtils.expectNumericValue(revenueValue.getPercentOfIntraGroupPurchasesAndExpenses())).toList();
        SheetTestUtils.validateNonNullSheetDateValueFields(numericPurchasesExpensesValues, numericPurchasesExpensesValues.size(), "id");

        verify(intraGroupSummaryOfGroupTransactionRepository, times(1)).saveAll(intraGroupSummaryOfGroupTransactionListCaptor.capture());
        SheetTestUtils.validateNonNullSheetDateValueFields(
                intraGroupSummaryOfGroupTransactionListCaptor.getValue(), expectedRecords * (expectedRecords - 1) * dates, "id");
    }
}

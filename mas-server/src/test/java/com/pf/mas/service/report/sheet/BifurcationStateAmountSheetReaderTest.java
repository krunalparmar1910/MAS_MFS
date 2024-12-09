package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.AdjustedAmounts;
import com.pf.mas.model.entity.sheet.AdjustedAmountsValue;
import com.pf.mas.model.entity.sheet.Bifurcation;
import com.pf.mas.model.entity.sheet.BifurcationValue;
import com.pf.mas.model.entity.sheet.StateWise;
import com.pf.mas.model.entity.sheet.StateWiseValue;
import com.pf.mas.model.entity.sheet.YearlyReturnSummary;
import com.pf.mas.model.entity.sheet.YearlyReturnSummaryValue;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.AdjustedAmountsRepository;
import com.pf.mas.repository.sheet.AdjustedAmountsValueRepository;
import com.pf.mas.repository.sheet.BifurcationRepository;
import com.pf.mas.repository.sheet.BifurcationValueRepository;
import com.pf.mas.repository.sheet.StateWiseRepository;
import com.pf.mas.repository.sheet.StateWiseValueRepository;
import com.pf.mas.repository.sheet.YearlyReturnSummaryRepository;
import com.pf.mas.repository.sheet.YearlyReturnSummaryValueRepository;
import com.pf.mas.service.report.SheetProvider;
import com.pf.mas.service.report.SheetTestUtils;
import org.apache.commons.lang3.StringUtils;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BifurcationStateAmountSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private BifurcationRepository bifurcationRepository;
    @Mock
    private BifurcationValueRepository bifurcationValueRepository;
    @Mock
    private AdjustedAmountsRepository adjustedAmountsRepository;
    @Mock
    private AdjustedAmountsValueRepository adjustedAmountsValueRepository;
    @Mock
    private StateWiseRepository stateWiseRepository;
    @Mock
    private StateWiseValueRepository stateWiseValueRepository;
    @Mock
    private YearlyReturnSummaryRepository yearlyReturnSummaryRepository;
    @Mock
    private YearlyReturnSummaryValueRepository yearlyReturnSummaryValueRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<Bifurcation>> bifurcationCaptor;
    @Captor
    private ArgumentCaptor<List<BifurcationValue>> bifurcationValueCaptor;
    @Captor
    private ArgumentCaptor<List<AdjustedAmounts>> adjustedAmountsCaptor;
    @Captor
    private ArgumentCaptor<List<AdjustedAmountsValue>> adjustedAmountsValueCaptor;
    @Captor
    private ArgumentCaptor<List<StateWise>> stateWiseCaptor;
    @Captor
    private ArgumentCaptor<List<StateWiseValue>> stateWiseValueCaptor;
    @Captor
    private ArgumentCaptor<List<YearlyReturnSummary>> yearlyReturnSummaryCaptor;
    @Captor
    private ArgumentCaptor<List<YearlyReturnSummaryValue>> yearlyReturnSummaryValueCaptor;

    // fields
    private BifurcationStateAmountSheetReader bifurcationStateAmountSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.BIFURCATION).expectedGroups(2).expectedRecords(14).expectedValueFields(348).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.ADJUSTED_AMOUNTS).expectedGroups(2).expectedRecords(47).expectedValueFields(1363).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.STATE_WISE).expectedGroups(2).expectedRecords(15).expectedValueFields(435).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.YEARLY_RETURN_SUMMARY).expectedGroups(0).expectedRecords(0).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.BIFURCATION).expectedGroups(2).expectedRecords(14).expectedValueFields(432).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.ADJUSTED_AMOUNTS).expectedGroups(2).expectedRecords(47).expectedValueFields(1692).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.STATE_WISE).expectedGroups(2).expectedRecords(23).expectedValueFields(828).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.YEARLY_RETURN_SUMMARY).expectedGroups(3).expectedRecords(40).expectedValueFields(111).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.BIFURCATION).expectedGroups(2).expectedRecords(14).expectedValueFields(348).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.ADJUSTED_AMOUNTS).expectedGroups(2).expectedRecords(47).expectedValueFields(1363).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.STATE_WISE).expectedGroups(2).expectedRecords(16).expectedValueFields(464).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.YEARLY_RETURN_SUMMARY).expectedGroups(3).expectedRecords(0).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.BIFURCATION).expectedGroups(2).expectedRecords(14).expectedValueFields(432).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.ADJUSTED_AMOUNTS).expectedGroups(2).expectedRecords(47).expectedValueFields(1692).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.STATE_WISE).expectedGroups(2).expectedRecords(43).expectedValueFields(1548).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.YEARLY_RETURN_SUMMARY).expectedGroups(3).expectedRecords(40).expectedValueFields(111).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.BIFURCATION).expectedGroups(2).expectedRecords(0).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.ADJUSTED_AMOUNTS).expectedGroups(2).expectedRecords(32).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.STATE_WISE).expectedGroups(2).expectedRecords(1).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.YEARLY_RETURN_SUMMARY).expectedGroups(3).expectedRecords(0).expectedValueFields(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        bifurcationStateAmountSheetReader = new BifurcationStateAmountSheetReader(
                fieldDateMonthYearSheetReader,
                fieldGroupRepository,
                bifurcationRepository,
                bifurcationValueRepository,
                adjustedAmountsRepository,
                adjustedAmountsValueRepository,
                stateWiseRepository,
                stateWiseValueRepository,
                yearlyReturnSummaryRepository,
                yearlyReturnSummaryValueRepository);
        when(fieldDateMonthYearRepository.save(any())).thenAnswer(m -> m.getArgument(0));
    }

    // these tests call the same method and perform the same validation
    // they are separated because they work on different data and for readability purpose
    // this also makes it simpler to track test failures for a specific report type
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
        bifurcationStateAmountSheetReader.readSheetAndStoreData(
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
        int expectedValueFields = params.getExpectedValueFields();

        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            verify(bifurcationRepository, times(1)).saveAll(bifurcationCaptor.capture());
            SheetTestUtils.validateNonNullFields(bifurcationCaptor.getValue(), expectedRecords, "id", "values");

            verify(bifurcationValueRepository, times(1)).saveAll(bifurcationValueCaptor.capture());
            assertEquals(expectedValueFields, bifurcationValueCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<BifurcationValue> revenue = bifurcationValueCaptor.getValue().stream().filter(c -> c.getPercentShareInAdjustedRevenue() != null).toList();
                List<BifurcationValue> purchases = bifurcationValueCaptor.getValue().stream().filter(c -> c.getPercentShareInAdjustedPurchasesExpenses() != null).toList();
                assertFalse(revenue.isEmpty());
                assertFalse(purchases.isEmpty());
                SheetTestUtils.validateNonNullBifurcatedValueFields(revenue, revenue.size(), true,
                        "id", "percentShareInAdjustedPurchasesExpenses", "percentShareInAdjustedPurchasesExpensesNumeric");
                SheetTestUtils.validateNonNullBifurcatedValueFields(purchases, purchases.size(), false,
                        "id", "percentShareInAdjustedRevenue", "percentShareInAdjustedRevenueNumeric");
            }
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            verify(adjustedAmountsRepository, times(1)).saveAll(adjustedAmountsCaptor.capture());
            SheetTestUtils.validateNonNullFields(adjustedAmountsCaptor.getValue(), expectedRecords, "id", "values");

            verify(adjustedAmountsValueRepository, times(1)).saveAll(adjustedAmountsValueCaptor.capture());
            assertEquals(expectedValueFields, adjustedAmountsValueCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<AdjustedAmountsValue> revenue = adjustedAmountsValueCaptor.getValue().stream()
                        .filter(c -> StringUtils.isNotEmpty(c.getPercentShareInAdjustedRevenue())).toList();
                List<AdjustedAmountsValue> purchases = adjustedAmountsValueCaptor.getValue().stream()
                        .filter(c -> StringUtils.isNotEmpty(c.getPercentShareInAdjustedPurchasesExpenses())).toList();
                assertFalse(revenue.isEmpty());
                assertFalse(purchases.isEmpty());
                SheetTestUtils.validateNonNullBifurcatedValueFields(revenue, revenue.size(), true,
                        "id", "percentShareInAdjustedPurchasesExpenses", "percentShareInAdjustedPurchasesExpensesNumeric");
                SheetTestUtils.validateNonNullBifurcatedValueFields(purchases, purchases.size(), false,
                        "id", "percentShareInAdjustedRevenue", "percentShareInAdjustedRevenueNumeric");
            }
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            verify(stateWiseRepository, times(1)).saveAll(stateWiseCaptor.capture());
            assertEquals(expectedRecords, stateWiseCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<StateWise> state = stateWiseCaptor.getValue().stream().filter(c -> c.getStateCode() != null).toList();
                List<StateWise> noState = stateWiseCaptor.getValue().stream().filter(c -> c.getStateCode() == null).toList();
                assertFalse(state.isEmpty());
                assertFalse(noState.isEmpty());
                SheetTestUtils.validateNonNullFields(state, state.size(), "id", "values");
                SheetTestUtils.validateNonNullFields(noState, noState.size(), "id", "values", "stateCode");
            }

            verify(stateWiseValueRepository, times(1)).saveAll(stateWiseValueCaptor.capture());
            assertEquals(expectedValueFields, stateWiseValueCaptor.getValue().size());

            if (!params.isInvalidData()) {
                List<StateWiseValue> revenue = stateWiseValueCaptor.getValue().stream()
                        .filter(c -> StringUtils.isNotEmpty(c.getPercentShareInAdjustedRevenue())).toList();
                List<StateWiseValue> purchases = stateWiseValueCaptor.getValue().stream()
                        .filter(c -> StringUtils.isNotEmpty(c.getPercentShareInAdjustedPurchasesExpenses())).toList();
                assertFalse(revenue.isEmpty());
                assertFalse(purchases.isEmpty());
                SheetTestUtils.validateNonNullBifurcatedValueFields(revenue, revenue.size(), true,
                        "id", "percentShareInAdjustedPurchasesExpenses", "percentShareInAdjustedPurchasesExpensesNumeric");
                SheetTestUtils.validateNonNullBifurcatedValueFields(purchases, purchases.size(), false,
                        "id", "percentShareInAdjustedRevenue", "percentShareInAdjustedRevenueNumeric");
            }
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            verify(yearlyReturnSummaryRepository, times(1)).saveAll(yearlyReturnSummaryCaptor.capture());
            SheetTestUtils.validateNonNullFields(yearlyReturnSummaryCaptor.getValue(), expectedRecords, "id", "values");

            verify(yearlyReturnSummaryValueRepository, times(1)).saveAll(yearlyReturnSummaryValueCaptor.capture());
            SheetTestUtils.validateNonNullSheetDateValueFields(yearlyReturnSummaryValueCaptor.getValue(), expectedValueFields,
                    "id", "amountNumeric", "percentShareInSalesDuringPeriodNumeric", "totalGSTNumeric");
            for (YearlyReturnSummaryValue value : yearlyReturnSummaryValueCaptor.getValue()) {
                if (SheetTestUtils.expectNumericValue(value.getAmount())) {
                    assertNotNull(value.getAmountNumeric(), "field value: " + value.getAmount());
                }
                if (SheetTestUtils.expectNumericValue(value.getPercentShareInSalesDuringPeriod())) {
                    assertNotNull(value.getPercentShareInSalesDuringPeriodNumeric(), "field value: " + value.getPercentShareInSalesDuringPeriod());
                }
                if (SheetTestUtils.expectNumericValue(value.getTotalGST())) {
                    assertNotNull(value.getTotalGSTNumeric(), "field value: " + value.getTotalGST());
                }
            }
        }
    }
}

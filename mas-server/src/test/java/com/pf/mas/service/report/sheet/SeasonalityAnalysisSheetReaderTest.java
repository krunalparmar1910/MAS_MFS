package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.SeasonalityAnalysis;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.SeasonalityAnalysisRepository;
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
class SeasonalityAnalysisSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private SeasonalityAnalysisRepository seasonalityAnalysisRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<SeasonalityAnalysis>> seasonalityAnalysisCaptor;

    // fields
    private SeasonalityAnalysisSheetReader seasonalityAnalysisSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SEASONALITY_ANALYSIS).expectedGroups(2).expectedRecords(60).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SEASONALITY_ANALYSIS).expectedGroups(2).expectedRecords(12).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SEASONALITY_ANALYSIS).expectedGroups(2).expectedRecords(44).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SEASONALITY_ANALYSIS).expectedGroups(2).expectedRecords(0).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.SEASONALITY_ANALYSIS).expectedGroups(2).expectedRecords(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        seasonalityAnalysisSheetReader = new SeasonalityAnalysisSheetReader(fieldDateMonthYearSheetReader, fieldGroupRepository, seasonalityAnalysisRepository);
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
        mockIfExpectedRecords(params.getExpectedRecords());
        seasonalityAnalysisSheetReader.readSheetAndStoreData(
                params.getSheetTypeName(), params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params.getExpectedRecords());
    }

    private void mockIfExpectedRecords(int expectedRecords) {
        if (expectedRecords > 0) {
            when(fieldDateMonthYearRepository.save(any())).thenAnswer(m -> m.getArgument(0));
        }
    }

    private void validateGroups(int expectedGroups) {
        verify(fieldGroupRepository, times(1)).saveAll(fieldGroupCaptor.capture());
        SheetTestUtils.validateFieldGroups(fieldGroupCaptor.getValue(), expectedGroups);
    }

    private void validateRecords(int expectedRecords) {
        verify(seasonalityAnalysisRepository, times(1)).saveAll(seasonalityAnalysisCaptor.capture());
        SheetTestUtils.validateNonNullFields(seasonalityAnalysisCaptor.getValue(), expectedRecords, "id", "valueNumeric");

        List<SeasonalityAnalysis> numericValueFields = seasonalityAnalysisCaptor.getValue().stream()
                .filter(seasonalityAnalysis -> SheetTestUtils.expectNumericValue(seasonalityAnalysis.getValue())).toList();
        SheetTestUtils.validateNonNullFields(numericValueFields, numericValueFields.size(), "id");
    }
}
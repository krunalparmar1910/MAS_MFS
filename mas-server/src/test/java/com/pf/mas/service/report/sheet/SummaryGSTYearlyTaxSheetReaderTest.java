package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.GSTR3B;
import com.pf.mas.model.entity.sheet.GSTR3BValue;
import com.pf.mas.model.entity.sheet.Summary;
import com.pf.mas.model.entity.sheet.SummaryAnalysis;
import com.pf.mas.model.entity.sheet.SummaryAnalysisValue;
import com.pf.mas.model.entity.sheet.SummaryValue;
import com.pf.mas.model.entity.sheet.Tax;
import com.pf.mas.model.entity.sheet.TaxValue;
import com.pf.mas.model.entity.sheet.YearlyTax;
import com.pf.mas.model.entity.sheet.YearlyTaxValue;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.GSTR3BRepository;
import com.pf.mas.repository.sheet.GSTR3BValueRepository;
import com.pf.mas.repository.sheet.SummaryAnalysisRepository;
import com.pf.mas.repository.sheet.SummaryAnalysisValueRepository;
import com.pf.mas.repository.sheet.SummaryRepository;
import com.pf.mas.repository.sheet.SummaryValueRepository;
import com.pf.mas.repository.sheet.TaxRepository;
import com.pf.mas.repository.sheet.TaxValueRepository;
import com.pf.mas.repository.sheet.YearlyTaxRepository;
import com.pf.mas.repository.sheet.YearlyTaxValueRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummaryGSTYearlyTaxSheetReaderTest {
    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private SummaryAnalysisRepository summaryAnalysisRepository;
    @Mock
    private SummaryAnalysisValueRepository summaryAnalysisValueRepository;
    @Mock
    private SummaryRepository summaryRepository;
    @Mock
    private SummaryValueRepository summaryValueRepository;
    @Mock
    private GSTR3BRepository gstr3BRepository;
    @Mock
    private GSTR3BValueRepository gstr3BValueRepository;
    @Mock
    private TaxRepository taxRepository;
    @Mock
    private TaxValueRepository taxValueRepository;
    @Mock
    private YearlyTaxRepository yearlyTaxRepository;
    @Mock
    private YearlyTaxValueRepository yearlyTaxValueRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<SummaryAnalysis>> summaryAnalysisCaptor;
    @Captor
    private ArgumentCaptor<List<SummaryAnalysisValue>> summaryAnalysisValueCaptor;
    @Captor
    private ArgumentCaptor<List<Summary>> summaryCaptor;
    @Captor
    private ArgumentCaptor<List<SummaryValue>> summaryValueCaptor;
    @Captor
    private ArgumentCaptor<List<GSTR3B>> gstr3bCaptor;
    @Captor
    private ArgumentCaptor<List<GSTR3BValue>> gstr3bValueCaptor;
    @Captor
    private ArgumentCaptor<List<Tax>> taxCaptor;
    @Captor
    private ArgumentCaptor<List<TaxValue>> taxValueCaptor;
    @Captor
    private ArgumentCaptor<List<YearlyTax>> yearlyTaxCaptor;
    @Captor
    private ArgumentCaptor<List<YearlyTaxValue>> yearlyTaxValueCaptor;

    // fields
    private SummaryGSTYearlyTaxSheetReader summaryGSTYearlyTaxSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUMMARY_ANALYSIS).expectedGroups(9).expectedRecords(90).expectedValueFields(305).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUMMARY).expectedGroups(5).expectedRecords(44).expectedValueFields(332).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.GSTR_3B).expectedGroups(3).expectedRecords(14).expectedValueFields(350).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.TAX).expectedGroups(2).expectedRecords(38).expectedValueFields(950).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.YEARLY_TAX).expectedGroups(0).expectedRecords(0).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUMMARY_ANALYSIS).expectedGroups(9).expectedRecords(55).expectedValueFields(177).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUMMARY).expectedGroups(5).expectedRecords(44).expectedValueFields(422).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.GSTR_3B).expectedGroups(4).expectedRecords(20).expectedValueFields(640).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.TAX).expectedGroups(2).expectedRecords(38).expectedValueFields(1216).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.YEARLY_TAX).expectedGroups(2).expectedRecords(33).expectedValueFields(99).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUMMARY_ANALYSIS).expectedGroups(7).expectedRecords(58).expectedValueFields(197).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.SUMMARY).expectedGroups(5).expectedRecords(44).expectedValueFields(332).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.GSTR_3B).expectedGroups(3).expectedRecords(14).expectedValueFields(350).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.TAX).expectedGroups(2).expectedRecords(38).expectedValueFields(950).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.YEARLY_TAX).expectedGroups(2).expectedRecords(0).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUMMARY_ANALYSIS).expectedGroups(7).expectedRecords(56).expectedValueFields(186).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.SUMMARY).expectedGroups(5).expectedRecords(44).expectedValueFields(422).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.GSTR_3B).expectedGroups(4).expectedRecords(20).expectedValueFields(640).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.TAX).expectedGroups(2).expectedRecords(38).expectedValueFields(1216).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.YEARLY_TAX).expectedGroups(2).expectedRecords(33).expectedValueFields(99).build())
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.SUMMARY_ANALYSIS).expectedGroups(8).expectedRecords(1).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.SUMMARY).expectedGroups(5).expectedRecords(7).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.GSTR_3B).expectedGroups(3).expectedRecords(5).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.TAX).expectedGroups(2).expectedRecords(23).expectedValueFields(0).build()),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.YEARLY_TAX).expectedGroups(2).expectedRecords(0).expectedValueFields(0).build())
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        summaryGSTYearlyTaxSheetReader = new SummaryGSTYearlyTaxSheetReader(
                fieldDateMonthYearSheetReader,
                fieldGroupRepository,
                summaryAnalysisRepository,
                summaryAnalysisValueRepository,
                summaryRepository,
                summaryValueRepository,
                gstr3BRepository,
                gstr3BValueRepository,
                taxRepository,
                taxValueRepository,
                yearlyTaxRepository,
                yearlyTaxValueRepository);
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
        summaryGSTYearlyTaxSheetReader.readSheetAndStoreData(
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
        int expectedTotalRecords = params.getExpectedValueFields();

        if (SheetTypeName.SUMMARY_ANALYSIS == sheetTypeName) {
            verify(summaryAnalysisRepository, times(1)).saveAll(summaryAnalysisCaptor.capture());
            SheetTestUtils.validateNonNullFields(summaryAnalysisCaptor.getValue(), expectedRecords, "id", "values");

            verify(summaryAnalysisValueRepository, times(1)).saveAll(summaryAnalysisValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(summaryAnalysisValueCaptor.getValue(), expectedTotalRecords, "id", "fieldValueNumeric");
        } else if (SheetTypeName.SUMMARY == sheetTypeName) {
            verify(summaryRepository, times(1)).saveAll(summaryCaptor.capture());
            SheetTestUtils.validateNonNullFields(summaryCaptor.getValue(), expectedRecords, "id", "values");

            verify(summaryValueRepository, times(1)).saveAll(summaryValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(summaryValueCaptor.getValue(), expectedTotalRecords, "id", "quarter", "fieldValueNumeric");

            if (!params.isInvalidData()) {
                List<SummaryValue> quarterlySummary = summaryValueCaptor.getValue().stream().filter(value -> value.getQuarter() != null).toList();
                assertFalse(quarterlySummary.isEmpty());
                SheetTestUtils.validateNonNullValueFields(quarterlySummary, quarterlySummary.size(), "id", "quarter", "fieldValueNumeric");
            }
        } else if (SheetTypeName.GSTR_3B == sheetTypeName) {
            verify(gstr3BRepository, times(1)).saveAll(gstr3bCaptor.capture());
            SheetTestUtils.validateNonNullFields(gstr3bCaptor.getValue(), expectedRecords, "id", "values");

            verify(gstr3BValueRepository, times(1)).saveAll(gstr3bValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(gstr3bValueCaptor.getValue(), expectedTotalRecords, "id", "fieldValueNumeric");
        } else if (SheetTypeName.TAX == sheetTypeName) {
            verify(taxRepository, times(1)).saveAll(taxCaptor.capture());
            SheetTestUtils.validateNonNullFields(taxCaptor.getValue(), expectedRecords, "id", "values");

            verify(taxValueRepository, times(1)).saveAll(taxValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(taxValueCaptor.getValue(), expectedTotalRecords, "id", "fieldValueNumeric");
        } else if (SheetTypeName.YEARLY_TAX == sheetTypeName) {
            verify(yearlyTaxRepository, times(1)).saveAll(yearlyTaxCaptor.capture());
            SheetTestUtils.validateNonNullFields(yearlyTaxCaptor.getValue(), expectedRecords, "id", "values");

            verify(yearlyTaxValueRepository, times(1)).saveAll(yearlyTaxValueCaptor.capture());
            SheetTestUtils.validateNonNullValueFields(yearlyTaxValueCaptor.getValue(), expectedTotalRecords, "id", "fieldValueNumeric");
        }
    }
}

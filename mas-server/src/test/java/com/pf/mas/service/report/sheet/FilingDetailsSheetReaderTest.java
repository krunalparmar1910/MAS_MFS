package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.FilingDetail;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.FilingDetailRepository;
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
class FilingDetailsSheetReaderTest {
    //mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private FieldDateMonthYearRepository fieldDateMonthYearRepository;
    @Mock
    private FilingDetailRepository filingDetailRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<FilingDetail>> filingDetailsCaptor;

    // fields
    private FilingDetailsSheetReader filingDetailsSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(1).expectedRecords(25).build(), true),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(2).expectedRecords(64).build(), false)
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(2).expectedRecords(50).build(), true),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(6).expectedRecords(192).build(), false)
        );
    }

    private static Stream<Arguments> provideDetailsForReadInvalidReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> invalidSheetMap = sheetProvider.getSheetMapInvalid();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder().invalidData(true)
                        .sheetMap(invalidSheetMap).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(3).expectedRecords(0).build(), false)
        );
    }

    @BeforeEach
    public void setup() {
        FieldDateMonthYearSheetReader fieldDateMonthYearSheetReader = new DefaultFieldDateMonthYearSheetReader(fieldDateMonthYearRepository);
        filingDetailsSheetReader = new FilingDetailsSheetReader(fieldDateMonthYearSheetReader, fieldGroupRepository, filingDetailRepository);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadSingleReport")
    void testReadSingleReport(SheetTestUtils.SheetReaderTestParams params, boolean returnTypePresent) throws MasReportSheetReaderException {
        readSheetAndStoreData(params, returnTypePresent);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadConsolidatedReport")
    void testReadConsolidatedReport(SheetTestUtils.SheetReaderTestParams params, boolean returnTypePresent) throws MasReportSheetReaderException {
        readSheetAndStoreData(params, returnTypePresent);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadInvalidReport")
    void testReadInvalidReport(SheetTestUtils.SheetReaderTestParams params, boolean returnTypePresent) throws MasReportSheetReaderException {
        readSheetAndStoreData(params, returnTypePresent);
    }

    private void readSheetAndStoreData(SheetTestUtils.SheetReaderTestParams params, boolean returnTypePresent) throws MasReportSheetReaderException {
        mockIfExpectedRecords(params.getExpectedRecords());
        filingDetailsSheetReader.readSheetAndStoreData(
                params.getSheetTypeName(), params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params, returnTypePresent);
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

    private void validateRecords(SheetTestUtils.SheetReaderTestParams params, boolean returnTypePresent) {
        verify(filingDetailRepository, times(1)).saveAll(filingDetailsCaptor.capture());

        List<FilingDetail> filingDetails = filingDetailsCaptor.getValue();
        List<String> fieldsToIgnore = List.of("id", "fieldDateMonthYear", "dateOfFiling", "delayedDays", returnTypePresent ? "" : "returnType");
        SheetTestUtils.validateNonNullFields(filingDetailsCaptor.getValue(), params.getExpectedRecords(), false, fieldsToIgnore.toArray(new String[0]));

        if (!params.isInvalidData()) {
            List<FilingDetail> delayedDays = filingDetails.stream().filter(filingDetail -> filingDetail.getDelayedDays() != null).toList();
            List<FilingDetail> dateOfFilingPresent = filingDetails.stream().filter(filingDetail -> filingDetail.getDateOfFiling() != null).toList();
            assertFalse(delayedDays.isEmpty());
            assertFalse(dateOfFilingPresent.isEmpty());
        }
    }
}

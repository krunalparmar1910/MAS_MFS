package com.pf.mas.service.report.sheet.profile;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import com.pf.mas.repository.FieldGroupRepository;
import com.pf.mas.repository.sheet.ProfileDetailRepository;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultProfileSheetReaderTest {
    // constants
    private static final List<String> NULL_FIELDS_SHEET_1 = List.of(
            "id",
            "fieldDateMonthYear",
            "createdTimestamp",
            "natureOfCoreBusinessActivity",
            "stateJurisdiction",
            "stateJurisdictionCode",
            "centerJurisdiction",
            "centerJurisdictionCode",
            "latitude",
            "longitude",
            "dateOfCancellation",
            "lastUpdatedDate"
    );
    private static final List<String> NULL_FIELDS_SHEET_2 = List.of(
            "id",
            "fieldDateMonthYear",
            "createdTimestamp",
            "latitude",
            "longitude",
            "dateOfCancellation"
    );

    // mocks
    @Mock
    private FieldGroupRepository fieldGroupRepository;
    @Mock
    private ProfileDetailRepository profileDetailRepository;
    @Mock
    private SheetType sheetType;
    @Mock
    private ClientOrder clientOrder;

    // captors
    @Captor
    private ArgumentCaptor<List<FieldGroup>> fieldGroupCaptor;
    @Captor
    private ArgumentCaptor<List<ProfileDetail>> profileDetailCaptor;

    // fields
    private ProfileSheetReader profileSheetReader;

    private static Stream<Arguments> provideDetailsForReadSingleReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapSingle1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapSingle2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(1).expectedRecords(1).build(), NULL_FIELDS_SHEET_1),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(1).expectedRecords(1).build(), NULL_FIELDS_SHEET_2)
        );
    }

    private static Stream<Arguments> provideDetailsForReadConsolidatedReport() {
        SheetProvider sheetProvider = SheetProvider.getInstance();
        Map<SheetTypeName, Sheet> sheetMap1 = sheetProvider.getSheetMapConsolidated1();
        Map<SheetTypeName, Sheet> sheetMap2 = sheetProvider.getSheetMapConsolidated2();

        return Stream.of(
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap1).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(2).expectedRecords(2).build(), NULL_FIELDS_SHEET_1),
                Arguments.of(SheetTestUtils.SheetReaderTestParams.builder()
                        .sheetMap(sheetMap2).sheetTypeName(SheetTypeName.PROFILE_FILING_TABLE).expectedGroups(3).expectedRecords(3).build(), NULL_FIELDS_SHEET_2)
        );
    }

    @BeforeEach
    public void setup() {
        profileSheetReader = new DefaultProfileSheetReader(fieldGroupRepository, profileDetailRepository);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadSingleReport")
    void testReadSingleReport(SheetTestUtils.SheetReaderTestParams params, List<String> nullFields) throws MasReportSheetReaderException {
        profileSheetReader.readSheetAndStoreData(params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params.getExpectedRecords(), nullFields);
    }

    @ParameterizedTest
    @MethodSource("provideDetailsForReadConsolidatedReport")
    void testReadConsolidatedReport(SheetTestUtils.SheetReaderTestParams params, List<String> nullFields) throws MasReportSheetReaderException {
        profileSheetReader.readSheetAndStoreData(params.getSheetMap().get(params.getSheetTypeName()), sheetType, clientOrder);
        validateGroups(params.getExpectedGroups());
        validateRecords(params.getExpectedRecords(), nullFields);
    }

    private void validateGroups(int expectedGroups) {
        verify(fieldGroupRepository, times(1)).saveAll(fieldGroupCaptor.capture());
        SheetTestUtils.validateFieldGroups(fieldGroupCaptor.getValue(), expectedGroups);
    }

    private void validateRecords(int expectedRecords, List<String> nullFields) {
        verify(profileDetailRepository, times(1)).saveAll(profileDetailCaptor.capture());
        SheetTestUtils.validateNonNullFields(profileDetailCaptor.getValue(), expectedRecords, false, nullFields.toArray(new String[0]));
    }
}

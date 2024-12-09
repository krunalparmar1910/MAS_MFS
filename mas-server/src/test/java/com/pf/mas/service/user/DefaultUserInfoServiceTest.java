package com.pf.mas.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.GSTReportProfileDetail;
import com.pf.mas.dto.user.GetGST3BReportRequestHistoryResponseDTO;
import com.pf.mas.dto.user.GetGST3BReportRequestResponseInfo;
import com.pf.mas.exception.MasJSONException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.ClientOrderReportDetails;
import com.pf.mas.model.entity.user.UserInfo;
import com.pf.mas.repository.user.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserInfoServiceTest {
    // constants
    private static final String TEST_USER = "test_user";
    private static final String TEST_COMPANY = "test_company";
    private static final GSTReport GST_REPORT = GSTReport.builder()
            .gstReportProfileDetails(List.of(GSTReportProfileDetail.builder().tradeName(TEST_COMPANY).build()))
            .build();
    private static final GetGST3BReportRequestHistoryResponseDTO TEST_HISTORY_DTO = GetGST3BReportRequestHistoryResponseDTO.builder()
            .getGST3BReportRequestResponseInfoList(List.of(
                    GetGST3BReportRequestResponseInfo.builder().clientOrderIds(List.of("1,2")).entityId("1001").companyName(TEST_COMPANY).build(),
                    GetGST3BReportRequestResponseInfo.builder().clientOrderIds(null).entityId("2002").companyName(TEST_COMPANY).build(),
                    GetGST3BReportRequestResponseInfo.builder().clientOrderIds(List.of("3")).entityId(null).companyName(TEST_COMPANY).build(),
                    GetGST3BReportRequestResponseInfo.builder().clientOrderIds(List.of("4")).entityId("4004").companyName(TEST_COMPANY).build(),
                    GetGST3BReportRequestResponseInfo.builder().clientOrderIds(List.of("5,6,7")).entityId("5005").companyName(TEST_COMPANY).build()
            ))
            .build();
    private static final ClientOrder CLIENT_ORDER;

    static {
        CLIENT_ORDER = new ClientOrder();
        ClientOrderReportDetails clientOrderReportDetails = new ClientOrderReportDetails();
        clientOrderReportDetails.setReportCompanyName(TEST_COMPANY);
        CLIENT_ORDER.setClientOrderReportDetails(clientOrderReportDetails);
    }

    // mocks
    @Mock
    private UserInfoRepository userInfoRepository;
    @Captor
    private ArgumentCaptor<UserInfo> userInfoArgumentCaptor;

    // fields
    private UserInfoService userInfoService;

    private static Stream<Arguments> provideInvalidCasesForUpdate() {
        return Stream.of(
                Arguments.of(GetGST3BReportRequestHistoryResponseDTO.builder()
                        .getGST3BReportRequestResponseInfoList(List.of(GetGST3BReportRequestResponseInfo.builder()
                                .entityId(null)
                                .clientOrderIds(null)
                                .build()))
                        .build(), true),
                Arguments.of(GetGST3BReportRequestHistoryResponseDTO.builder()
                        .getGST3BReportRequestResponseInfoList(List.of(GetGST3BReportRequestResponseInfo.builder()
                                .entityId(null)
                                .clientOrderIds(null)
                                .build()))
                        .build(), false)
        );
    }

    private static Stream<Arguments> provideCaseForUpdateWithNoUserInfoPresent() {
        return Stream.of(
                Arguments.of("abc", List.of("d", "e")),
                Arguments.of(null, List.of("d", "e")),
                Arguments.of("abc", List.of())
        );
    }

    @BeforeEach
    public void setup() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        userInfoService = new DefaultUserInfoService(objectMapper, userInfoRepository);

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(TEST_USER);
        userInfo.setGetGstReportRequestHistoryJSON(new ObjectMapper().writeValueAsString(TEST_HISTORY_DTO));
        when(userInfoRepository.findByUsername(TEST_USER)).thenReturn(userInfo);
    }

    @Test
    void testGetGST3BReportRequestHistory() throws MasJSONException {
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService.getGST3BReportRequestHistory(TEST_USER);
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);
        assertEquals(TEST_HISTORY_DTO, getGST3BReportRequestHistoryResponseDTO);
    }

    @Test
    void testGetGST3BReportRequestHistoryNoUserInfoPresent() throws MasJSONException {
        mockNoUser();
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService.getGST3BReportRequestHistory(TEST_USER);
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);
        assertEquals(GetGST3BReportRequestHistoryResponseDTO.builder().build(), getGST3BReportRequestHistoryResponseDTO);
    }

    @Test
    void testUpdateGST3BReportRequestHistory() throws MasJSONException, JsonProcessingException {
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, "abc", List.of("d", "e"), GST_REPORT.getGstReportProfileDetails());
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);

        List<GetGST3BReportRequestResponseInfo> updatedList = new ArrayList<>(TEST_HISTORY_DTO.getGetGST3BReportRequestResponseInfoList().stream().toList());
        updatedList.remove(0);
        updatedList.add(GetGST3BReportRequestResponseInfo.builder().entityId("abc").clientOrderIds(List.of("d", "e")).companyName(TEST_COMPANY).build());
        GetGST3BReportRequestHistoryResponseDTO updatedDTO = GetGST3BReportRequestHistoryResponseDTO.builder().getGST3BReportRequestResponseInfoList(updatedList).build();
        assertEquals(updatedDTO, getGST3BReportRequestHistoryResponseDTO);
        verifyUserInfoSaveCalled(updatedDTO, 1);
    }

    @Test
    void testUpdateGST3BReportRequestHistoryRepeatedCalls() throws MasJSONException, JsonProcessingException {
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, "abc", List.of("d", "e"), GST_REPORT.getGstReportProfileDetails());
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);

        List<GetGST3BReportRequestResponseInfo> updatedList = new ArrayList<>(TEST_HISTORY_DTO.getGetGST3BReportRequestResponseInfoList().stream().toList());
        updatedList.remove(0);
        updatedList.add(GetGST3BReportRequestResponseInfo.builder().entityId("abc").clientOrderIds(List.of("d", "e")).companyName(TEST_COMPANY).build());
        GetGST3BReportRequestHistoryResponseDTO updatedDTO = GetGST3BReportRequestHistoryResponseDTO.builder().getGST3BReportRequestResponseInfoList(updatedList).build();
        assertEquals(updatedDTO, getGST3BReportRequestHistoryResponseDTO);
        verifyUserInfoSaveCalled(updatedDTO, 1);

        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO2 = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, "abc", List.of("d", "e"), GST_REPORT.getGstReportProfileDetails());
        assertNotNull(getGST3BReportRequestHistoryResponseDTO2);
        assertEquals(updatedDTO, getGST3BReportRequestHistoryResponseDTO2);
        verifyUserInfoSaveCalled(updatedDTO, 2);
    }

    @Test
    void testUpdateGST3BReportRequestHistoryWithExistingRequest() throws MasJSONException, JsonProcessingException {
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, "1001", List.of("1", "2"), GST_REPORT.getGstReportProfileDetails());
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);

        List<GetGST3BReportRequestResponseInfo> updatedList = new ArrayList<>(TEST_HISTORY_DTO.getGetGST3BReportRequestResponseInfoList().stream().toList());
        updatedList.remove(0);
        updatedList.add(GetGST3BReportRequestResponseInfo.builder().entityId("1001").clientOrderIds(List.of("1", "2")).companyName(TEST_COMPANY).build());
        GetGST3BReportRequestHistoryResponseDTO updatedDTO = GetGST3BReportRequestHistoryResponseDTO.builder().getGST3BReportRequestResponseInfoList(updatedList).build();
        assertEquals(updatedDTO, getGST3BReportRequestHistoryResponseDTO);
        verifyUserInfoSaveCalled(updatedDTO, 1);
    }

    @ParameterizedTest
    @MethodSource("provideCaseForUpdateWithNoUserInfoPresent")
    void testUpdateGST3BReportRequestHistoryNoUserInfoPresent(String entityId, List<String> clientOrderIds) throws MasJSONException, JsonProcessingException {
        mockNoUser();
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, entityId, clientOrderIds, GST_REPORT.getGstReportProfileDetails());
        assertNotNull(getGST3BReportRequestHistoryResponseDTO);

        GetGST3BReportRequestHistoryResponseDTO expectedDTO = GetGST3BReportRequestHistoryResponseDTO.builder()
                .getGST3BReportRequestResponseInfoList(List.of(GetGST3BReportRequestResponseInfo.builder()
                        .entityId(entityId)
                        .clientOrderIds(clientOrderIds)
                        .companyName(TEST_COMPANY)
                        .build()))
                .build();
        assertEquals(expectedDTO, getGST3BReportRequestHistoryResponseDTO);
        verifyUserInfoSaveCalled(expectedDTO, 3);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCasesForUpdate")
    void testUpdateGST3BReportRequestHistoryInvalidCases(GetGST3BReportRequestHistoryResponseDTO expectedDTO, boolean noUser) throws MasJSONException, JsonProcessingException {
        if (noUser) {
            mockNoUser();
        }
        String entityId = expectedDTO.getGetGST3BReportRequestResponseInfoList().get(0).getEntityId();
        List<String> clientOrderIds = expectedDTO.getGetGST3BReportRequestResponseInfoList().get(0).getClientOrderIds();
        GetGST3BReportRequestHistoryResponseDTO responseDTO = userInfoService
                .updateGST3BReportRequestHistory(TEST_USER, entityId, clientOrderIds, null);
        assertNotNull(responseDTO);
        if (noUser) {
            assertEquals(expectedDTO, responseDTO);
            verifyUserInfoSaveCalled(responseDTO, 3);
        } else {
            List<GetGST3BReportRequestResponseInfo> updatedList = new ArrayList<>(TEST_HISTORY_DTO.getGetGST3BReportRequestResponseInfoList().stream().toList());
            updatedList.remove(0);
            updatedList.add(GetGST3BReportRequestResponseInfo.builder().entityId(entityId).clientOrderIds(clientOrderIds).build());
            GetGST3BReportRequestHistoryResponseDTO updatedDTO = GetGST3BReportRequestHistoryResponseDTO.builder().getGST3BReportRequestResponseInfoList(updatedList).build();
            assertEquals(updatedDTO, responseDTO);
            verifyUserInfoSaveCalled(updatedDTO, 1);
        }
    }

    private void mockNoUser() {
        when(userInfoRepository.findByUsername(TEST_USER)).thenReturn(null);
        when(userInfoRepository.save(any())).thenAnswer(m -> m.getArgument(0));
    }

    private void verifyUserInfoSaveCalled(GetGST3BReportRequestHistoryResponseDTO expectedDTO, int times) throws JsonProcessingException {
        verify(userInfoRepository, times(times)).save(userInfoArgumentCaptor.capture());
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(TEST_USER);
        userInfo.setGetGstReportRequestHistoryJSON(new ObjectMapper().writeValueAsString(expectedDTO));
        assertEquals(userInfo, userInfoArgumentCaptor.getAllValues().get(times - 1));
    }
}

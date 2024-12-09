package com.pf.mas.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.dto.report.GSTReportProfileDetail;
import com.pf.mas.dto.user.GetGST3BReportRequestHistoryResponseDTO;
import com.pf.mas.dto.user.GetGST3BReportRequestResponseInfo;
import com.pf.mas.exception.MasJSONException;
import com.pf.mas.model.entity.user.UserInfo;
import com.pf.mas.repository.user.UserInfoRepository;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class DefaultUserInfoService implements UserInfoService {
    private static final int MAX_REQUEST_HISTORY_SIZE = 5;
    private static final String EMPTY_JSON_STRING = "{}";
    private final ObjectMapper objectMapper;
    private final UserInfoRepository userInfoRepository;

    public DefaultUserInfoService(
            ObjectMapper objectMapper,
            UserInfoRepository userInfoRepository) {
        this.objectMapper = objectMapper;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistory(String username) throws MasJSONException {
        UserInfo userInfo = getUserInfo(username);
        String getGstReportRequestHistoryJSON = userInfo.getGetGstReportRequestHistoryJSON();
        try {
            return objectMapper.readValue(StringUtils.defaultIfBlank(getGstReportRequestHistoryJSON, EMPTY_JSON_STRING), GetGST3BReportRequestHistoryResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new MasJSONException(e);
        }
    }

    @Override
    public GetGST3BReportRequestHistoryResponseDTO updateGST3BReportRequestHistory(
            String username, @Nullable String entityId, @Nullable List<String> clientOrderIds, List<GSTReportProfileDetail> gstReportProfileDetails) throws MasJSONException {
        log.debug("Updating gst3b report request history for username {} with entity id {} and client order ids {}", username, entityId, clientOrderIds);
        GetGST3BReportRequestHistoryResponseDTO getGST3BReportRequestHistoryResponseDTO = getGST3BReportRequestHistory(username);
        if (getGST3BReportRequestHistoryResponseDTO == null || getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList() == null) {
            log.debug("History does not exist for username {} with entity id {} and client order ids {}", username, entityId, clientOrderIds);
            getGST3BReportRequestHistoryResponseDTO = GetGST3BReportRequestHistoryResponseDTO.builder()
                    .getGST3BReportRequestResponseInfoList(new ArrayList<>(MAX_REQUEST_HISTORY_SIZE))
                    .build();
        }

        // find existing history
        int index = 0;
        GetGST3BReportRequestResponseInfo getGST3BReportRequestResponseInfo = null;
        for (; index < getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().size(); ++index) {
            GetGST3BReportRequestResponseInfo info = getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().get(index);
            if (StringUtils.equals(entityId, info.getEntityId()) && isEqualList(clientOrderIds, info.getClientOrderIds())) {
                getGST3BReportRequestResponseInfo = info;
                break;
            }
        }

        // if new record, then add
        // else remove existing record and place it at the end
        if (getGST3BReportRequestResponseInfo == null) {
            getGST3BReportRequestResponseInfo = GetGST3BReportRequestResponseInfo.builder()
                    .entityId(entityId)
                    .clientOrderIds(clientOrderIds)
                    .companyName(getCompanyName(gstReportProfileDetails))
                    .build();
            getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().add(getGST3BReportRequestResponseInfo);
            if (getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().size() > MAX_REQUEST_HISTORY_SIZE) {
                getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().remove(0);
            }
        } else {
            getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().remove(index);
            getGST3BReportRequestHistoryResponseDTO.getGetGST3BReportRequestResponseInfoList().add(getGST3BReportRequestResponseInfo);
        }

        // save updated user info
        try {
            String getGstReportRequestHistoryJSON = objectMapper.writeValueAsString(getGST3BReportRequestHistoryResponseDTO);
            UserInfo userInfo = getUserInfo(username);
            userInfo.setGetGstReportRequestHistoryJSON(getGstReportRequestHistoryJSON);
            userInfoRepository.save(userInfo);
            log.debug("Updated history for username {} with entity id {} and client order ids {}", username, entityId, clientOrderIds);
        } catch (JsonProcessingException e) {
            throw new MasJSONException(e);
        } catch (Exception e) {
            throw new MasRuntimeException(e);
        }

        return getGST3BReportRequestHistoryResponseDTO;
    }

    private String getCompanyName(List<GSTReportProfileDetail> gstReportProfileDetails) {
        return CollectionUtils.emptyIfNull(gstReportProfileDetails).stream()
                .findFirst()
                .map(GSTReportProfileDetail::getTradeName)
                .orElse(null);
    }

    private UserInfo getUserInfo(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null) {
            log.debug("User info does not exist for {}, creating", username);
            userInfo = new UserInfo();
            userInfo.setUsername(username);
            return userInfoRepository.save(userInfo);
        } else {
            return userInfo;
        }
    }

    private boolean isEqualList(@Nullable List<String> list1, @Nullable List<String> list2) {
        return new HashSet<>(CollectionUtils.emptyIfNull(list1)).equals(new HashSet<>(CollectionUtils.emptyIfNull(list2)));
    }
}

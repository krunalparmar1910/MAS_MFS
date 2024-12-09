package com.pf.karza.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pf.common.exception.MasRuntimeException;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.Response;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.advanced.filinghistory.AdvancedFilingHistory;
import com.pf.karza.model.entity.advanced.filinghistory.AdvancedFilingHistoryEntry;
import com.pf.karza.model.entity.advanced.itrdata.ItrData;
import com.pf.karza.model.entity.advanced.outstandingdemand.AdvancedOutstandingDemand;
import com.pf.karza.model.entity.advanced.outstandingdemand.AdvancedOutstandingDemandEntry;
import com.pf.karza.model.entity.advanced.profile.Profile;
import com.pf.karza.model.entity.advanced.twentysixas.AdvancedTwentySixAS;
import com.pf.karza.model.entity.ais.Ais;
import com.pf.karza.repository.AdvancedFilingHistoryRepository;
import com.pf.karza.repository.AdvancedOutstandingDemandRepository;
import com.pf.karza.repository.AdvancedTwentySixASRepository;
import com.pf.karza.repository.AisRepository;
import com.pf.karza.repository.ItrDataRepository;
import com.pf.karza.repository.ProfileRepository;
import com.pf.karza.repository.RequestRepository;
import com.pf.karza.repository.ResponseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdvancedITRDataStore {
    private final ObjectMapper objectMapper;
    private final ResponseRepository responseRepository;
    private final RequestRepository requestRepository;
    private final ProfileRepository profileRepository;
    private final AdvancedFilingHistoryRepository advancedFilingHistoryRepository;
    private final AdvancedOutstandingDemandRepository advancedOutstandingDemandRepository;
    private final AdvancedTwentySixASRepository advancedTwentySixASRepository;
    private final ItrDataRepository itrDataRepository;
    private final AisRepository aisRepository;

    public AdvancedITRDataStore(
            ObjectMapper objectMapper,
            RequestRepository requestRepository,
            ResponseRepository responseRepository,
            ProfileRepository profileRepository,
            AdvancedFilingHistoryRepository advancedFilingHistoryRepository,
            AdvancedOutstandingDemandRepository advancedOutstandingDemandRepository,
            AdvancedTwentySixASRepository advancedTwentySixASRepository,
            ItrDataRepository itrDataRepository,
            AisRepository aisRepository) {
        this.objectMapper = objectMapper;
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.profileRepository = profileRepository;
        this.advancedFilingHistoryRepository = advancedFilingHistoryRepository;
        this.advancedOutstandingDemandRepository = advancedOutstandingDemandRepository;
        this.advancedTwentySixASRepository = advancedTwentySixASRepository;
        this.itrDataRepository = itrDataRepository;
        this.aisRepository = aisRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public ItrResponse saveAdvancedITRData(UserRequest userRequest, String responseBody, String masRefId, String ipAddress, String createdBy) throws JsonProcessingException {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Save request data
            Response response = ItrUtils.extractResponse(jsonNode, masRefId);
            if (response != null) {
                response.setUserRequest(userRequest);
                requestRepository.save(userRequest);
                responseRepository.save(response);
                log.info("Response and request table saved!!");

                saveAIS(jsonNode, userRequest);
                log.debug("AIS Data saved!!");

                saveItrData(jsonNode, userRequest);
                log.debug("ITR Data saved!!");

                saveProfile(jsonNode, userRequest);
                log.debug("Profile saved!!");

                saveAdvancedFilingHistory(jsonNode, userRequest);
                log.debug("Advanced Filing History saved!!");

                saveAdvancedTwentySixAS(jsonNode, userRequest);
                log.debug("Advanced 26AS saved!!");

                saveAdvancedOutstandingDemand(jsonNode, userRequest);
                log.debug("Advanced Outstanding Demand saved!!");

                // Build and return ItrResponse
                return ItrUtils.getItrResponse(response, masRefId);
            } else {
                log.error("Unable to extract request data. JSON structure might be incomplete.");
                throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Unable to extract request data. JSON structure might be incomplete.");
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            throw e;
        }
    }

    private void saveAIS(JsonNode jsonNode, UserRequest userRequest) {
        JsonNode aisNode = jsonNode.path(ItrUtils.RESULT).path("ais");

        TypeReference<Ais> aisTypeReference = new TypeReference<>() {
        };
        Ais ais = objectMapper.convertValue(aisNode, aisTypeReference);
        ais.setUserRequest(userRequest);

        aisRepository.save(ais);
    }

    private void saveItrData(JsonNode jsonNode, UserRequest userRequest) throws JsonProcessingException {
        JsonNode itrDataNode = jsonNode.path(ItrUtils.RESULT).path("itrData");

        TypeReference<ItrData> itrDataTypeReference = new TypeReference<>() {
        };
        ItrData itrData = objectMapper.readValue(itrDataNode.toString(), itrDataTypeReference);
        itrData.setUserRequest(userRequest);

        itrDataRepository.save(itrData);
    }

    private void saveProfile(JsonNode jsonNode, UserRequest userRequest) throws JsonProcessingException {
        JsonNode profileNode = jsonNode.path(ItrUtils.RESULT).path("profile");

        TypeReference<Profile> profileTypeReference = new TypeReference<>() {
        };
        Profile profile = objectMapper.readValue(profileNode.toString(), profileTypeReference);
        profile.setUserRequest(userRequest);

        profileRepository.save(profile);
    }

    private void saveAdvancedFilingHistory(JsonNode jsonNode, UserRequest userRequest) throws JsonProcessingException {
        JsonNode filingHistoryNode = jsonNode.path(ItrUtils.RESULT).path("filingHstry");
        AdvancedFilingHistory advancedFilingHistory = new AdvancedFilingHistory();
        advancedFilingHistory.setUserRequest(userRequest);

        CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(List.class, AdvancedFilingHistoryEntry.class);
        List<AdvancedFilingHistoryEntry> advancedFilingHistoryEntries = objectMapper.readValue(filingHistoryNode.toString(), collectionType);
        advancedFilingHistory.setAdvancedFilingHistoryEntries(advancedFilingHistoryEntries);
        for (AdvancedFilingHistoryEntry advancedFilingHistoryEntry : CollectionUtils.emptyIfNull(advancedFilingHistoryEntries)) {
            advancedFilingHistoryEntry.setAdvancedFilingHistory(advancedFilingHistory);
        }

        advancedFilingHistoryRepository.save(advancedFilingHistory);
    }

    private void saveAdvancedTwentySixAS(JsonNode jsonNode, UserRequest userRequest) throws JsonProcessingException {
        JsonNode twentySixASNode = jsonNode.path(ItrUtils.RESULT).path("26as");
        TypeReference<AdvancedTwentySixAS> advancedTwentySixASTypeReference = new TypeReference<>() {
        };
        AdvancedTwentySixAS advancedTwentySixAS = objectMapper.readValue(twentySixASNode.toString(), advancedTwentySixASTypeReference);
        advancedTwentySixAS.setUserRequest(userRequest);

        advancedTwentySixASRepository.save(advancedTwentySixAS);
    }

    private void saveAdvancedOutstandingDemand(JsonNode jsonNode, UserRequest userRequest) throws JsonProcessingException {
        JsonNode advancedOutstandingDemandNode = jsonNode.path(ItrUtils.RESULT).path("outstandingDemand");
        AdvancedOutstandingDemand advancedOutstandingDemand = new AdvancedOutstandingDemand();
        advancedOutstandingDemand.setUserRequest(userRequest);

        CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(List.class, AdvancedOutstandingDemandEntry.class);
        List<AdvancedOutstandingDemandEntry> advancedOutstandingDemandEntries = objectMapper.readValue(advancedOutstandingDemandNode.toString(), collectionType);

        for (AdvancedOutstandingDemandEntry advancedOutstandingDemandEntry : CollectionUtils.emptyIfNull(advancedOutstandingDemandEntries)) {
            advancedOutstandingDemandEntry.setAdvancedOutstandingDemand(advancedOutstandingDemand);
        }
        advancedOutstandingDemand.setAdvancedOutstandingDemandEntries(advancedOutstandingDemandEntries);

        advancedOutstandingDemandRepository.save(advancedOutstandingDemand);
    }
}

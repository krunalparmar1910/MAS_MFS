package com.pf.karza.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.exception.MasRuntimeException;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.EntityBehaviour;
import com.pf.karza.model.entity.FillingHistory;
import com.pf.karza.model.entity.OutstandingDemand;
import com.pf.karza.model.entity.Response;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.ais.Ais;
import com.pf.karza.model.entity.fillingData.FillingData;
import com.pf.karza.model.entity.generalProfile.GeneralProfileInfo;
import com.pf.karza.model.entity.twentySixAS.TwentySixASData;
import com.pf.karza.repository.AisRepository;
import com.pf.karza.repository.EntityBehaviourRepository;
import com.pf.karza.repository.FillingDataRepository;
import com.pf.karza.repository.FillingHistoryRepository;
import com.pf.karza.repository.GeneralProfileInfoRepository;
import com.pf.karza.repository.OutstandingDemandRepository;
import com.pf.karza.repository.RequestRepository;
import com.pf.karza.repository.ResponseRepository;
import com.pf.karza.repository.TwentySixASDataRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SalariedITRDataStore {
    private final ObjectMapper objectMapper;
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;
    private final GeneralProfileInfoRepository generalProfileInfoRepository;
    private final EntityBehaviourRepository entityBehaviourRepository;
    private final TwentySixASDataRepository twentySixASDataRepository;
    private final OutstandingDemandRepository outstandingDemandRepository;
    private final FillingHistoryRepository fillingHistoryRepository;
    private final FillingDataRepository fillingDataRepository;
    private final AisRepository aisRepository;


    @Autowired
    public SalariedITRDataStore(ObjectMapper objectMapper,
                                RequestRepository requestRepository,
                                ResponseRepository responseRepository,
                                GeneralProfileInfoRepository generalProfileInfoRepository,
                                EntityBehaviourRepository entityBehaviourRepository,
                                TwentySixASDataRepository twentySixASDataRepository,
                                OutstandingDemandRepository outstandingDemandRepository,
                                FillingHistoryRepository fillingHistoryRepository,
                                FillingDataRepository fillingDataRepository,
                                AisRepository aisRepository) {
        this.objectMapper = objectMapper;
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.generalProfileInfoRepository = generalProfileInfoRepository;
        this.entityBehaviourRepository = entityBehaviourRepository;
        this.twentySixASDataRepository = twentySixASDataRepository;
        this.outstandingDemandRepository = outstandingDemandRepository;
        this.fillingHistoryRepository = fillingHistoryRepository;
        this.fillingDataRepository = fillingDataRepository;
        this.aisRepository = aisRepository;
    }


    @Transactional(rollbackOn = Exception.class)
    public ItrResponse saveSalariedITRData(UserRequest userRequest, String responseBody, String masRefId, String ipAddress, String createdBy) throws JsonProcessingException {
        try {

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = jsonNode.has(ItrUtils.RESULT) ? jsonNode.path(ItrUtils.RESULT) : null;

            if (resultNode == null) {
                throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Could not read itr json response.");
            }
            resultNode = ItrUtils.flattenCptyArrays(resultNode);

            // Save request data
            Response response = ItrUtils.extractResponse(jsonNode, masRefId);

            if (response != null) {
                log.info("Triggering itr salaried data store.");
                response.setUserRequest(userRequest);
                requestRepository.save(userRequest);
                responseRepository.save(response);
                log.info("Response and request table saved!!");

                saveGeneralProfile(resultNode, userRequest);

                saveEntityBehaviour(resultNode, userRequest);

                save26AS(resultNode, userRequest);

                saveOutstandingDemand(resultNode, userRequest);

                saveItrFillingHistory(resultNode, userRequest);

                saveFillingData(resultNode, userRequest);

                saveAIS(resultNode, userRequest);

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

    private void saveAIS(JsonNode resultNode, UserRequest userRequest) {
        // Save AIS Data
        JsonNode aisNode = resultNode.has("ais") ? resultNode.path("ais") : null;

        if (aisNode != null && !aisNode.isNull() && !aisNode.isMissingNode()) {
            Ais ais = objectMapper.convertValue(aisNode, Ais.class);
            ais.setUserRequest(userRequest);
            aisRepository.save(ais);
            log.debug("AIS table saved!!");
        } else {
            log.debug("ais block not found. Skipped ais data storage.");
        }
    }


    private void saveFillingData(JsonNode resultNode, UserRequest userRequest) {
        // Save Filling Data
        JsonNode fillingDataArrayNode = resultNode.has("filingData") ? resultNode.path("filingData") : null;
        if (fillingDataArrayNode != null && !fillingDataArrayNode.isNull() && !fillingDataArrayNode.isMissingNode()) {
            List<FillingData> fillingDataList = objectMapper.convertValue(fillingDataArrayNode, new TypeReference<List<FillingData>>() {
            });
            fillingDataList.forEach(each -> each.setUserRequest(userRequest));
            fillingDataRepository.saveAll(fillingDataList);
            log.debug("Filling Data table saved!!");
        } else {
            log.debug("filingData block not found. Skipped filingData data storage.");
        }

    }

    private void saveItrFillingHistory(JsonNode resultNode, UserRequest userRequest) {

        JsonNode itrFilledArrayNode = resultNode.has("filingHistory") ? resultNode.path("filingHistory") : null;
        if (itrFilledArrayNode != null && !itrFilledArrayNode.isNull() && !itrFilledArrayNode.isMissingNode()) {
            List<FillingHistory> fillingHistoryList = objectMapper.convertValue(itrFilledArrayNode, new TypeReference<List<FillingHistory>>() {
            });
            fillingHistoryList.forEach(each -> each.setUserRequest(userRequest));
            fillingHistoryRepository.saveAll(fillingHistoryList);
            log.debug("Itr Filling History table save!!");
        } else {
            log.debug("filingHistory block not found. Skipped filingHistory data storage.");
        }
    }

    private void saveOutstandingDemand(JsonNode resultNode, UserRequest userRequest) {
        // Save OutStandingDemand
        JsonNode outstandingDemandNode = resultNode.has("outstandingDemand") ? resultNode.path("outstandingDemand") : null;
        if (outstandingDemandNode != null && !outstandingDemandNode.isNull() && !outstandingDemandNode.isMissingNode()) {
            List<OutstandingDemand> outstandingDemandList = objectMapper.convertValue(outstandingDemandNode, new TypeReference<List<OutstandingDemand>>() {
            });
            outstandingDemandList.forEach(each -> each.setUserRequest(userRequest));
            outstandingDemandRepository.saveAll(outstandingDemandList);
            log.debug("OutstandingDemand details saved!!");
        } else {
            log.debug("outstandingDemand block not found. Skipped outstandingDemand data storage.");
        }
    }

    private void save26AS(JsonNode resultNode, UserRequest userRequest) {

        JsonNode twentySixASNode = resultNode.has("26asData") ? resultNode.path("26asData") : null;
        if (twentySixASNode != null && !twentySixASNode.isNull() && !twentySixASNode.isMissingNode()) {

            List<TwentySixASData> twentySixASDataList = new ArrayList<>();
            for (JsonNode parentNode : twentySixASNode) {
                JsonNode someDataNode = parentNode.get("data");
                TwentySixASData twentySixASData = objectMapper.convertValue(someDataNode, TwentySixASData.class);
                twentySixASData.setStatusOf26asData(ItrUtils.getValue(parentNode.get("statusOf26asData")));
                twentySixASData.setAssessmentYear(ItrUtils.getValue(parentNode.get("assessmentYear")));
                twentySixASData.setUserRequest(userRequest);
                twentySixASDataList.add(twentySixASData);
            }
            twentySixASDataRepository.saveAll(twentySixASDataList);
            log.debug("26AS table saved!!");
        } else {
            log.debug("26asData block not found. Skipped 26asData data storage.");
        }
    }

    private void saveGeneralProfile(JsonNode resultNode, UserRequest userRequest) {

        JsonNode profileNode = resultNode.has("profile") ? resultNode.path("profile") : null;
        if (profileNode != null && !profileNode.isNull() && !profileNode.isMissingNode()) {
            GeneralProfileInfo generalProfileInfo = objectMapper.convertValue(profileNode, GeneralProfileInfo.class);
            generalProfileInfo.setUserRequest(userRequest);
            generalProfileInfoRepository.save(generalProfileInfo);
            log.debug("GeneralProfileInfo table saved!!");
        } else {
            log.debug("profile block not found. Skipped profile data storage.");
        }

    }

    private void saveEntityBehaviour(JsonNode resultNode, UserRequest userRequest) {

        JsonNode entityBehaviourNode = resultNode.has("entityBehaviour") ? resultNode.path("entityBehaviour") : null;
        if (entityBehaviourNode != null && !entityBehaviourNode.isNull() && !entityBehaviourNode.isMissingNode()) {
            EntityBehaviour entityBehaviour = objectMapper.convertValue(entityBehaviourNode, EntityBehaviour.class);
            entityBehaviour.setUserRequest(userRequest);
            entityBehaviourRepository.save(entityBehaviour);
            log.debug("EntityBehaviour table saved!!");
        } else {
            log.debug("entityBehaviour block not found. Skipped entityBehaviour data storage.");
        }

    }
}

package com.pf.mas.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.mapper.CibilConsumerMapper;
import com.pf.mas.model.dto.cibil.OutputResponseDTO;
import com.pf.mas.model.dto.cibil.commercial.EnvelopeCommercialDTO;
import com.pf.mas.model.dto.cibil.consumer.EnvelopeDTO;
import com.pf.mas.model.dto.cibil.enums.CcFlag;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsDTO;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsSearchRequestDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerProfileSummaryDTO;
import com.pf.mas.model.dto.cibil.ui.DataSaveDTO;
import com.pf.mas.model.entity.cibil.CibilErrorLog;
import com.pf.mas.model.entity.cibil.NonCibilAccount;
import com.pf.mas.model.entity.commercial.EnvelopeResponseCommercial;
import com.pf.mas.model.entity.consumer.EnvelopeResponse;
import com.pf.mas.repository.cibil.CibilErrorLogRepository;
import com.pf.mas.service.cibilservice.CibilCommercialService;
import com.pf.mas.service.cibilservice.CibilConsumerService;
import com.pf.mas.utils.cibil.CibilUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cibil")
@Slf4j
@RequiredArgsConstructor
public class CibilController {
    private final CibilCommercialService cibilCommercialService;
    private final CibilConsumerService cibilConsumerService;
    private final CibilErrorLogRepository cibilErrorLogRepository;
    private final CibilConsumerMapper cibilConsumerMapper;

    @PostMapping(value = "/data/save/{requestId}")
    public ResponseEntity<OutputResponseDTO> saveConsumerCommercialData(
            @PathVariable String requestId,
            @RequestParam(name = "ip-address", required = false) String ipAddress,
            @RequestParam(value = "created-by", required = false) String createdBy,
            @RequestBody DataSaveDTO dataSaveDTO) {
        log.debug("Saving new consumer data with request id {}", requestId);
        try {
            cibilConsumerService.saveRawData(requestId, dataSaveDTO, ipAddress, createdBy);
            log.debug("Saved raw data");
        } catch (Exception e) {
            log.error("Error while saving raw cibil data.", e);
        }

        String extractedXmlResponse = CibilUtility.decodeAndExtractEncodedRawXml(dataSaveDTO.getRawXmlString());
        try {
            if (dataSaveDTO.getCcFlag().equalsIgnoreCase(CcFlag.CONSUMER.toString())) {
                log.debug("Saving consumer data");
                EnvelopeDTO envelopeDTO = convertXmlToDto(extractedXmlResponse, requestId, EnvelopeDTO.class);
                EnvelopeResponse envelopeResponse = cibilConsumerService.addConsumerDetail(requestId, envelopeDTO, dataSaveDTO.getLosDetailsDTO(), ipAddress, createdBy);
                if (envelopeResponse == null) {
                    log.warn("Recieved null response, returning empty response");
                    return ResponseEntity.ok().build();
                } else {
                    log.debug("Saved data, returning");
                    return ResponseEntity.ok(OutputResponseDTO.builder().id(envelopeResponse.getId()).requestId(envelopeResponse.getRequestId()).status("Data saved successfully").build());
                }
            } else if (dataSaveDTO.getCcFlag().equalsIgnoreCase(CcFlag.COMMERCIAL.toString())) {
                log.debug("Saving commercial data");
                EnvelopeCommercialDTO envelopeCommercialDTO = convertXmlToDto(extractedXmlResponse, requestId, EnvelopeCommercialDTO.class);
                EnvelopeResponseCommercial envelopeResponseCommercial = cibilCommercialService.addCommercialDetail(requestId, envelopeCommercialDTO, ipAddress, createdBy);
                if (envelopeResponseCommercial == null) {
                    log.warn("Recieved null response, returning empty response");
                    return ResponseEntity.ok().build();
                } else {
                    log.debug("Saved data, returning");
                    return ResponseEntity.ok(OutputResponseDTO.builder().id(envelopeResponseCommercial.getId()).requestId(envelopeResponseCommercial.getRequestId()).status("Data saved successfully").build());
                }
            }
        } catch (MasRuntimeException e) {
            throw e;
        } catch (Exception e) {
            handleCibilErrorLog(extractedXmlResponse, requestId, e);
            throw new MasRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving data.");
        }
        throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Error while saving data.");
    }

    @PostMapping(value = "/consumer/profile/{requestId}")
    public ResponseEntity<ConsumerProfileSummaryDTO> getConsumerProfileSummary(@PathVariable String requestId, @RequestBody AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO) {
        return ResponseEntity.ok(cibilConsumerService.getConsumerProfileSummary(requestId, accountBorrowingDetailsSearchRequestDTO));
    }

    @PostMapping(value = "/consumer/non-cibil/account/{requestId}")
    public ResponseEntity<NonCibilAccount> addManualAccountData(@PathVariable String requestId, @RequestBody NonCibilAccount nonCibilAccount) {
        nonCibilAccount.setRequestId(requestId);
        return ResponseEntity.ok(cibilConsumerService.addNonCibilAccount(nonCibilAccount));
    }

    @GetMapping(value = "/consumer/non-cibil/account/{requestId}")
    public ResponseEntity<List<NonCibilAccount>> getNonCibilAccountDetails(@PathVariable String requestId) {
        return ResponseEntity.ok(cibilConsumerService.getNonCibilAccount(requestId));
    }

    @PutMapping(value = "/consumer/non-cibil/account/{requestId}")
    public ResponseEntity<NonCibilAccount> updateNonCibilAccountDetails(@PathVariable String requestId, @RequestBody NonCibilAccount nonCibilAccount) {
        nonCibilAccount.setRequestId(requestId);
        return ResponseEntity.ok(cibilConsumerService.updateNonCibilAccount(nonCibilAccount));
    }

    @DeleteMapping(value = "/consumer/non-cibil/account/{id}")
    public ResponseEntity<Void> deleteNonCibilAccountDetails(@PathVariable UUID id) {
        cibilConsumerService.deleteNonCibilAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/consumer/cibil/account/{id}")
    public ResponseEntity<AccountBorrowingDetailsDTO> updateCibilAccountData(@PathVariable Long id, @RequestBody AccountBorrowingDetailsDTO accountBorrowingDetailsDTO) {
        return ResponseEntity.ok(cibilConsumerService.updateCibilAccountDetails(accountBorrowingDetailsDTO, id));
    }

    @PostMapping(value = "/consumer/profile/view/{requestId}")
    public ResponseEntity<ConsumerProfileSummaryDTO> getConsumerProfileSummaryForView(@PathVariable String requestId, @RequestParam String  searchCriteria,@RequestParam String  searchString, @RequestParam String searchFor, @RequestBody AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO) {
        return ResponseEntity.ok(cibilConsumerService.getConsumerProfileSummaryView(requestId, accountBorrowingDetailsSearchRequestDTO,searchCriteria,searchString,searchFor));
    }


    private <T> T convertXmlToDto(String xmlData, String requestId, Class<T> targetType) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return xmlMapper.readValue(xmlData, targetType);
        } catch (Exception e) {
            handleCibilErrorLog(xmlData, requestId, e);
        }
        throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Could not parse xml Data");
    }

    private void handleCibilErrorLog(String xmlData, String requestId, Exception e) {
        log.error("Error response:", e);
        log.error("Triggering error log mechanism for request id {}", requestId);
        CibilErrorLog cibilErrorLog = new CibilErrorLog();
        cibilErrorLog.setRawData(xmlData);
        cibilErrorLog.setError(e.toString());
        cibilErrorLog.setDate(LocalDateTime.now());
        cibilErrorLog.setRequestId(requestId);
        cibilErrorLogRepository.save(cibilErrorLog);
        log.error("Error log reported triggered successfully.");
    }

}

package com.pf.karza.service;

import com.pf.common.enums.ProgressStatus;
import com.pf.karza.model.dto.request.ItrRequest;
import com.pf.karza.model.entity.ItrErrorLog;
import com.pf.karza.model.entity.ItrRawData;
import com.pf.karza.repository.ErrorLogRepository;
import com.pf.karza.repository.ItrRawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorLoggingService {
    private final ErrorLogRepository errorLogRepository;
    private final ItrRawDataRepository itrRawDataRepository;
    private final KarzaITREncryptionService karzaITREncryptionService;

    public void logItrError(String errorMessage, String rawData, ItrRequest itrRequest) {
        log.error("Error occurred while saving data. Triggering Error Logging mechanism. " + errorMessage);
        ItrErrorLog itrErrorLog = new ItrErrorLog();
        itrErrorLog.setError(errorMessage);
        itrErrorLog.setRawData(rawData);
        itrErrorLog.setUsername(itrRequest.getUsername());
        itrErrorLog.setPassword(karzaITREncryptionService.encrypt(itrRequest.getPassword()));
        itrErrorLog.setConsent(itrRequest.getConsent());
        itrErrorLog.setMasRefId(itrRequest.getMasRefId());
        itrErrorLog.setRequestType(itrRequest.getRequestType());
        itrErrorLog.setDate(LocalDateTime.now());
        errorLogRepository.save(itrErrorLog);
        log.info("Error log data saved successfully.");
    }

    public void saveRawData(String errorMessage, String rawData, ItrRequest itrRequest, ProgressStatus progressStatus) {
        if (ProgressStatus.COMPLETED == progressStatus) {
            log.info("Saving raw data for successful response");
        } else {
            log.error("Error occurred while saving data. Triggering raw data save. " + errorMessage);
        }
        ItrRawData itrRawData = new ItrRawData();
        itrRawData.setError(errorMessage);
        itrRawData.setRawData(rawData);
        itrRawData.setUsername(itrRequest.getUsername());
        itrRawData.setPassword(karzaITREncryptionService.encrypt(itrRequest.getPassword()));
        itrRawData.setConsent(itrRequest.getConsent());
        itrRawData.setMasRefId(itrRequest.getMasRefId());
        itrRawData.setRequestType(itrRequest.getRequestType());
        itrRawData.setDate(LocalDateTime.now());
        itrRawData.setProgressStatus(progressStatus);
        itrRawDataRepository.save(itrRawData);
        log.info("Raw data saved successfully.");
    }
}
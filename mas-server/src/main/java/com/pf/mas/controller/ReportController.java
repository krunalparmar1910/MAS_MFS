package com.pf.mas.controller;

import com.pf.mas.dto.ProfileDetailResponse;
import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.GSTReportProfileDetail;
import com.pf.mas.dto.report.UpdateGST3BReportManualEntryRequest;
import com.pf.mas.exception.MasGSTInvalidRequestParametersException;
import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasGetGST3BReportException;
import com.pf.mas.exception.MasJSONException;
import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.mapper.ProfileDetailResponseMapper;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import com.pf.mas.service.gst.GSTReportService;
import com.pf.mas.service.report.ReportReadingService;
import com.pf.mas.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    private static final String INVALID_ARGUMENTS_ERROR = "Either entity id or client order id is required";
    private final AsyncTaskExecutor taskExecutor;
    private final ProfileDetailResponseMapper profileDetailResponseMapper;
    private final ReportReadingService reportReadingService;
    private final GSTReportService gstReportService;
    private final UserInfoService userInfoService;
    @Value("${default-username}")
    private String defaultUsername;

    public ReportController(
            AsyncTaskExecutor taskExecutor,
            ReportReadingService reportReadingService,
            ProfileDetailResponseMapper profileDetailResponseMapper,
            GSTReportService gstReportService,
            UserInfoService userInfoService) {
        this.taskExecutor = taskExecutor;
        this.reportReadingService = reportReadingService;
        this.profileDetailResponseMapper = profileDetailResponseMapper;
        this.gstReportService = gstReportService;
        this.userInfoService = userInfoService;
    }

    @PostMapping(value = "/parse-excel-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDetailResponse> parseExcelReport(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "entity-id") String entityId,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "ip-address", required = false) String ipAddress)
            throws MasGSTInvalidRequestParametersException, MasReportSheetReaderException, MasGSTNoEntityFoundException {
        if (StringUtils.isBlank(entityId)) {
            log.error("Invalid arguments, entity id {} is required", entityId);
            throw new MasGSTInvalidRequestParametersException("Entity id is required");
        }

        if (file == null) {
            throw new MasReportSheetReaderException("No file was sent to parse excel report");
        }

        String fileName = file.getOriginalFilename();
        log.info("Parsing excel report file {}", fileName);

        byte[] fileData;
        try {
            log.debug("Reading excel report file {}", fileName);
            fileData = file.getBytes();
        } catch (IOException e) {
            log.error("Error occurred during reading all bytes from file {} due to {}", fileName, e.getLocalizedMessage());
            throw new MasReportSheetReaderException(e);
        }

        ClientOrder clientOrder = reportReadingService.getOrCreateNewClientOrder(entityId, null, createdBy, ipAddress);
        List<ProfileDetail> profileDetails = reportReadingService.readAndStoreReport(clientOrder, fileName, fileData);

        updateGST3BReportRequestHistory(entityId, profileDetails);

        return ResponseEntity.ok().body(ProfileDetailResponse.builder()
                .clientOrderId(clientOrder.getClientOrderId())
                .entityId(clientOrder.getEntityId())
                .profileDetails(profileDetails.stream().map(profileDetailResponseMapper::convertToProfileDetailResponse).toList())
                .build());
    }

    @GetMapping("/gst-3b-report")
    public ResponseEntity<GSTReport> getGST3BReport(
            @RequestParam(value = "entity-id", required = false) String entityId,
            @RequestParam(value = "client-order-ids", required = false) List<String> clientOrderIds)
            throws MasGSTInvalidRequestParametersException, MasGSTNoEntityFoundException, MasGetGST3BReportException, MasJSONException {
        if (StringUtils.isBlank(entityId)
                && (CollectionUtils.isEmpty(clientOrderIds) || clientOrderIds.stream().anyMatch(StringUtils::isBlank))) {
            log.error("Invalid arguments, entity id {}, client order ids {}", entityId, clientOrderIds);
            throw new MasGSTInvalidRequestParametersException(INVALID_ARGUMENTS_ERROR);
        }

        log.info("Requesting GST3B report for entity id {}, client order ids {}", entityId, clientOrderIds);

        GSTReport gstReport = null;
        try {
            gstReport = gstReportService.getGSTReport(entityId, clientOrderIds);
        } finally {
            updateGST3BReportRequestHistory(entityId, clientOrderIds, gstReport != null ? gstReport.getGstReportProfileDetails() : Collections.emptyList());
        }

        return ResponseEntity.ok().body(gstReport);
    }

    @PutMapping("/update-manual-entries-gst-3b-report")
    public ResponseEntity<GSTReport> updateManualEntriesGST3BReport(
            @RequestBody UpdateGST3BReportManualEntryRequest updateRequest) throws MasGSTInvalidRequestParametersException, MasGSTNoEntityFoundException, MasGetGST3BReportException {
        log.info("Updating manual entries for GST3B report {}", updateRequest);
        if (updateRequest == null) {
            throw new MasGSTInvalidRequestParametersException("Request body not provided");
        }
        if ((StringUtils.isBlank(updateRequest.getEntityId()) && StringUtils.isBlank(updateRequest.getClientOrderId()))
                || (StringUtils.isNotBlank(updateRequest.getEntityId()) && StringUtils.isNotBlank(updateRequest.getClientOrderId()))) {
            log.error("Invalid arguments {}", updateRequest);
            throw new MasGSTInvalidRequestParametersException(INVALID_ARGUMENTS_ERROR);
        }

        GSTReport updatedManualEntriesReport = gstReportService.updateManualEntriesInGST3BReport(updateRequest);
        log.info("Manual entries updated for GST3B Report {}", updateRequest);
        return ResponseEntity.ok().body(updatedManualEntriesReport);
    }

    private void updateGST3BReportRequestHistory(String entityId, List<ProfileDetail> profileDetails) {
        List<GSTReportProfileDetail> gstReportProfileDetails = profileDetails.stream()
                .map(profileDetail -> GSTReportProfileDetail.builder()
                        .gstNumber(profileDetail.getGstNumber())
                        .tradeName(profileDetail.getTradeName())
                        .legalName(profileDetail.getLegalName())
                        .panNumber(profileDetail.getPanNumber())
                        .state(profileDetail.getState())
                        .placeOfBusiness(profileDetail.getPlaceOfBusiness())
                        .status(profileDetail.getStatus())
                        .build())
                .toList();
        updateGST3BReportRequestHistory(entityId, Collections.emptyList(), gstReportProfileDetails);
    }

    private void updateGST3BReportRequestHistory(String entityId, List<String> clientOrderIds, List<GSTReportProfileDetail> gstReportProfileDetails) {
        try {
            taskExecutor.execute(() -> {
                try {
                    userInfoService.updateGST3BReportRequestHistory(defaultUsername, entityId, clientOrderIds, gstReportProfileDetails);
                } catch (MasJSONException e1) {
                    log.error("Could not save report request history due to {}", e1.getLocalizedMessage(), e1);
                }
            });
        } catch (Exception e2) {
            log.error("Unexpected error while submitting task to update gst3b report request history", e2);
        }
    }
}

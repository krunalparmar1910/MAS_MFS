package com.pf.mas.controller;

import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.karza.exception.MasKarzaITRInvalidRequestException;
import com.pf.karza.model.dto.request.ItrRequest;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.repository.RequestRepository;
import com.pf.karza.service.KarzaITRApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/karza")
public class KarzaITRController {
    private final KarzaITRApi karzaITRApi;
    private final RequestRepository requestRepository;

    public KarzaITRController(
            KarzaITRApi karzaITRApi,
            RequestRepository requestRepository) {
        this.karzaITRApi = karzaITRApi;
        this.requestRepository = requestRepository;
    }

    @PostMapping("/loadITRData")
    public ResponseEntity<ItrResponse> loadItrData(
            @RequestBody ItrRequest request,
            @RequestParam(name = "ip-address", required = false) String ipAddress,
            @RequestParam(value = "created-by", required = false) String createdBy) throws MasKarzaITRInvalidRequestException, MasThirdPartyApiException {
        checkIfMasRefIdAlreadyExists(request);

        if ("salaried".equalsIgnoreCase(request.getRequestType())) {
            return karzaITRApi.getItrSalaried(request, ipAddress, createdBy);
        } else if ("business".equalsIgnoreCase(request.getRequestType())) {
            return karzaITRApi.getItrBusiness(request, ipAddress, createdBy);
        } else if ("advanced".equalsIgnoreCase(request.getRequestType())) {
            return karzaITRApi.getItrAdvanced(request, ipAddress, createdBy);
        } else {
            log.error("Invalid request type {} provided", request.getRequestType());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ItrResponse.builder()
                            .masRefId(request.getMasRefId())
                            .error(String.format("Invalid request type provided: %s", request.getRequestType()))
                    .build());
        }
    }

    private void checkIfMasRefIdAlreadyExists(ItrRequest itrRequest) throws MasKarzaITRInvalidRequestException {
        if (StringUtils.isBlank(itrRequest.getMasRefId())) {
            throw new MasKarzaITRInvalidRequestException(String.format("Mas Ref Id was not specified: %s", itrRequest.getMasRefId()));
        }
        Optional<UserRequest> request = requestRepository.findByMasRefId(itrRequest.getMasRefId());
        if (request.isPresent()) {
            throw new MasKarzaITRInvalidRequestException(String.format("Data with Mas Ref Id %s already exists, please specify a unique ID", itrRequest.getMasRefId()));
        }
    }
}

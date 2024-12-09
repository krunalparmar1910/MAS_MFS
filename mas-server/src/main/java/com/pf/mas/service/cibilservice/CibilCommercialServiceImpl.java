package com.pf.mas.service.cibilservice;

import com.pf.common.exception.MasRuntimeException;
import com.pf.mas.mapper.CibilCommercialMapper;
import com.pf.mas.model.dto.cibil.commercial.DcResponseCommercialDTO;
import com.pf.mas.model.dto.cibil.commercial.EnvelopeCommercialDTO;
import com.pf.mas.model.entity.commercial.EnvelopeResponseCommercial;
import com.pf.mas.repository.cibil.EnvelopeResponseCommercialRepository;
import com.pf.mas.repository.cibil.EnvelopeResponseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CibilCommercialServiceImpl implements CibilCommercialService {
    @Autowired
    private EnvelopeResponseCommercialRepository envelopeResponseCommercialRepository;

    @Autowired
    private CibilCommercialMapper cibilCommercialMapper;
    @Autowired
    private EnvelopeResponseRepository envelopeResponseRepository;

    @Override
    public EnvelopeResponseCommercial addCommercialDetail(String requestId, EnvelopeCommercialDTO envelopeCommercialDTO, String ipAddress, String createdBy) {
        if (requestId == null) {
            log.error("[CibilCommercialServiceImpl][addCommercialDetail] Could not save commercial data. Request id not found.");
            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Request Id not found.");
        }
        if (envelopeResponseCommercialRepository.findByRequestId(requestId).isPresent()) {
            log.error("[CibilCommercialServiceImpl][addCommercialDetail] Could not save commercial data. Data against the request id %s already present.\", requestId");

            throw new MasRuntimeException(HttpStatus.BAD_REQUEST, String.format("Data against the request id %s already present.", requestId));
        }
        if (envelopeCommercialDTO != null && envelopeCommercialDTO.getBodyCommercialDTO() != null && envelopeCommercialDTO.getBodyCommercialDTO().getExecuteXMLStringResponseCommercialDTO() != null && envelopeCommercialDTO.getBodyCommercialDTO().getExecuteXMLStringResponseCommercialDTO().getExecuteXMLStringResultCommercialDTO() != null) {
            log.info("[CibilCommercialServiceImpl][addCommercialDetail] Initiating commercial data save.");
            DcResponseCommercialDTO dcResponseCommercialDTO = envelopeCommercialDTO.getBodyCommercialDTO().getExecuteXMLStringResponseCommercialDTO().getExecuteXMLStringResultCommercialDTO().getDcResponseCommercialDTO();
            EnvelopeResponseCommercial envelopeResponseCommercial = cibilCommercialMapper.toEnvelopeResponseCommercial(dcResponseCommercialDTO);
            envelopeResponseCommercial.setRequestId(requestId);
            envelopeResponseCommercial.setIpAddress(ipAddress);
            envelopeResponseCommercial.setCreatedBy(createdBy);
            EnvelopeResponseCommercial savedEnvelopeResponseCommercial = envelopeResponseCommercialRepository.save(envelopeResponseCommercial);
            log.info("[CibilCommercialServiceImpl][addCommercialDetail] Commercial data saved successfully.");
            return savedEnvelopeResponseCommercial;
        }
        log.error("[CibilCommercialServiceImpl][addCommercialDetail] Error while saving commercial data.");
        throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Xml data found to be empty.");
    }

}

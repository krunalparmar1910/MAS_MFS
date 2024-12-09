package com.pf.mas.service.cibilservice;

import com.pf.mas.model.dto.cibil.commercial.EnvelopeCommercialDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerProfileSummaryDTO;
import com.pf.mas.model.entity.commercial.EnvelopeResponseCommercial;

public interface CibilCommercialService {

    EnvelopeResponseCommercial addCommercialDetail(String requestId, EnvelopeCommercialDTO envelopeCommercialDTO, String ipAddress, String createdBy);

}



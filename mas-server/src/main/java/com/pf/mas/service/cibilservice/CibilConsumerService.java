package com.pf.mas.service.cibilservice;


import com.pf.mas.model.dto.cibil.consumer.EnvelopeDTO;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsDTO;
import com.pf.mas.model.dto.cibil.ui.AccountBorrowingDetailsSearchRequestDTO;
import com.pf.mas.model.dto.cibil.ui.ConsumerProfileSummaryDTO;
import com.pf.mas.model.dto.cibil.ui.DataSaveDTO;
import com.pf.mas.model.dto.cibil.ui.LosDetailsDTO;
import com.pf.mas.model.entity.cibil.NonCibilAccount;
import com.pf.mas.model.entity.consumer.EnvelopeResponse;

import java.util.List;
import java.util.UUID;

public interface CibilConsumerService {

    EnvelopeResponse addConsumerDetail(String requestId, EnvelopeDTO envelopeDTO, LosDetailsDTO losDetailsDTO, String ipAddress, String createdBy);

    ConsumerProfileSummaryDTO getConsumerProfileSummary(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO);

    EnvelopeResponse searchConsumerDetails(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO);

    List<NonCibilAccount> getNonCibilAccount(String requestId);

    NonCibilAccount addNonCibilAccount(NonCibilAccount nonCibilAccount);


    NonCibilAccount updateNonCibilAccount(NonCibilAccount nonCibilAccount);

    void deleteNonCibilAccount(UUID id);

    AccountBorrowingDetailsDTO updateCibilAccountDetails(AccountBorrowingDetailsDTO accountBorrowingDetailsDTO, Long id);

    void saveRawData(String requestId, DataSaveDTO dataSaveDTO, String ipAddress, String createdBy);

    ConsumerProfileSummaryDTO getConsumerProfileSummaryView(String requestId, AccountBorrowingDetailsSearchRequestDTO accountBorrowingDetailsSearchRequestDTO, String searchCriteria,String searchString,String searchFor);
}

package com.pf.mas.controller;

import com.pf.common.response.CommonResponse;
import com.pf.perfios.exception.MasBadRequestException;
import com.pf.perfios.exception.MasEntityNotFoundException;
import com.pf.perfios.model.dto.*;
//import com.pf.perfios.model.entity.EditableCustomFields;
import com.pf.perfios.model.entity.MasRequests;
import com.pf.perfios.service.MasterRuleService;
import com.pf.perfios.service.PerfiosService;
import com.pf.perfios.service.SaveReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/perfios")
@RequiredArgsConstructor
public class PerfiosController {
    private static final String SUCCESS = "Success";
    private final PerfiosService perfiosService;
    private final SaveReportService saveReportService;
    private final MasterRuleService masterRuleService;

    @PostMapping("/initiate-transaction")
    public ResponseEntity<String> initiateTransaction(@RequestBody InitiateTransactionRequestBodyDTO initiateTransactionRequestBodyDTO) {
        return perfiosService.initiateTransaction(initiateTransactionRequestBodyDTO);
    }

    @PostMapping("/{transactionId}/cancel-transaction")
    public ResponseEntity<String> cancelTransaction(@PathVariable(value = "transactionId") String transactionId) {
        return perfiosService.cancelTransaction(transactionId);
    }

    @PostMapping("/{transactionId}/delete-transaction")
    public ResponseEntity<String> deleteTransaction(@PathVariable(value = "transactionId") String transactionId) {
        return perfiosService.deleteTransaction(transactionId);
    }

    @PostMapping(value = "/{transactionId}/upload-file")
    public ResponseEntity<String> uploadFile(@PathVariable(value = "transactionId") String transactionId,
                                             @RequestParam(value = "file") MultipartFile file) {
        return perfiosService.uploadFile(transactionId, file);
    }

    @PostMapping("/{transactionId}/process-statement")
    public ResponseEntity<String> processStatement(@PathVariable(value = "transactionId") String transactionId,
                                                   @RequestBody ProcessStatementReqDTO processStatementReqDTO) {
        return perfiosService.processStatement(transactionId, processStatementReqDTO, false);
    }

    @PostMapping("/{transactionId}/re-process-statement")
    public ResponseEntity<String> reProcessStatement(@PathVariable(value = "transactionId") String transactionId,
                                                     @RequestBody ProcessStatementReqDTO processStatementReqDTO) {
        return perfiosService.processStatement(transactionId, processStatementReqDTO, true);
    }

    @PostMapping("/{transactionId}/generate-report")
    public ResponseEntity<String> generateReport(@PathVariable("transactionId") String transactionId) {
        return perfiosService.generateReport(transactionId);
    }

    //temporary API to call retrieve report directly to perfios
    @GetMapping("/{transactionId}/get-report/{type}")
    public ResponseEntity<byte[]> getReport(@PathVariable("transactionId") String transactionId,
                                            @PathVariable(value = "type", required = false) String type) {
        if (type == null) {
            type = "json";
        }
        return perfiosService.getReport(transactionId, type);
    }

    @GetMapping("/retrieve-report/{type}")
    public ResponseEntity<byte[]> retrieveReport(@RequestBody @Valid CreateReportResDTO createReportResDTO,
                                                 @PathVariable(value = "type", required = false) String type) throws MasEntityNotFoundException, MasBadRequestException {
        if (type == null) {
            type = "json";
        }
        type = type.toLowerCase();
        return perfiosService.retrieveReport(createReportResDTO, type);
    }

    @PostMapping("/{transactionId}/save-json-report")
    public ResponseEntity<String> saveJsonReport(@PathVariable("transactionId") String transactionId) {
        MasRequests masRequests = new MasRequests();
        masRequests.setPerfiosTransactionId(transactionId);
        masRequests.setMasFinancialId(UUID.randomUUID().toString());
        return perfiosService.saveJsonReport(masRequests);
    }

    @GetMapping("/{transactionId}/transaction-status")
    public ResponseEntity<String> transactionStatus(@PathVariable("transactionId") String transactionId) {
        return perfiosService.transactionStatus(transactionId);
    }

    @GetMapping("/bulk-transaction-status")
    public ResponseEntity<String> bulkTransactionStatus(
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime) {
        return perfiosService.bulkTransactionStatus(startTime, endTime);
    }

    @GetMapping("/get-institutions")
    public ResponseEntity<CommonResponse<InstitutionResponseDTO>> getInstitutions(
            @RequestParam("processingType") String processingType, @RequestParam("dataSource") String dataSource) {
        InstitutionResponseDTO data = perfiosService.getInstitutions(processingType, dataSource);
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS, data));
    }

    @PostMapping("/re-process-transaction")
    public ResponseEntity<String> reProcessTransaction(@RequestBody ReprocessTransactionReqDTO reprocessTransactionReqDTO) {
        return perfiosService.reProcessTransaction(reprocessTransactionReqDTO);
    }

    @PostMapping("/{masFinancialId}/monthly-details")
    public ResponseEntity<CommonResponse<MonthlyDetailsDTO>> monthlyDetails(
            @PathVariable("masFinancialId") String masFinancialId,
            @RequestParam("uniqueFirmId") String uniqueFirmId,
            @RequestBody MonthlyDetailsReqDTO request) throws MasEntityNotFoundException {
        MonthlyDetailsDTO data = saveReportService.monthlyDetails(masFinancialId,uniqueFirmId, request);
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS, data));
    }

    @PostMapping("/update-custom-fields")
    public ResponseEntity<CommonResponse<Object>> updateCustomFields(@RequestBody CustomFieldReqDTO request) {
        saveReportService.updateCustomFields(request);
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS, null));
    }

    @PutMapping("/update-transaction-custom-fields")
    public ResponseEntity<CommonResponse<Object>> updateTransactionCustomField(@RequestBody @Valid List<TransactionCustomFieldReqDTO> request) throws MasEntityNotFoundException {
        saveReportService.updateTransactionCustomFields(request);
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS, null));
    }

    @GetMapping("/{transactionId}/bank-drop-down")
    public ResponseEntity<CommonResponse<List<BankResDTO>>> bankList(@PathVariable("transactionId") String transactionId) {
        List<BankResDTO> data = saveReportService.bankList(transactionId);

        return ResponseEntity.ok(
                new CommonResponse<>(SUCCESS,
                        data
                )
        );
    }

    @PostMapping("/{masFinancialId}/transactions")
    public ResponseEntity<CommonResponse<PageResponseDTO<TransactionDTO>>> getTransactions(
            @PathVariable("masFinancialId") String masFinancialId, Pageable pageable, @RequestBody @Valid TransactionReqDTO request) throws MasEntityNotFoundException {
        PageResponseDTO<TransactionDTO> data = saveReportService.getTransactions(masFinancialId, request, pageable);
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS, data));
    }

    @PostMapping("/{masFinancialId}/transactions-total")
    public ResponseEntity<CommonResponse<TransactionTotalDTO>> getTransactionsTotal(
            @PathVariable("masFinancialId") String masFinancialId, @RequestBody @Valid TransactionReqDTO request) throws MasEntityNotFoundException {
        TransactionTotalDTO data = saveReportService.getTransactionsTotal(masFinancialId, request);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @GetMapping("/master-identifiers")
    public ResponseEntity<CommonResponse<List<MasterIdentifiersDTO>>> getMasterIdentifiers() {
        List<MasterIdentifiersDTO> data = masterRuleService.getMasterIdentifiers();

        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @PutMapping("/master-identifiers")
    public ResponseEntity<CommonResponse<MasterIdentifiersDTO>> updateMasterIdentifier(
            @RequestBody @Valid MasterIdentifiersUpdateDTO request) throws MasEntityNotFoundException, MasBadRequestException {
        MasterIdentifiersDTO data = masterRuleService.updateMasterIdentifier(request);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @DeleteMapping("/transaction-type-identifier/{id}")
    public ResponseEntity<CommonResponse<Object>> deleteTransactionTypeIdentifier(@PathVariable Long id) throws MasBadRequestException {
        masterRuleService.deleteTransactionTypeIdentifier(id);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        null
                )
        );
    }

    @DeleteMapping("/category-identifier/{id}")
    public ResponseEntity<CommonResponse<Object>> deleteCategoryIdentifier(@PathVariable Long id) throws MasBadRequestException {
        masterRuleService.deleteCategoryIdentifier(id);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        null
                )
        );
    }

    @DeleteMapping("/parties-identifier/{id}")
    public ResponseEntity<CommonResponse<Object>> deletePartiesIdentifier(@PathVariable Long id) throws MasBadRequestException {
        masterRuleService.deletePartiesIdentifier(id);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        null
                )
        );
    }

    @GetMapping("/master-rule")
    public ResponseEntity<CommonResponse<List<MasterRulesDTO>>> getMasterRules() {
        List<MasterRulesDTO> data = masterRuleService.getMasterRules();

        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @PutMapping("/master-rule")
    public ResponseEntity<CommonResponse<MasterRulesDTO>> updateMasterRule(@RequestBody @Valid MasterRulesUpdateDTO request) throws MasEntityNotFoundException, MasBadRequestException {
        MasterRulesDTO data = masterRuleService.updateMasterRule(request);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @PostMapping("/master-rule-transaction-flag")
    public ResponseEntity<CommonResponse<TransactionFlagDTO>> addMasterRuleTransactionFlag(@RequestBody @Valid TransactionFlagDTO request) throws MasBadRequestException {
        TransactionFlagDTO data = masterRuleService.addMasterRuleTransactionFlag(request);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        data
                )
        );
    }

    @DeleteMapping("/master-rule/{id}")
    public ResponseEntity<CommonResponse<Object>> deleteMasterRule(@PathVariable Long id) throws MasEntityNotFoundException, MasBadRequestException {
        masterRuleService.deleteMasterRule(id);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        SUCCESS,
                        null
                )
        );
    }

    //mas-financials API
    @PostMapping(value = "/create-report", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse<CreateReportResDTO>> createMASReport(
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "ip-address", required = false) String ipAddress,
            @ModelAttribute @Valid CreateReportDTO request) throws MasBadRequestException {
        CreateReportResDTO data = perfiosService.createMASReport(request, createdBy, ipAddress);
        return ResponseEntity.ok(new CommonResponse<>("Report Generated Successfully", data));
    }
}

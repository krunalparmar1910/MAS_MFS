package com.pf.perfios.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateReportDTO {
    @NotNull
    private String masFinancialId;

    private String customerTransactionId;

    private String uniqueFirmId;

    @NotNull
    private String transactionCompleteCallbackUrl;

    @NotNull
    private Boolean uploadingScannedStatements;

    private String institutionId;

    @NotNull
    private MultipartFile[] files;

    private String passwords;

    private String yearMonthFrom;

    private String yearMonthTo;

    private BigDecimal creditLimit;
}

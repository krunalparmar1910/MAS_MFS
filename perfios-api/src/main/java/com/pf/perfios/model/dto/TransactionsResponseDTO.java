package com.pf.perfios.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionsResponseDTO {

    private List<TransactionDTO> transactions;
}

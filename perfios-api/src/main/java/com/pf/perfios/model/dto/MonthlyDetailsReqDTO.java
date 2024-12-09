package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthlyDetailsReqDTO {

    private List<String> customerTxnIdList;
}

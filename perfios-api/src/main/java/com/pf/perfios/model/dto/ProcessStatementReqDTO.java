package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("payload")
public class ProcessStatementReqDTO {
    private String fileId;
    private String institutionId;
    private String password;
}

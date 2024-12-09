package com.pf.karza.model.dto.request;

import lombok.Data;

@Data
public class KarzaItrSalariedRequest {
    private String username;
    private String password;
    private String apiVersion;
    private String consent;
    private Integer numberOfYears;
}

package com.pf.karza.model.dto.request;

import lombok.Data;

@Data
public class KarzaItrBusinessRequest {
    private String username;
    private String password;
    private String apiVersion;
    private String consent;
    private Boolean additionalData;
    private Integer numberOfYears;
}

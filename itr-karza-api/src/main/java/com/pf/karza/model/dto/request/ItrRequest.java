package com.pf.karza.model.dto.request;

import lombok.Data;

@Data
public class ItrRequest {
    private String username;
    private String password;
    private String consent;
    private String masRefId;
    private Integer numberOfYears;
    // allowed types - salaried, business, advanced
    private String requestType;
}

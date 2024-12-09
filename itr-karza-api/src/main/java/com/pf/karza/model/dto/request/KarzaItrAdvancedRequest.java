package com.pf.karza.model.dto.request;

import lombok.Data;

@Data
public class KarzaItrAdvancedRequest {
    private String username;
    private String password;
    private String consent;
    private Integer numberOfYears;
}

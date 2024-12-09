package com.pf.common.model.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CredentialType {
    CORPOSITORY_GST("Corpository GST"),
    PERFIOS("Perfios"),
    KARZA_ITR("Karza ITR");

    private final String name;

    CredentialType(String name) {
        this.name = name;
    }
}

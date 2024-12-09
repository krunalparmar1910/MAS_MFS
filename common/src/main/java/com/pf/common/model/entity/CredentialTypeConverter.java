package com.pf.common.model.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CredentialTypeConverter implements AttributeConverter<CredentialType, String> {
    @Override
    public String convertToDatabaseColumn(CredentialType credentialType) {
        if (credentialType == null) {
            throw new IllegalArgumentException("Credential Type cannot be null");
        }
        return credentialType.getName();
    }

    @Override
    public CredentialType convertToEntityAttribute(String s) {
        for (CredentialType credentialType : CredentialType.values()) {
            if (credentialType.getName().equals(s)) {
                return credentialType;
            }
        }
        throw new IllegalArgumentException("Invalid name " + s);
    }
}

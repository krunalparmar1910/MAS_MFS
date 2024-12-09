package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pf.perfios.model.entity.IdentifierType;
import com.pf.perfios.model.entity.MasterIdentifiers;
import com.pf.perfios.model.entity.DebitCredit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterIdentifiersDTO {

    private Long id;

    private IdentifierType identifierType;

    private String identifierName;

    private String identificationValue;

    private DebitCredit debitCredit;

    private boolean deletable;


    public static MasterIdentifiersDTO from(MasterIdentifiers masterIdentifiers) {
        return MasterIdentifiersDTO.builder()
                .id(masterIdentifiers.getId())
                .identifierType(masterIdentifiers.getIdentifierType())
                .identifierName(masterIdentifiers.getIdentifierName())
                .identificationValue(masterIdentifiers.getIdentificationValue())
                .debitCredit(masterIdentifiers.getDebitOrCredit())
                .deletable(masterIdentifiers.isDeletable())
                .build();
    }

    public static MasterIdentifiersDTO nameOnlyDTOFrom(MasterIdentifiers masterIdentifiers) {
        return MasterIdentifiersDTO.builder()
                .id(masterIdentifiers.getId())
                .identifierName(masterIdentifiers.getIdentifierName())
                .build();
    }
}

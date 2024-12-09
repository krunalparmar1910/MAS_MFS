package com.pf.perfios.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RuleQueryContext {

    private boolean transactionType;
    private boolean category;
    private boolean parties;
    private boolean identificationValue;
}

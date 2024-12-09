package com.pf.mas.model.dto.cibil.enums;

import lombok.Getter;

@Getter
public enum AssetClassificationStatus {
    XXX("Not Reported"),
    STD("Standard"),
    SMA("Special Mention Account"),
    SUB("Substandard"),
    DBT("Doubtful"),
    LSS("Loss");

    private final String status;

    AssetClassificationStatus(String status) {
        this.status = status;
    }

}

package com.pf.mas.model.dto.cibil.ui;

import lombok.Data;

@Data
public class DataSaveDTO {
    private String rawXmlString;
    private String ccFlag;
    private LosDetailsDTO losDetailsDTO;
}
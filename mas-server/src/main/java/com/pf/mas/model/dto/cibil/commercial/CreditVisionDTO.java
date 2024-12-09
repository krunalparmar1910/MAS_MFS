package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditVision")
public class CreditVisionDTO {
    private String creditVisionName;
    private String creditVisionDesc;
    private String creditVisionValue;
}
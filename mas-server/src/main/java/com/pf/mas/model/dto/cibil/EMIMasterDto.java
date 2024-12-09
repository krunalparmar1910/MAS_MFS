package com.pf.mas.model.dto.cibil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EMIMasterDto {
    private long interestId;
    private String typeOfLoan;
    private int interest;
    private int tenure;
}

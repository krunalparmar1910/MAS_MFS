package com.pf.mas.model.dto.cibil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputResponseDTO {
    private String requestId;
    private Long id;
    private String status;
}

package com.pf.perfios.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponseDTO<T> {
    private Long totalElements;
    private Integer totalPages;
    private List<T> elements;
}

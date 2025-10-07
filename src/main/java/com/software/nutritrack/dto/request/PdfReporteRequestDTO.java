package com.software.nutritrack.dto.request;

public record PdfReporteRequestDTO(
        Long userId,
        String rango
) {}
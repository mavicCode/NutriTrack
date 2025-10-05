package com.software.nutritrack.dto.response;

public record ComparacionReporteResponseDTO(
        Double metaCalorias,
        Double consumido,
        Long cumplimiento,
        String mensaje
) {}
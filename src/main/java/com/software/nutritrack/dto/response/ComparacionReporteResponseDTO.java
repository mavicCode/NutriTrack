package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record ComparacionReporteResponseDTO(
        Double metaCalorias,
        Double consumido,
        Integer cumplimiento
) {}
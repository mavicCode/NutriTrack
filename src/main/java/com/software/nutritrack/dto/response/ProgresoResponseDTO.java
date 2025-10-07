package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProgresoResponseDTO(
        Long id,
        LocalDate fecha,
        boolean cumplido,
        double porcentaje,
        String recomendaciones
) {}


package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record ComidaAlimentoResponseDTO(
        Long id,
        Long alimentoId,
        String alimentoNombre,
        String categoria,
        Integer caloriasPorUnidad,
        Double cantidad,
        Integer caloriasTotales
) {}
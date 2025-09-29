package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record AlimentoResponseDTO(
        Long id,
        String name,
        int calorias,
        double peso,
        String categoria
) {}
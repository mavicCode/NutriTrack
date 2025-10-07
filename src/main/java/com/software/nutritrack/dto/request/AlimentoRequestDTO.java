package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record AlimentoRequestDTO(
        String name,

        @PositiveOrZero(message = "Las calorías no pueden ser negativas")
        Integer calorias,

        @Positive(message = "El peso debe ser mayor a cero")
        Double peso,

        String categoria
) {}
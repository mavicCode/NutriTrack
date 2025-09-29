package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AlimentoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "Las calorías son obligatorias")
        @PositiveOrZero(message = "Las calorías no pueden ser negativas")
        Integer calorias,

        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor a cero")
        Double peso,

        @NotBlank(message = "La categoría es obligatoria")
        String categoria
) {}
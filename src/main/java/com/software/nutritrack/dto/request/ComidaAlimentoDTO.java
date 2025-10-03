package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ComidaAlimentoDTO(
        @NotNull(message = "El ID del alimento es obligatorio")
        Long alimentoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser positiva")
        Double cantidad
) {}
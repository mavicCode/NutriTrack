package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PlanRequestDTO(
        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "El tipo de comida es obligatorio")
        @Positive(message = "El tipo de comida debe ser positivo")
        Integer tipoComida,

        @Size(max = 400, message = "La descripción no puede exceder 400 caracteres")
        String descripcion,

        Integer clasificacion,

        List<ComidaAlimentoDTO> alimentos
) {}